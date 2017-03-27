package conversation;

import java.sql.Timestamp;

/**
 * ConversationFlow
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */
public class ConversationFlow {

    private int bytes;
    private Timestamp time;
    private boolean reverse;
    private int ttl;

    public ConversationFlow(int bytes, Timestamp time) {

        this.reverse = false;
        this.bytes = bytes;
        this.time = time;
    }

    public ConversationFlow(int bytes, Timestamp time, int ttl) {

        this.reverse = false;
        this.bytes = bytes;
        this.time = time;
        this.ttl = ttl;
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

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

}
