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
    private int max_ttl = 0;
    private int min_ttl = 255;
    private int ttls = 0;

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

    public int getMinTTL() {
        return min_ttl;
    }

    public int getMaxTTL() {
        return max_ttl;
    }

    public int getAvgTTL() {
        return ttls/getTotalPackets();
    }

    public void setTTL(int ttl) {

        if (ttl < min_ttl) {
            min_ttl = ttl;
        }

        if (ttl > max_ttl) {
            max_ttl = ttl;
        }

        ttls += ttl;
    }

    public void setDuration(Timestamp start, Timestamp end) {

        //do relative computation
        //get the difference in milliseconds first
        //we know end is always greater than start
        long milliDifference = end.getTime() - start.getTime();

        //get their nano values for a more precise calc
        //remove unnecessary part, precision is up to 3 decimal after millis
        //after that its just zero
        long nanoStart = start.getNanos() / 1000;
        long nanoEnd = end.getNanos() / 1000;

        //get the values after the millis part
        double startNanoPart = nanoStart % 1000 / 1000.0;
        double endNanoPart = nanoEnd % 1000/ 1000.0;

        //relative computation
        //start is zero so end is the difference between end and start
        //just add the nano part of the end
        double endMillis = milliDifference + endNanoPart;
        //you don't have to add zero to start nano

        //so difference is the end millis and start nano
        double difference = endMillis - startNanoPart;

        duration = difference / 1000.0;

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
