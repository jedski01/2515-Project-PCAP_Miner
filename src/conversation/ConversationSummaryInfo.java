package conversation;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private double duration;


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
        return duration;
    }

    public double getBpsAToB() {

        if(getDuration() == 0) return 0;

        return bytesAToB * 8 / getDuration();
    }

    public double getBpsBToA() {

        if(getDuration() == 0) return 0;
        return bytesBToA * 8 / getDuration();
    }

    public void setDuration(Timestamp start, Timestamp end) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("ss S");
        Date firstParsedDate = start;
        Date secondParsedDate = end;
        long resultInNano = secondParsedDate.getTime() - firstParsedDate.getTime();
        duration = (double)resultInNano / 1000;

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
