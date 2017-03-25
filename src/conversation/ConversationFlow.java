package conversation;

/**
 * ConversationFlow
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */
public class ConversationFlow {

    private int bytesAToB;
    private int bytesBtoA;
    private int packetsAToB;
    private int packetsBToA;

    private double time;
    private double bpsAToB;
    private double bpsBToA;

    public ConversationFlow(int bytesAToB, int bytesBtoA, double time) {


        this.bytesAToB = bytesAToB;
        this.bytesBtoA = bytesBtoA;
        this.packetsAToB = packetsAToB;
        this.packetsBToA = packetsBToA;
        this.time = time;

    }


    public int getBytes() {
        return bytesAToB + bytesBtoA;
    }

    public int getBytesAToB() {
        return bytesAToB;
    }

    public int getBytesBtoA() {
        return bytesBtoA;
    }

    public double getTime() {
        return time;
    }

    public double getBpsAToB() {
        return bpsAToB;
    }

    public double getBpsBToA() {
        return bpsBToA;
    }

    public int getPacketsAToB() {
        return packetsAToB;
    }

    public int getPacketsBToA() {
        return packetsBToA;
    }

    public ConversationFlow reverse() {

        return new ConversationFlow(this.bytesBtoA, this.bytesAToB, this.time);
    }


}
