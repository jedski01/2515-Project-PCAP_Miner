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

         int COUNT = 5;

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

                //TODO [jed] read layer by layer
                if (packet.contains(PppPacket.class)) {
                    System.out.print("found PPP -->>");
                    Packet payload = packet.getPayload();

                    if (payload != null) {
                        if (payload.contains(IcmpV6CommonPacket.class)) {
                            System.out.print("found ICMP frame -->>");
                        }

                        if (payload.contains(IpV6Packet.class)) {
                            System.out.print("Found IPV6 frame -->>");
                        }

                        if (payload.contains(IpV4Packet.class)) {
                            System.out.print("Found IPV4 frame  -->>");
                        }
                    }

                } else if (packet.contains(EthernetPacket.class)) {
                    System.out.println("Found ethernet frame");
                    //conversationManager.addFlow(Protocol.ETHERNET, packet);
                    String addressA = packet.get(EthernetPacket.class).getHeader().getSrcAddr().toString();
                    String addressB = packet.get(EthernetPacket.class).getHeader().getDstAddr().toString();
                    conversationManager.addFlow(Protocol.ETHERNET, addressA, addressB, 0, 0, 0);
                }
                else {
                    System.out.println("Found wireless");
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
