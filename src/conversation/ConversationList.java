package conversation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public abstract List<ConversationModel> getSummarizedList();

    public static ConversationList getInstance() {
        return null;
    }

    //for testing purposes. print the values in console
    public abstract void showConversation();

}
