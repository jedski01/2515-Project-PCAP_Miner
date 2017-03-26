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
    private static int packetCount;

    //TODO [jed] : implement this. Parsing starts here
    public static boolean loadFromFile(String filename)  throws PcapNativeException, NotOpenException{
        packetCount = 0;

        String PCAP_FILE_KEY
                = PCapInterface.class.getName() + ".pcapFile";
        String PCAP_FILE
                = System.getProperty(PCAP_FILE_KEY, filename);

        PcapHandle handle;
        try {
            handle = Pcaps.openOffline(PCAP_FILE, TimestampPrecision.NANO);
        } catch (PcapNativeException e) {
            handle = Pcaps.openOffline(PCAP_FILE);
        }
        int packetCount = 0;
        while(true) {
            try {
                Packet packet = handle.getNextPacketEx();
                Timestamp time = handle.getTimestamp();

                //check if packet has ethernet frame
                if (packet.contains(EthernetPacket.class)) {
                    //System.out.println(packet);

                    //System.out.println("Found ethernet frame");
                    //conversationManager.addFlow(Protocol.ETHERNET, packet);
                    EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                    EthernetPacket.EthernetHeader ethernetHeader = ethernetPacket.getHeader();

                    String addressA = ethernetHeader.getSrcAddr().toString();
                    String addressB = ethernetHeader.getDstAddr().toString();
                    int sizeInBytes = ethernetPacket.length();
                    conversationManager.addFlow(Protocol.ETHERNET, addressA, addressB, sizeInBytes, time);
                }

                if (packet.contains(IpV6Packet.class)) {
                    //System.out.println("found ipv6 segment");
                }

                if (packet.contains(IpV4Packet.class)) {
                    //System.out.println("found ipv4 segment");
                }

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
            //CHANGES THIS FILENAME IF YOU WANT TO TEST FILES
            loadFromFile("sample files/smallFlows.pcap");

            System.out.printf("Total number of packets : %d%n", getPacketCount());
            conversationManager.viewConversation();

        } catch (PcapNativeException e) {
            e.printStackTrace();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }

    }
}
