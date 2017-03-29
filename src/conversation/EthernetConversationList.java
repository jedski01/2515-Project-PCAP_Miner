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

    private static ConversationList instance = new EthernetConversationList();
    private static final int FIELD_COUNT = 11;

    private EthernetConversationList(){}

    public static ConversationList getInstance() {
        return instance;
    }

    @Override
    public ArrayList<String[]> getSummarizedList() {

        ArrayList<String[]> result = new ArrayList<>();

        Set<ConversationID> ids = conversations.keySet();
        for (ConversationID id : ids) {

            String[] fields = new String[FIELD_COUNT];

            ArrayList<ConversationFlow> flows = conversations.get(id);
            String addressA = id.getAddressA();
            String addressB = id.getAddressB();

            ConversationSummaryInfo summaryInfo = getSummaryInfo(flows);
            ConversationModel cm = new ConversationModel();

            cm.setAddressA(addressA);
            cm.setAddressB(addressB);
            cm.setPackets(summaryInfo.getTotalPackets());
            cm.setBytes(summaryInfo.getTotalBytes());
            cm.setPacketsAToB(summaryInfo.getPacketsAToB());
            cm.setPacketsBToA(summaryInfo.getPacketsBToA());
            cm.setBytesAToB(summaryInfo.getBytesAToB());
            cm.setBytesBToA(summaryInfo.getBytesBToA());
            cm.setDuration(summaryInfo.getDuration());
            cm.setBpsAToB(summaryInfo.getBpsAToB());
            cm.setBpsBToA(summaryInfo.getBpsBToA());

            result.add(fields);
        }

        return result;
    }



    @Override
    public void showConversation() {

        ArrayList<String[]> result = getSummarizedList();

        String format = "%-3s %-20s %-20s %-10s %-10s %-15s %-15s %-15s %-15s %-10s %-15s %-15s%n";

        System.out.println("******************************");
        System.out.println("ETHERNET CONVERSATIONS");
        System.out.println("******************************");

        System.out.printf(format, "No", "Address A", "Address B", "Packets", "Bytes", "Packets A->B", "Packets B->A",
                "Bytes A->B", "Bytes B->A", "Duration", "bps A->B", "bps B->A");
        Integer index = 1;
        for (String[] entry : result) {
            System.out.printf(format, index.toString(), entry[ADDR_A_FIELD], entry[ADDR_B_FIELD],
                    entry[TOT_PACKETS_FIELD], entry[TOT_BYTES_FIELD], entry[PACKETS_A_B_FIELD], entry[PACKETS_B_A_FIELD],
                    entry[BYTES_A_B_FIELD], entry[BYTES_B_A_FIELD], entry[DURATION_FIELD], entry[BPS_A_B_FIELD],
                    entry[BPS_B_A_FIELD]);
            index++;
        }
    }

    private ConversationSummaryInfo getSummaryInfo(ArrayList<ConversationFlow> flows) {

        ConversationSummaryInfo summaryInfo = new ConversationSummaryInfo();

        for (ConversationFlow flow : flows) {
            summaryInfo.incrementByteSize(flow.getBytes(), flow.isReversed());
            summaryInfo.incrementPacketCount(flow.isReversed());
        }
        if (!flows.isEmpty()) {
            summaryInfo.setDuration(flows.get(0).getTime(), flows.get(flows.size()-1).getTime());
        }

        return summaryInfo;
    }




}
