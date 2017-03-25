package pcap_packets;

import java.util.HashMap;

/**
 * PacketManager
 * ACIT 2515 <Enter activity here>
 * <p>
 * <Enter a brief one sentence description of what this class is>
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-24
 */
public class PacketManager {

    private static PacketManager instance = new PacketManager();
    private HashMap<Integer, Packet> packetList = new HashMap<>();

    private PacketManager(){}

    public static PacketManager getInstance() {
        return instance;
    }

    public int getTotalPacketCount() {
        return packetList.size();
    }

    public Packet getPacketAtIndex(int index) {
        return packetList.get(index);
    }

    public void addPacket(Packet packet) {
        packetList.put(packetList.size(), packet);
    }

    //TODO [jed] : Implement this
    public int getTotalPacketLost() {
        return 0;
    }

    //TODO [jed] : Implement this
    public int getTCPCount() {
        return 0;
    }

    //TODO [jed] : Implement this
    public int getUDPCount() {
        return 0;
    }



}
