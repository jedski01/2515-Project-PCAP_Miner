package conversation;

import java.util.ArrayList;

/**
 * UdpConversationList
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */
//TODO [jed] : implement udp conversation
public class UdpConversationList extends ConversationList {

    private static ConversationList instance = new UdpConversationList();

    private UdpConversationList() {}

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
        System.out.println("UDP CONVERSATIONS");
        System.out.println("******************************");
    }
}
