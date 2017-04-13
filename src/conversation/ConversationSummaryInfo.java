package conversation;

import java.sql.Timestamp;
import java.util.HashSet;

import util.TimeUtil;

/**
 * ConversationSummaryInfo
 * ACIT 2515 Final Project
 * Process the flows and return a summarize info for a specific hosts conversation
 *
 * @author Jed Iquin A00790108
 * @author Patrick Rodriguez A00997571
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
    private int retransmitAToB = 0;
    private int retransmitBToA = 0;
    private HashSet<Long> seqAB = new HashSet<>();
    private HashSet<Long> seqBA = new HashSet<>();


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

        if(packetsAToB < 2) return 0;

        return bytesAToB * 8 / getDuration();
    }

    public double getBpsBToA() {

        if(packetsBToA < 2) return 0;
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
        duration = TimeUtil.getTimeDifferenceInSeconds(start, end);
    }

    public void addSeq(Long seq, boolean reverse) {
        if (reverse) {
            if (seq != -1 && !seqBA.add(seq)) {
                retransmitBToA++;
            }
        } else {
            if (seq != -1 && !seqAB.add(seq)) {
                retransmitAToB++;
            }
        }
    }



    public int getRetransmitAToB() {
        return retransmitAToB;
    }

    public int getRetransmitBToA() {
        return retransmitBToA;
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
