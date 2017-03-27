package pcap_packets;

import java.io.EOFException;
import java.sql.Timestamp;
import java.util.concurrent.TimeoutException;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapHandle.TimestampPrecision;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.*;
import conversation.ConversationManager;
import org.pcap4j.packet.namednumber.TcpPort;
import org.pcap4j.packet.namednumber.UdpPort;

/**
 * PCapInterface
 * ACIT 2515 <Enter activity here>
 * <p>
 * <Enter a brief one sentence description of what this class is>
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-24
 */
public class PCapInterface {

    private static ConversationManager conversationManager = ConversationManager.getInstance();
    //declare statistics variables here
    private static int packetCount;
    private static int udpCount;
    private static int tcpCount;
    private static int ipv4Count;
    private static int ipv6Count;

    //TODO [jed] : implement this. Parsing starts here
    public static boolean loadFromFile(String filename)  throws PcapNativeException, NotOpenException{
        resetCounters();

        String PCAP_FILE_KEY = PCapInterface.class.getName() + ".pcapFile";
        String PCAP_FILE = System.getProperty(PCAP_FILE_KEY, filename);

        PcapHandle handle;
        try {
            handle = Pcaps.openOffline(PCAP_FILE, TimestampPrecision.NANO);
        } catch (PcapNativeException e) {
            handle = Pcaps.openOffline(PCAP_FILE);
        }
        while(true) {
            try {
                Packet packet = handle.getNextPacketEx();
                Timestamp time = handle.getTimestamp();
                String addressA = "";
                String addressB = "";
                //TODO [anyone] : we could probably simplify this conditionals, all of them are doing the same thing
                // READ LAYER 1
                //check if packet has ethernet frame
                if (packet.contains(EthernetPacket.class)) {
                    //System.out.println(packet);

                    //System.out.println("Found ethernet frame");
                    //conversationManager.addFlow(Protocol.ETHERNET, packet);
                    EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                    EthernetPacket.EthernetHeader ethernetHeader = ethernetPacket.getHeader();

                    addressA = ethernetHeader.getSrcAddr().toString();
                    addressB = ethernetHeader.getDstAddr().toString();
                    int sizeInBytes = packet.length();
                    conversationManager.addFlow(Protocol.ETHERNET, addressA, addressB, sizeInBytes, time);
                }
                //READ LAYER 2
                if (packet.contains(IpV6Packet.class)) {
                    //System.out.println("found ipv6 segment");
                    ipv6Count++;
                    IpV6Packet ipv6Packet = packet.get(IpV6Packet.class);
                    IpV6Packet.IpV6Header ipV6Header = ipv6Packet.getHeader();

                    addressA = ipV6Header.getSrcAddr().toString();
                    addressB = ipV6Header.getDstAddr().toString();
                    int sizeInBytes = packet.length();
                    int ttl = ipV6Header.getHopLimit();
                    conversationManager.addFlow(Protocol.IPV6, addressA, addressB, sizeInBytes, time, ttl);
                }

                if (packet.contains(IpV4Packet.class)) {
                    //System.out.println("found ipv4 segment");
                    ipv4Count++;
                    IpV4Packet ipv4Packet = packet.get(IpV4Packet.class);
                    IpV4Packet.IpV4Header ipV4Header = ipv4Packet.getHeader();
                    addressA = ipV4Header.getSrcAddr().toString();
                    addressB = ipV4Header.getDstAddr().toString();
                    int sizeInBytes = packet.length();
                    int ttl = ipV4Header.getTtlAsInt();
                    conversationManager.addFlow(Protocol.IPV4, addressA, addressB, sizeInBytes, time, ttl);
                }
                //FIXME [jed] : Temporary fix. there should be a better way of doing this
                //just extracting the address
                if (packet.contains(IcmpV6CommonPacket.class)) {

                    IcmpV6CommonPacket icmpv6Packet = packet.get(IcmpV6CommonPacket.class);

                    Packet payload = icmpv6Packet.getPayload();

                    if (payload.contains(IpV4Packet.class)) {
                        IpV4Packet ipv4Payload = payload.get(IpV4Packet.class);
                        IpV4Packet.IpV4Header payloadHeader = ipv4Payload.getHeader();

                        addressA = payloadHeader.getSrcAddr().toString();
                        addressB = payloadHeader.getDstAddr().toString();
                    }

                    if (payload.contains(IpV6Packet.class)) {
                        IpV6Packet ipv6Payload = payload.get(IpV6Packet.class);
                        IpV6Packet.IpV6Header payloadHeader = ipv6Payload.getHeader();

                        addressA = payloadHeader.getSrcAddr().toString();
                        addressB = payloadHeader.getDstAddr().toString();
                    }
                }
                if (packet.contains(IcmpV4CommonPacket.class)) {
                    IcmpV4CommonPacket icmpv4Packet = packet.get(IcmpV4CommonPacket.class);

                    Packet payload = icmpv4Packet.getPayload();

                    if (payload.contains(IpV4Packet.class)) {
                        IpV4Packet ipv4Payload = payload.get(IpV4Packet.class);
                        IpV4Packet.IpV4Header payloadHeader = ipv4Payload.getHeader();

                        addressA = payloadHeader.getSrcAddr().toString();
                        addressB = payloadHeader.getDstAddr().toString();
                    }

                    if (payload.contains(IpV6Packet.class)) {
                        IpV6Packet ipv6Payload = payload.get(IpV6Packet.class);
                        IpV6Packet.IpV6Header payloadHeader = ipv6Payload.getHeader();

                        addressA = payloadHeader.getSrcAddr().toString();
                        addressB = payloadHeader.getDstAddr().toString();
                    }

                }
                //READ LAYER 3
                if (packet.contains(TcpPacket.class)) {
                    //System.out.println("Found tcp packet!");
                    tcpCount++;

                    TcpPacket tcpPacket = packet.get(TcpPacket.class);
                    TcpPacket.TcpHeader tcpHeader = tcpPacket.getHeader();

                    TcpPort portA = tcpHeader.getSrcPort();
                    TcpPort portB = tcpHeader.getDstPort();
                    int sizeInBytes = packet.length();
                    conversationManager.addFlow(Protocol.TCP, addressA, addressB,
                            portA.valueAsInt(), portB.valueAsInt(), sizeInBytes, time);
                }

                if (packet.contains(UdpPacket.class)) {
                    //System.out.println("Found tcp packet!");
                    udpCount++;

                    UdpPacket udpPacket = packet.get(UdpPacket.class);
                    UdpPacket.UdpHeader udpHeader = udpPacket.getHeader();

                    UdpPort portA = udpHeader.getSrcPort();
                    UdpPort portB = udpHeader.getDstPort();
                    int sizeInBytes = packet.length();
                    conversationManager.addFlow(Protocol.UDP, addressA, addressB,
                            portA.valueAsInt(), portB.valueAsInt(), sizeInBytes, time);
                }
            } catch (TimeoutException e) {
                System.out.println("exception thrown");
            } catch (EOFException e) {
                System.out.println("EOF");
                break;
            }
            packetCount++;
        }

        handle.close();

        return true;
    }

    private static void resetCounters() {
        packetCount = 0;
        udpCount = 0;
        tcpCount = 0;
        ipv4Count = 0;
        ipv6Count = 0;
    }

    public static int getPacketCount() {
        return packetCount;
    }
    public static int getUDPCount() { return udpCount; }
    public static int getTCPCount() { return tcpCount; }
    public static int getIPv4Count() { return ipv4Count; }
    public static int getIPv6Count() { return ipv6Count; }

    //TEST function
    public static void main(String[] args) {

        try {
            //CHANGE THIS FILENAME IF YOU WANT TO TEST FILES
            loadFromFile("sample files/test.pcap");

            System.out.println("********************");
            System.out.println("SUMMARY");
            System.out.println("********************");
            System.out.printf("Total number of packets : %d%n", getPacketCount());
            System.out.printf("Total number of TCP packets : %d%n", getTCPCount());
            System.out.printf("Total number of UDP Packets : %d%n", getUDPCount());
            System.out.printf("Total number of IPv4 Packets : %d%n", getIPv4Count());
            System.out.printf("Total number of IPv6 Packets : %d%n", getIPv6Count());
            conversationManager.viewConversation();

        } catch (PcapNativeException e) {
            e.printStackTrace();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }
    }

}
