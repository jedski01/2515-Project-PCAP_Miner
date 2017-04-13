package conversation;

import pcap_packets.Protocol;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * ConversationManager
 * ACIT 2515 Final Project
 * Class that manages all the conversation lists
 *
 * @author Jed Iquin A00790108
 * @author Patrick Rodriguez A00997571
 * @date 2017-03-25
 */
public class ConversationManager {

    private HashMap<Protocol, ConversationList> conversations = new HashMap<>();
    private final static ConversationManager instance = new ConversationManager();

    private ConversationManager() {

        for (Protocol protocol : Protocol.values()) {
            conversations.put(protocol, createConversation(protocol));
        }
    }

    public ConversationList getConversation(Protocol protocol) {
            return conversations.get(protocol);
    }

    private ConversationList createConversation(Protocol protocol) {

        switch (protocol) {
            case ETHERNET: return EthernetConversationList.getInstance();
            case IPV4: return Ipv4ConversationList.getInstance();
            case IPV6: return Ipv6ConversationList.getInstance();
            case TCP: return TcpConversationList.getInstance();
            case UDP: return UdpConversationList.getInstance();
        }
        return null;
    }
    public static ConversationManager getInstance() {
        return instance;
    }

    public void addFlow(Protocol protocol, String addressA, String addressB,
                        int bytes, Timestamp time) {

        ConversationID id = new ConversationID(addressA, addressB);
        ConversationFlow flow = new ConversationFlow(bytes, time);

        try {
            conversations.get(protocol).add(id, flow);
        } catch (NullPointerException e) {
            System.out.printf("Conversation for %s does not exists. Skipping this protocol%n", protocol.toString());
        }

    }

    public void addFlow(Protocol protocol, String addressA, String addressB, Integer portA, Integer portB,
                        int bytes, Timestamp time, long seq) {

        String addressAEx = addressA + "port" +portA;
        String addressBEx = addressB + "port" +portB;

        ConversationID id = new ConversationID(addressAEx, addressBEx);
        ConversationFlow flow;

        flow = new ConversationFlow(bytes, time, 0, seq);

        try {
            conversations.get(protocol).add(id, flow);
        } catch (NullPointerException e) {
            System.out.printf("Conversation for %s does not exists. Skipping this protocol%n", protocol.toString());
        }

    }

    public void addFlow(Protocol protocol, String addressA, String addressB,
                        int bytes, Timestamp time, int ttl) {

        ConversationID id = new ConversationID(addressA, addressB);
        ConversationFlow flow;

        flow = new ConversationFlow(bytes, time, ttl, 0);

        try {
            conversations.get(protocol).add(id, flow);
        } catch (NullPointerException e) {
            System.out.printf("Conversation for %s does not exists. Skipping this protocol%n", protocol.toString());
        }

    }

    public void viewConversation() {

        for (Protocol protocol : Protocol.values()) {
            ConversationList convo = getConversation(protocol);
            if (convo == null) {
                System.err.printf("Conversation for %s not found%n", protocol.toString());
                continue;
            }
            convo.showConversation();
            System.out.printf("Found %d conversations%n", convo.getSize());
        }

    }

    public void resetAll() {

        for (Protocol protocol : Protocol.values()) {
            if (conversations.get(protocol) == null) {
                System.err.printf("Conversation for %s not found%n", protocol.toString());
                continue;
            }
            conversations.get(protocol).reset();
        }
    }


}
