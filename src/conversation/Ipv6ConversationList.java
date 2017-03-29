package conversation;

import java.util.ArrayList;
import java.util.Set;

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
    private final static int FIELD_COUNT = 11;
    private Ipv6ConversationList(){}

    public static ConversationList getInstance() {
        return instance;
    }

    @Override
    public ArrayList<ConversationModel> getSummarizedList() {
        ArrayList<ConversationModel> result = new ArrayList<>();

        Set<ConversationID> ids = conversations.keySet();
        for (ConversationID id : ids) {

            ArrayList<ConversationFlow> flows = conversations.get(id);
            String addressA = id.getAddressA();
            String addressB = id.getAddressB();

            ConversationSummaryInfo summaryInfo = getSummaryInfo(flows);
            ConversationModel cm = new ConversationModel();

            cm.setAddressA(addressA);
            cm.setAddressB(addressB);
            cm.setPackets(summaryInfo.getTotalPackets());
            cm.setBytes(summaryInfo.getTotalBytes());
            cm.setBytesAToB(summaryInfo.getBytesAToB());
            cm.setBytesBToA(summaryInfo.getBytesBToA());
            cm.setPacketsAToB(summaryInfo.getPacketsAToB());
            cm.setPacketsBToA(summaryInfo.getPacketsBToA());
            cm.setDuration(summaryInfo.getDuration());
            cm.setBpsAToB(summaryInfo.getBpsAToB());
            cm.setBpsBToA(summaryInfo.getBpsBToA());
            cm.setMinTTL(summaryInfo.getMinTTL());
            cm.setMaxTTL(summaryInfo.getMaxTTL());
            cm.setAvgTTL(summaryInfo.getAvgTTL());

            result.add(cm);
        }

        return result;
    }

    @Override
    public void showConversation() {
        ArrayList<ConversationModel> result = getSummarizedList();

        String format = "%-3d %-30s %-30s %-10d %-10d %-15d %-15d %-15d %-15d %-10f %-15f %-15f %-10d %-10d %-10d%n";

        System.out.println("******************************");
        System.out.println("IPV6 CONVERSATIONS");
        System.out.println("******************************");

        System.out.printf("%-3s %-30s %-30s %-10s %-10s %-15s %-15s %-15s %-15s %-10s %-15s %-15s %-10s %-10s %-10s%n",
                "No", "Address A", "Address B", "Packets", "Bytes", "Packets A->B", "Packets B->A",
                "Bytes A->B", "Bytes B->A", "Duration", "bps A->B", "bps B->A", "Min TTL", "Max TTL", "Avg TTL");
        Integer index = 1;
        for (ConversationModel conv : result) {
            System.out.printf(format, index, conv.getAddressA(), conv.getAddressB(), conv.getPackets(), conv.getBytes(),
                    conv.getPacketsAToB(), conv.getPacketsBToA(), conv.getBytesAToB(), conv.getBytesBToA(),
                    conv.getDuration(), conv.getBpsAToB(), conv.getBpsBToA(), conv.getMinTTL(), conv.getMaxTTL(), conv.getAvgTTL());
            index++;
        }
    }

    private ConversationSummaryInfo getSummaryInfo(ArrayList<ConversationFlow> flows) {

        ConversationSummaryInfo summaryInfo = new ConversationSummaryInfo();

        for (ConversationFlow flow : flows) {
            summaryInfo.incrementByteSize(flow.getBytes(), flow.isReversed());
            summaryInfo.incrementPacketCount(flow.isReversed());
            summaryInfo.setTTL(flow.getTTL());
        }
        if (!flows.isEmpty()) {
            summaryInfo.setDuration(flows.get(0).getTime(), flows.get(flows.size()-1).getTime());
        }

        return summaryInfo;
    }
}
