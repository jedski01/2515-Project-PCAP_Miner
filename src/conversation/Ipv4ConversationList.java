package conversation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Ipv4ConversationList
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */
public class Ipv4ConversationList extends ConversationList {

    private static ConversationList instance = new Ipv4ConversationList();

    private Ipv4ConversationList() {}

    public static ConversationList getInstance() {
        return instance;
    }

    @Override
    public ArrayList<String[]> getSummarizedList() {
        return null;
    }

    @Override
    public void showConversation() {
        System.out.println("******************************");
        System.out.println("IPV4 CONVERSATIONS");
        System.out.println("******************************");
    }
}
