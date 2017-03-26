package conversation;

import java.util.ArrayList;
import java.util.Set;

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
    private static final int FIELD_COUNT = 13;
    private TcpConversationList(){}

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
            String[] addressPortPairA = id.getAddressA().split("port");
            String[] addressPortPairB = id.getAddressB().split("port");
            String addressA = addressPortPairA[0];
            String addressB = addressPortPairB[0];
            String portA = addressPortPairA[1];
            String portB = addressPortPairB[1];

            ConversationSummaryInfo summaryInfo = getSummaryInfo(flows);

            fields[ADDR_A_FIELD] = addressA;
            fields[ADDR_B_FIELD] = addressB;
            fields[PORT_A_FIELD] = portA;
            fields[PORT_B_FIELD] = portB;
            fields[TOT_PACKETS_FIELD] = String.format("%d", summaryInfo.getTotalPackets());
            fields[TOT_BYTES_FIELD] = String.format("%d", summaryInfo.getTotalBytes());
            fields[BYTES_A_B_FIELD] = String.format("%d", summaryInfo.getBytesAToB());
            fields[BYTES_B_A_FIELD] = String.format("%d", summaryInfo.getBytesBToA());
            fields[PACKETS_A_B_FIELD] = String.format("%d", summaryInfo.getPacketsAToB());
            fields[PACKETS_B_A_FIELD] = String.format("%d", summaryInfo.getPacketsBToA());
            fields[DURATION_FIELD] = String.format("%.5f", summaryInfo.getDuration());
            fields[BPS_A_B_FIELD] = String.format("%.2f", summaryInfo.getBpsAToB());
            fields[BPS_B_A_FIELD] = String.format("%.5f", summaryInfo.getBpsBToA());

            result.add(fields);
        }

        return result;
    }


    @Override
    public void showConversation() {
        ArrayList<String[]> result = getSummarizedList();

        String format = "%-3s %-20s %-10s %-20s %-10s %-10s %-10s %-15s %-15s %-15s %-15s %-10s %-15s %-15s%n";

        System.out.println("******************************");
        System.out.println("TCP CONVERSATIONS");
        System.out.println("******************************");

        System.out.printf(format, "No", "Address A", "Port A","Address B", "Port B",
                "Packets", "Bytes", "Packets A->B", "Packets B->A",
                "Bytes A->B", "Bytes B->A", "Duration", "bps A->B", "bps B->A");
        Integer index = 1;
        for (String[] entry : result) {
            System.out.printf(format, index.toString(),
                    entry[ADDR_A_FIELD], entry[PORT_A_FIELD], entry[ADDR_B_FIELD], entry[PORT_B_FIELD],
                    entry[TOT_PACKETS_FIELD], entry[TOT_BYTES_FIELD],
                    entry[PACKETS_A_B_FIELD], entry[PACKETS_B_A_FIELD], entry[BYTES_A_B_FIELD], entry[BYTES_B_A_FIELD],
                    entry[DURATION_FIELD], entry[BPS_A_B_FIELD], entry[BPS_B_A_FIELD]);
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
