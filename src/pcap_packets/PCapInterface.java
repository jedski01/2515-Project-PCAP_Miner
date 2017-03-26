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

    //TODO [jed] : implement this. Parsing starts here
    public static boolean loadFromFile(String filename)  throws PcapNativeException, NotOpenException{
        packetCount = 0;

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

                //TODO [anyone] : we could probably simplify this conditionals, all of them are doing the same thing
                //check if packet has ethernet frame
                if (packet.contains(EthernetPacket.class)) {
                    //System.out.println(packet);

                    //System.out.println("Found ethernet frame");
                    //conversationManager.addFlow(Protocol.ETHERNET, packet);
                    EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                    EthernetPacket.EthernetHeader ethernetHeader = ethernetPacket.getHeader();

                    String addressA = ethernetHeader.getSrcAddr().toString();
                    String addressB = ethernetHeader.getDstAddr().toString();
                    int sizeInBytes = packet.length();
                    conversationManager.addFlow(Protocol.ETHERNET, addressA, addressB, sizeInBytes, time);
                }

                if (packet.contains(IpV6Packet.class)) {
                    //System.out.println("found ipv6 segment");
                    IpV6Packet ipv6Packet = packet.get(IpV6Packet.class);
                    IpV6Packet.IpV6Header ipV6Header = ipv6Packet.getHeader();

                    String addressA = ipV6Header.getSrcAddr().toString();
                    String addressB = ipV6Header.getDstAddr().toString();
                    //FIXME [anyone] : length doesn't seem right. bytes does not match up with wireshark values
                    int sizeInBytes = packet.length();
                    conversationManager.addFlow(Protocol.IPV6, addressA, addressB, sizeInBytes, time);
                }

                if (packet.contains(IpV4Packet.class)) {
                    //System.out.println("found ipv4 segment");

                    IpV4Packet ipv4Packet = packet.get(IpV4Packet.class);
                    IpV4Packet.IpV4Header ipV4Header = ipv4Packet.getHeader();

                    String addressA = ipV4Header.getSrcAddr().toString();
                    String addressB = ipV4Header.getDstAddr().toString();
                    //FIXME [anyone] : length doesn't seem right. bytes does not match up with wireshark values
                    int sizeInBytes = packet.length();
                    conversationManager.addFlow(Protocol.IPV4, addressA, addressB, sizeInBytes, time);
                }
                //TODO [anyone] : implement remaining protocols
                //TODO [anyone] : check if port is already in address, if so, separate it from the address
                if (packet.contains(TcpPacket.class)) {
                    //System.out.println("Found tcp packet!");
                }

                if (packet.contains(UdpPacket.class)) {
                    //System.out.println("Found tcp packet!");
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

    public static int getPacketCount() {
        return packetCount;
    }
    //TEST function
    public static void main(String[] args) {

        try {
            //CHANGE THIS FILENAME IF YOU WANT TO TEST FILES
            loadFromFile("sample files/iperf-mptcp-0-0.pcap");

            System.out.printf("Total number of packets : %d%n", getPacketCount());
            conversationManager.viewConversation();

        } catch (PcapNativeException e) {
            e.printStackTrace();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }

    }
}
