package pcap_packets;

/**
 * Header
 * ACIT 2515 <Enter activity here>
 * <p>
 * <Enter a brief one sentence description of what this class is>
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-24
 */
public class Header {

    private String source;
    private String destination;
    private int layer;
    String protocol;

    public Header(int layer) {
        this.layer = layer;
    }

    public String getSource(){
        return source;
    }
    public String getDestination() {
        return destination;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getLayer() {
        return layer;
    }

    public void setSource(String source){
        this.source = source;
    }

    public void setDestiantion(String destination) {
        this.destination = destination;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
