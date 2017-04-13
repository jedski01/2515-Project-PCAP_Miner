package pcap_packets;

import java.io.EOFException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import conversation.ConversationModel;
import conversation.TcpConversationList;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapHandle.TimestampPrecision;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.*;
import conversation.ConversationManager;

import static util.TimeUtil.getTimeDifferenceInSeconds;

/**
 * PCapInterface
 * ACIT 2515 Final Project
 * Acting API to controllers. Controllers only communicate with this class
 *
 * @author Jed Iquin A00790108
 * @author Patrick Rodriguez A00997571
 * @date 2017-03-24
 */


public class PCapInterface {

    static class Metrics {
        int bytes;
        int ttl;
        long seq;
        int portA;
        int portB;
    }


    private static HashMap<Protocol, List<ConversationModel>> conversationModels = new HashMap<>();

    public static ConversationManager conversationManager = ConversationManager.getInstance();
    public static PacketStat packetStat = PacketStat.getInstance();


    public static PcapHandle openPcapFile(String filename) throws Exception {

        try {
            Class.forName("android.support.v7.internal.view.menu.MenuBuilder");
        } catch (ClassNotFoundException e) {
            // Handle Exception by displaying an alert to user to update device firmware.
        }catch (NoClassDefFoundError e){
            // Handle Exception by displaying an alert to user to update device firmware.
        }

        String PCAP_FILE_KEY = PCapInterface.class.getName() + ".pcapFile";
        String PCAP_FILE = System.getProperty(PCAP_FILE_KEY, filename);
        PcapHandle handle = null;
        try {
            handle = Pcaps.openOffline(PCAP_FILE, TimestampPrecision.NANO);
        } catch (PcapNativeException e) {
            try {
                handle = Pcaps.openOffline(PCAP_FILE);
            } catch (PcapNativeException ee) {
                //System.out.println(ee.toString());
                throw new Exception(e.toString());
            } catch (Exception  e1) {
                //e1.printStackTrace();
                return null;
            }
        } catch (NoClassDefFoundError  e) {
            e.printStackTrace();
        }
        return handle;
    }

    public static boolean processPcapFile(PcapHandle handle) throws PcapNativeException, NotOpenException {

        //initialize counters
        int packetCount = 0;
        int udpCount = 0;
        int tcpCount = 0;
        int ipv4Count = 0;
        int ipv6Count = 0;
        int packetSize = 0;

        Timestamp start = null;
        Timestamp end = null;
        boolean errorFound = false;
        boolean packetError = false;
        conversationManager.resetAll();

        while (!errorFound) {
            packetError = false;

            Packet packet = null;
            try {
                packet = handle.getNextPacketEx();
            } catch (TimeoutException e) {
                System.out.println("exception thrown");
                //I need to do this because if i set this to return true
                //java thinks that the codes underneath are unreachable
                errorFound = true;
                handle.close();
                return !errorFound;
            } catch (EOFException e) {
                System.out.println("EOF");
                handle.close();
                saveStat(start, end, packetCount, packetSize, ipv4Count, ipv6Count, tcpCount, udpCount);
                return true;
            } catch (Exception e) {
                packetError = true;

            }
            //skip packets with error
            if(!packetError) {
                Timestamp time = handle.getTimestamp();
                StringBuilder addressA = new StringBuilder();
                StringBuilder addressB = new StringBuilder();
                Metrics metric = new Metrics();

                packetSize += packet.length();

                if (packetCount == 0) {
                    start = time;
                }

                //read layer 2
                if (processEthernetPackets(packet, addressA, addressB, metric)) {
                    conversationManager.addFlow(Protocol.ETHERNET, addressA.toString(), addressB.toString(),
                            metric.bytes, time);
                }

                if (processIPV4Packets(packet, addressA, addressB, metric)) {
                    conversationManager.addFlow(Protocol.IPV4, addressA.toString(), addressB.toString(),
                            metric.bytes, time, metric.ttl);
                    ipv4Count++;
                }

                if (processIPV6Packets(packet, addressA, addressB, metric)) {
                    conversationManager.addFlow(Protocol.IPV6, addressA.toString(), addressB.toString(),
                            metric.bytes, time, metric.ttl);
                    ipv6Count++;
                }

                processICMPv4Packets(packet, addressA, addressB, metric);
                processICMPv6Packets(packet, addressA, addressB, metric);

                if (processTCPPackets(packet, addressA, addressB, metric)) {
                    conversationManager.addFlow(Protocol.TCP, addressA.toString(), addressB.toString(),
                            metric.portA, metric.portB, metric.bytes, time, metric.seq);
                    tcpCount++;
                }

                if (processUDPPackets(packet, addressA, addressB, metric)) {
                    conversationManager.addFlow(Protocol.UDP, addressA.toString(), addressB.toString(),
                            metric.portA, metric.portB, metric.bytes, time, 0);
                    udpCount++;
                }
                end = time;
                packetCount++;
            }


        }
        return false;
    }

    public static List<ConversationModel> getConversationModel(Protocol protocol) {
        return conversationModels.get(protocol);
    }

    public static void updateConversationModel(Protocol protocol) {
        conversationModels.put(protocol, conversationManager.getConversation(protocol).getSummarizedList());
        if (protocol == Protocol.TCP) {
            TcpConversationList tcp = (TcpConversationList) conversationManager.getConversation(protocol);
            packetStat.setPacketLost(tcp.getRetransmissionCount());

        }
    }

