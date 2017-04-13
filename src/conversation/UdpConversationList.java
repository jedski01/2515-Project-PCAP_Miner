package conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * UdpConversationList
 * ACIT 2515 Final Project
 * List of UDP conversations between hosts for UDP protocol
 *
 * @author Jed Iquin A00790108
 * @author Patrick Rodriguez A00997571
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
    public List<ConversationModel> getSummarizedList() {
        ArrayList<ConversationModel> result = new ArrayList<>();

        Set<ConversationID> ids = conversations.keySet();
        for (ConversationID id : ids) {

            ArrayList<ConversationFlow> flows = conversations.get(id);
            String[] addressPortPairA = id.getAddressA().split("port");
            String[] addressPortPairB = id.getAddressB().split("port");
            String addressA = addressPortPairA[0];
            String addressB = addressPortPairB[0];
            Integer portA = Integer.parseInt(addressPortPairA[1]);
            Integer portB = Integer.parseInt(addressPortPairB[1]);

            ConversationSummaryInfo summaryInfo = getSummaryInfo(flows);
            ConversationModel cm = new ConversationModel();

            cm.setAddressA(addressA);
            cm.setAddressB(addressB);
            cm.setPortA(portA);
            cm.setPortB(portB);
            cm.setPackets(summaryInfo.getTotalPackets());
            cm.setBytes(summaryInfo.getTotalBytes());
            cm.setBytesAToB(summaryInfo.getBytesAToB());
            cm.setBytesBToA(summaryInfo.getBytesBToA());
            cm.setPacketsAToB(summaryInfo.getPacketsBToA());
            cm.setPacketsBToA(summaryInfo.getPacketsAToB());
            cm.setDuration(summaryInfo.getDuration());
            cm.setBpsAToB(summaryInfo.getBpsAToB());
            cm.setBpsBToA(summaryInfo.getBpsBToA());


            result.add(cm);
        }

        return result;
    }

    @Override
    public void showConversation() {
        List<ConversationModel> result = getSummarizedList();

        String format = "%-3d %-20s %-10s %-20s %-10d %-10d %-10d %-15d %-15d %-15d %-15d %-10f %-15f %-15f%n";

        System.out.println("******************************");
        System.out.println("UDP CONVERSATIONS");
        System.out.println("******************************");

        System.out.printf("%-3s %-20s %-10s %-20s %-10s %-10s %-10s %-15s %-15s %-15s %-15s %-10s %-15s %-15s%n",
                "No", "Address A", "Port A","Address B", "Port B",
                "Packets", "Bytes", "Packets A->B", "Packets B->A",
                "Bytes A->B", "Bytes B->A", "Duration", "bps A->B", "bps B->A");
        Integer index = 1;
        for (ConversationModel conv : result) {
            System.out.printf(format, index,
                    conv.getAddressA(), conv.getPortA(), conv.getAddressB(), conv.getPortB(), conv.getPackets(),
                    conv.getBytes(), conv.getPacketsAToB(), conv.getPacketsBToA(), conv.getBytesAToB(), conv.getBytesBToA(),
                    conv.getDuration(), conv.getBpsAToB(), conv.getBpsBToA());
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
