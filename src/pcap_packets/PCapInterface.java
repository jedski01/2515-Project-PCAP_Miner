package pcap_packets;

import java.io.EOFException;
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

    //TODO [jed] : implement this. Parsing starts here
    public static boolean loadFromFile(String filename) {
        //prase the pcap file here

        //read the file
        //loop through all the packets

        return true;
    }

    //TEST function
    public static void main(String[] args) throws PcapNativeException, NotOpenException {


        String PCAP_FILE_KEY
                = PCapInterface.class.getName() + ".pcapFile";
        String PCAP_FILE
                = System.getProperty(PCAP_FILE_KEY, "sample files/smallFlows.pcap");

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

                //check if packet has ethernet frame
                if (packet.contains(EthernetPacket.class)) {
                    //System.out.println("Found ethernet frame");
                    //conversationManager.addFlow(Protocol.ETHERNET, packet);
                    EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                    EthernetPacket.EthernetHeader ethernetHeader = ethernetPacket.getHeader();

                    String addressA = ethernetHeader.getSrcAddr().toString();
                    String addressB = ethernetHeader.getDstAddr().toString();
                    int sizeInBytes = ethernetPacket.length();
//                    System.out.println(addressA);
//                    System.out.println(addressB);
//                    System.out.println("Size in bytes : "+sizeInBytes);
                    conversationManager.addFlow(Protocol.ETHERNET, addressA, addressB, sizeInBytes, 0);
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

        System.out.printf("Total packets : %d%n", packetCount);
        conversationManager.viewConversation();

        handle.close();


    }
}
