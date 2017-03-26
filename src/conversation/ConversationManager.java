package conversation;

import pcap_packets.Protocol;

import java.sql.Timestamp;

/**
 * ConversationManager
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */
public class ConversationManager {

    private final static ConversationManager instance = new ConversationManager();
    ConversationList ethernetConversation = new EthernetConversationList();

    private ConversationManager() {}

    public static ConversationManager getInstance() {
        return instance;
    }

    public void addFlow(Protocol protocol, String addressA, String addressB,
                        int bytes, Timestamp time) {

        ConversationID id = new ConversationID(addressA, addressB);
        ConversationFlow flow = new ConversationFlow(bytes, time);

        switch (protocol) {
            case ETHERNET:
                ethernetConversation.add(id, flow);
                break;
            case IPV4:
                break;
            case IPV6:
                break;
            case TCP:
                 break;
            case UDP:
                 break;

        }
        if(protocol == Protocol.ETHERNET) {

        }
    }

    public void viewConversation() {

        ethernetConversation.showConversation();
        System.out.printf("Found %d conversations%n", ethernetConversation.getSize());
    }
}
