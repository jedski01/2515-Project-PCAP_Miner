package conversation;

/**
 * ConversationSummaryInfo
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */
public class ConversationSummaryInfo {

    private int packetsAToB = 0;
    private int packetsBToA = 0;
    private int bytesAToB = 0;
    private int bytesBToA = 0;
    private double startTime = 0;
    private double endTime = 0;


    public int getTotalPackets() {
        return packetsAToB + packetsBToA;
    }

    public int getTotalBytes() {
        return bytesAToB + bytesBToA;
    }
    public int getPacketsAToB() {
        return packetsAToB;
    }

    public int getPacketsBToA() {
        return packetsBToA;
    }

    public int getBytesAToB() {
        return bytesAToB;
    }

    public int getBytesBToA() {
        return bytesBToA;
    }

    public double getDuration() {
        return endTime - startTime;
    }

    public double getBpsAToB() {
        //return bytesAToB * 8 / getDuration();
        return 0;
    }

    public double getBpsBToA() {
        //return bytesBToA * 8 / getDuration();
        return 0;
    }

    public void incrementPacketCount(boolean reverse) {
        if (reverse) {
            packetsBToA++;
        } else {
            packetsAToB++;
        }

    }

    public void incrementByteSize(int size, boolean reverse) {
        if (reverse) {
            bytesBToA += size;
        } else {
            bytesAToB += size;
        }
    }

}
