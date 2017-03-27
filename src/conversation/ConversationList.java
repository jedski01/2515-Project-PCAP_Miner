package conversation;

import sun.plugin2.message.Conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * ConversationList
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */
public abstract class ConversationList {

    protected HashMap<ConversationID, ArrayList<ConversationFlow>> conversations = new HashMap<>();

    public final static int ADDR_A_FIELD = 0;
    public final static int ADDR_B_FIELD = 1;
    public final static int TOT_BYTES_FIELD = 2;
    public final static int TOT_PACKETS_FIELD = 3;
    public final static int BYTES_A_B_FIELD = 4;
    public final static int PACKETS_A_B_FIELD = 5;
    public final static int BYTES_B_A_FIELD = 6;
    public final static int PACKETS_B_A_FIELD = 7;
    public final static int DURATION_FIELD = 8;
    public final static int BPS_A_B_FIELD = 9;
    public final static int BPS_B_A_FIELD = 10;
    public final static int PORT_A_FIELD = 11;
    public final static int PORT_B_FIELD = 12;
    public final static int MIN_TTL_FIELD = 11;
    public final static int MAX_TTL_FIELD = 12;
    public final static int AVG_TTL_FIELD = 13;


    public int getSize() {
        return conversations.size();
    }

    public void add(ConversationID id, ConversationFlow flow) {

        if(conversations.containsKey(id.reverse())) {
            flow.setReverse(true);
            id = id.reverse();
        }

        else if(!conversations.containsKey(id)) {
            conversations.put(id, new ArrayList<ConversationFlow>());
        }

        conversations.get(id).add(flow);
    }

    public void reset() {
        conversations.clear();
    }

    public int getPacketCount(ConversationID id) {
        return conversations.get(id).size();
    }

    public abstract ArrayList<String[]> getSummarizedList();

    public static ConversationList getInstance() {
        return null;
    }

    //for testing purposes. print the values in console
    public abstract void showConversation();

}
