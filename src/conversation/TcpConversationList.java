package conversation;

import java.util.ArrayList;

/**
 * TcpConversationList
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */
//TODO [jed] : implement tcp conversation. NOTE add follow tcp stream feature
public class TcpConversationList extends ConversationList {

    private static ConversationList instance = new TcpConversationList();

    private TcpConversationList(){}

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
        System.out.println("TCP CONVERSATIONS");
        System.out.println("******************************");
    }
}