    public static void updateAllConversationModels() {
        for (Protocol protocol : Protocol.values()) {
            updateConversationModel(protocol);

        }
    }
    private static void saveStat(Timestamp start, Timestamp end, int packetCount, int packetSize, int ipv4Count,
                                 int ipv6Count, int tcpCount, int udpCount) {

        double duration = getTimeDifferenceInSeconds(start, end);
        int avgPacketSize, packetPerSec;
        if (packetCount > 1) {
            packetPerSec = (int) (packetCount / duration);
        } else {
            packetPerSec = 0;
        }
        avgPacketSize = packetSize / packetCount;
        packetStat.setAvgPacketSize(avgPacketSize);
        packetStat.setPacketPerSec(packetPerSec);
        packetStat.setPacketCount(packetCount);
        packetStat.setPacketLost(0);
        packetStat.setIpv4Count(ipv4Count);
        packetStat.setIpv6Count(ipv6Count);
        packetStat.setTcpCount(tcpCount);
        packetStat.setUdpCount(udpCount);
        packetStat.setDuration(duration);
    }

    //process packet and search for ethernet. return true if Ethernet packet is found
    private static boolean processEthernetPackets(Packet packet, StringBuilder addressA, StringBuilder addressB, Metrics metric) {

        if (packet.contains(EthernetPacket.class)) {
            //System.out.println(packet);

            //System.out.println("Found ethernet frame");
            //conversationManager.addFlow(Protocol.ETHERNET, packet);
            EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
            EthernetPacket.EthernetHeader ethernetHeader = ethernetPacket.getHeader();

            addressA.append(ethernetHeader.getSrcAddr().toString()) ;
            addressB.append(ethernetHeader.getDstAddr().toString());
            metric.bytes = packet.length();
            return true;
        }
        return false;
    }

    //process IPV4 packets, return true if ipv4 packet is found
    private static boolean processIPV4Packets(Packet packet, StringBuilder addressA, StringBuilder addressB, Metrics metric) {
        if (packet.contains(IpV4Packet.class)) {

            //clear the string builder first
            addressA.delete(0, addressA.length());
            addressB.delete(0, addressB.length());
            //System.out.println("found ipv4 segment");

            IpV4Packet ipv4Packet = packet.get(IpV4Packet.class);
            IpV4Packet.IpV4Header ipV4Header = ipv4Packet.getHeader();
            addressA.append(ipV4Header.getSrcAddr().toString().substring(1));
            addressB.append(ipV4Header.getDstAddr().toString().substring(1));
            metric.bytes = packet.length();
            metric.ttl = ipV4Header.getTtlAsInt();

            return true;
        }

        return false;
    }

    private static boolean processIPV6Packets(Packet packet, StringBuilder addressA, StringBuilder addressB, Metrics metric) {

        if (packet.contains(IpV6Packet.class)) {
            //System.out.println("found ipv6 segment");

            //clear the string builder first
            addressA.delete(0, addressA.length());
            addressB.delete(0, addressB.length());

            IpV6Packet ipv6Packet = packet.get(IpV6Packet.class);
            IpV6Packet.IpV6Header ipV6Header = ipv6Packet.getHeader();

            addressA.append(ipV6Header.getSrcAddr().toString().substring(1));
            addressB.append(ipV6Header.getDstAddr().toString().substring(1));
            metric.bytes = packet.length();
            metric.ttl = ipV6Header.getHopLimitAsInt();

            return true;
        }
        return false;
    }

    private static boolean processTCPPackets(Packet packet, StringBuilder addressA, StringBuilder addressB, Metrics metric) {
        if (packet.contains(TcpPacket.class)) {
            //System.out.println("Found tcp packet!");

            TcpPacket tcpPacket = packet.get(TcpPacket.class);
            TcpPacket.TcpHeader tcpHeader = tcpPacket.getHeader();

            metric.portA = tcpHeader.getSrcPort().valueAsInt();
            metric.portB = tcpHeader.getDstPort().valueAsInt();

            metric.bytes = packet.length();
            if(tcpPacket.getPayload() == null) {
                metric.seq = -1;
            } else {
                metric.seq = tcpHeader.getSequenceNumber();
            }
            return true;
        }

        return false;
    }

    private static boolean processUDPPackets(Packet packet, StringBuilder addressA, StringBuilder addressB, Metrics metric) {
        if (packet.contains(UdpPacket.class)) {
            //System.out.println("Found tcp packet!");

            UdpPacket udpPacket = packet.get(UdpPacket.class);
            UdpPacket.UdpHeader udpHeader = udpPacket.getHeader();

            metric.portA = udpHeader.getSrcPort().valueAsInt();
            metric.portB = udpHeader.getDstPort().valueAsInt();
            metric.bytes = packet.length();
            return true;
        }
        return false;
    }

    private static boolean processICMPv4Packets(Packet packet, StringBuilder addressA, StringBuilder addressB, Metrics metric) {
        if (packet.contains(IcmpV4CommonPacket.class)) {
            IcmpV4CommonPacket icmpv4Packet = packet.get(IcmpV4CommonPacket.class);

            Packet payload = icmpv4Packet.getPayload();

            processIPV4Packets(payload, addressA, addressB, metric);
            processIPV6Packets(payload, addressA, addressB, metric);
            return true;
        }

        return false;
    }

    private static boolean processICMPv6Packets(Packet packet, StringBuilder addressA, StringBuilder addressB, Metrics metric) {
        if (packet.contains(IcmpV6CommonPacket.class)) {

            IcmpV6CommonPacket icmpv6Packet = packet.get(IcmpV6CommonPacket.class);
            Packet payload = icmpv6Packet.getPayload();

            //clear the address first
            addressA.delete(0, addressA.length());
            addressB.delete(0, addressB.length());

            processIPV4Packets(payload, addressA, addressB, metric);
            processIPV6Packets(payload, addressA, addressB, metric);
            return true;
        }

        return false;
    }


}
