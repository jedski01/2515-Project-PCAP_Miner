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
    private TcpConversationList(){}
    private int retransmissionCount;

    public static ConversationList getInstance() {
        return instance;
    }
    @Override
    public ArrayList<ConversationModel> getSummarizedList() {
        ArrayList<ConversationModel> result = new ArrayList<>();
        retransmissionCount = 0;
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
            cm.setRetAToB(summaryInfo.getRetransmitAToB());
            cm.setRetBToA(summaryInfo.getRetransmitBToA());
            retransmissionCount += summaryInfo.getRetransmitAToB()+summaryInfo.getRetransmitBToA();
            result.add(cm);
        }

        return result;
    }

    public int getRetransmissionCount() {
        return retransmissionCount;
    }

    @Override
    public void showConversation() {
        ArrayList<ConversationModel> result = getSummarizedList();

        String format = "%-3d %-20s %-10d %-20s %-10d %-10d %-10d %-15d %-15d %-15d %-15d %-10f %-15f %-15f %-10d %-10d%n";

        System.out.println("******************************");
        System.out.println("TCP CONVERSATIONS");
        System.out.println("******************************");

        System.out.printf("%-3s %-20s %-10s %-20s %-10s %-10s %-10s %-15s %-15s %-15s %-15s %-10s %-15s %-15s %-10s %10s%n",
                "No", "Address A", "Port A","Address B", "Port B",
                "Packets", "Bytes", "Packets A->B", "Packets B->A",
                "Bytes A->B", "Bytes B->A", "Duration", "bps A->B", "bps B->A", "Re A->B", "Re B->A");
        Integer index = 1;
        for (ConversationModel conv : result) {
            System.out.printf(format, index,
                    conv.getAddressA(), conv.getPortA(), conv.getAddressB(), conv.getPortB(), conv.getPackets(),
                    conv.getBytes(), conv.getPacketsAToB(), conv.getPacketsBToA(), conv.getBytesAToB(), conv.getBytesBToA(),
                    conv.getDuration(), conv.getBpsAToB(), conv.getBpsBToA(), conv.getRetAToB(), conv.getRetBToA());
            index++;
        }
    }

    private ConversationSummaryInfo getSummaryInfo(ArrayList<ConversationFlow> flows) {

        ConversationSummaryInfo summaryInfo = new ConversationSummaryInfo();

        for (ConversationFlow flow : flows) {
            summaryInfo.incrementByteSize(flow.getBytes(), flow.isReversed());
            summaryInfo.incrementPacketCount(flow.isReversed());
            summaryInfo.addSeq(flow.getSeq(), flow.isReversed());
        }
        if (!flows.isEmpty()) {
            summaryInfo.setDuration(flows.get(0).getTime(), flows.get(flows.size()-1).getTime());
        }

        return summaryInfo;
    }
}
