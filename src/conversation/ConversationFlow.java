package conversation;

import java.sql.Timestamp;

/**
 * ConversationFlow
 * ACIT 2515 Final Project
 * This holds information for each packet flow of a specific conversation between two hosts
 *
 * @author Jed Iquin A00790108
 * @author Patrick Rodriguez A00997571
 * @date 2017-03-25
 */
public class ConversationFlow {

    private int bytes;
    private Timestamp time;
    private boolean reverse;
    private int ttl;
    private long seq;

    public ConversationFlow(int bytes, Timestamp time) {

        this.reverse = false;
        this.bytes = bytes;
        this.time = time;
    }

    public ConversationFlow(int bytes, Timestamp time, int ttl, long seq) {

        this(bytes, time);
        this.ttl = ttl;
        this.seq = seq;
    }

    public boolean isReversed() {
        return reverse;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getBytes() {
        return bytes;
    }

    public int getTTL() {
        return ttl;
    }

    public long getSeq() {
        return seq;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

}
