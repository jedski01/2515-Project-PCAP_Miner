package pcap_packets;

/**
 * Packet
 * Class for storing individual packet information
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-24
 */
public class Packet {

    private Header layer2Header = null,
                   layer3Header = null,
                   layer4Header = null;

    private ApplicationLayer appLayer= null;

    public Packet() {
    }

    public void setLayerHeader(int layer, Header layerHeader) {
        switch (layer) {
            case 2: layer2Header = layerHeader;
                    break;
            case 3: layer3Header = layerHeader;
                    break;
            case 4: layer4Header = layerHeader;
                    break;
        }
    }

    public Header getLayerHeader(int layer) {
        switch (layer) {
            case 2: return layer2Header;
            case 3: return layer3Header;
            case 4: return layer4Header;
            default: return null;
        }
    }

    public void setApplicationLayer(ApplicationLayer appLayer) {
        this.appLayer = appLayer;
    }

    public ApplicationLayer getApplicationLayer() {
        return this.appLayer;
    }
}
