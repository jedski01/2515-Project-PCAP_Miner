package conversation;

import java.util.ArrayList;

/**
 * Ipv6ConversationList
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */
//TODO [jed] : implement ipv6 conversation
public class Ipv6ConversationList extends ConversationList {

    private static ConversationList instance = new Ipv6ConversationList();

    private Ipv6ConversationList(){}

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
        System.out.println("IPV6 CONVERSATIONS");
        System.out.println("******************************");
    }
}
