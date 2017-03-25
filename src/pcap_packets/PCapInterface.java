package pcap_packets;

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

    PacketManager packetManager = PacketManager.getInstance();

    //TODO [jed] : implement this. Parsing starts here
    public boolean loadFromFile(String filename) {
        //prase the pcap file here

        //read the file
        //loop through all the packets
        Packet newPacket = new Packet();

        newPacket.setLayerHeader(2, extractHeaderInfo(2));
        newPacket.setLayerHeader(3, extractHeaderInfo(3));
        newPacket.setLayerHeader(4, extractHeaderInfo(4));
        newPacket.setApplicationLayer(extractAppLayer());

        packetManager.addPacket(newPacket);
        return true;
    }

    //TODO [jed] : Extract information for each layer
    private Header extractHeaderInfo(int layer) {
        return null;
    }

    private ApplicationLayer extractAppLayer() {
        return null;
    }

    public static void main(String[] args) {

        PCapInterface pInterface = new PCapInterface();
    }
}
