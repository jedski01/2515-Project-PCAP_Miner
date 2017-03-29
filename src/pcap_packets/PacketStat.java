package pcap_packets;


/**
 * Created by Jed on 2017-03-28.
 */
public class PacketStat {

    private static final PacketStat instance = new PacketStat();
    private PacketStat(){}

    private Integer packetCount;
    private Integer avgPacketSize;
    private Integer packetPerSec;
    private Integer packetLost;
    private Integer ipv6Count;
    private Integer ipv4Count;
    private Integer tcpCount;
    private Integer retransmit;

    public Integer getRetransmit() {
        return retransmit;
    }

    public void setRetransmit(Integer retransmit) {
        this.retransmit = retransmit;
    }

    public static PacketStat getInstance() {
        return instance;
    }

    public Integer getPacketCount() {
        return packetCount;
    }

    public void setPacketCount(Integer packetCount) {
        this.packetCount = packetCount;
    }

    public Integer getAvgPacketSize() {
        return avgPacketSize;
    }

    public void setAvgPacketSize(Integer avgPacketSize) {
        this.avgPacketSize = avgPacketSize;
    }

    public Integer getPacketPerSec() {
        return packetPerSec;
    }

    public void setPacketPerSec(Integer packetPerSec) {
        this.packetPerSec = packetPerSec;
    }

    public Integer getPacketLost() {
        return packetLost;
    }

    public void setPacketLost(Integer packetLost) {
        this.packetLost = packetLost;
    }

    public Integer getIpv6Count() {
        return ipv6Count;
    }

    public void setIpv6Count(Integer ipv6Count) {
        this.ipv6Count = ipv6Count;
    }

    public Integer getIpv4Count() {
        return ipv4Count;
    }

    public void setIpv4Count(Integer ipv4Count) {
        this.ipv4Count = ipv4Count;
    }

    public Integer getTcpCount() {
        return tcpCount;
    }

    public void setTcpCount(Integer tcpCount) {
        this.tcpCount = tcpCount;
    }

    public Integer getUdpCount() {
        return udpCount;
    }

    public void setUdpCount(Integer udpCount) {
        this.udpCount = udpCount;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    private Integer udpCount;
    private double duration;


}
