package conversation;

import java.util.ArrayList;
import java.util.Set;

/**
 * EthernetConversationList
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */
public class EthernetConversationList extends ConversationList {

    @Override
    public ArrayList<String[]> getSummarizedList() {

        ArrayList<String[]> result = new ArrayList<>();

        Set<ConversationID> ids = conversations.keySet();
        for (ConversationID id : ids) {
            String[] fields = new String[3];
            ArrayList<ConversationFlow> flows = conversations.get(id);
            int packetCount = flows.size();
            String addressA = id.getAddressA();
            String addressB = id.getAddressB();

            fields[0] = String.format("%d", packetCount);
            fields[1] = addressA;
            fields[2] = addressB;

            result.add(fields);
        }

        return result;
    }

}
