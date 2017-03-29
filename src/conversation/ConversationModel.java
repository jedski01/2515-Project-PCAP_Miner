package conversation;

import javafx.beans.property.*;

/**
 * Created by Jed on 2017-03-28.
 */
public class ConversationModel {

    private StringProperty addressA = new SimpleStringProperty();
    private StringProperty addressB = new SimpleStringProperty();

    private IntegerProperty portA = new SimpleIntegerProperty();
    private IntegerProperty portB = new SimpleIntegerProperty();

    private IntegerProperty packets = new SimpleIntegerProperty();
    private IntegerProperty bytes = new SimpleIntegerProperty();

    private IntegerProperty packetsAToB = new SimpleIntegerProperty();
    private IntegerProperty packetsBToA = new SimpleIntegerProperty();

    private IntegerProperty bytesAToB = new SimpleIntegerProperty();
    private IntegerProperty bytesBToA = new SimpleIntegerProperty();

    private IntegerProperty minTTL = new SimpleIntegerProperty();
    private IntegerProperty maxTTL = new SimpleIntegerProperty();
    private IntegerProperty avgTTL = new SimpleIntegerProperty();

    private IntegerProperty retAToB = new SimpleIntegerProperty();
    private IntegerProperty retBToA = new SimpleIntegerProperty();

    private DoubleProperty bpsAToB = new SimpleDoubleProperty();
    private DoubleProperty bpsBToA = new SimpleDoubleProperty();

    private DoubleProperty duration = new SimpleDoubleProperty();

    public String getAddressA() {
        return addressA.get();
    }

    public StringProperty addressAProperty() {
        return addressA;
    }

    public void setAddressA(String addressA) {
        this.addressA.set(addressA);
    }

    public String getAddressB() {
        return addressB.get();
    }

    public StringProperty addressBProperty() {
        return addressB;
    }

    public void setAddressB(String addressB) {
        this.addressB.set(addressB);
    }

    public int getPortA() {
        return portA.get();
    }

    public IntegerProperty portAProperty() {
        return portA;
    }

    public void setPortA(int portA) {
        this.portA.set(portA);
    }

    public int getPortB() {
        return portB.get();
    }

    public IntegerProperty portBProperty() {
        return portB;
    }

    public void setPortB(int portB) {
        this.portB.set(portB);
    }

    public int getPackets() {
        return packets.get();
    }

    public IntegerProperty packetsProperty() {
        return packets;
    }

    public void setPackets(int packets) {
        this.packets.set(packets);
    }

    public int getBytes() {
        return bytes.get();
    }

    public IntegerProperty bytesProperty() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes.set(bytes);
    }

    public int getPacketsAToB() {
        return packetsAToB.get();
    }

    public IntegerProperty packetsAToBProperty() {
        return packetsAToB;
    }

    public void setPacketsAToB(int packetsAToB) {
        this.packetsAToB.set(packetsAToB);
    }

    public int getPacketsBToA() {
        return packetsBToA.get();
    }

    public IntegerProperty packetsBToAProperty() {
        return packetsBToA;
    }

    public void setPacketsBToA(int packetsBToA) {
        this.packetsBToA.set(packetsBToA);
    }

    public int getBytesAToB() {
        return bytesAToB.get();
    }

    public IntegerProperty bytesAToBProperty() {
        return bytesAToB;
    }

    public void setBytesAToB(int bytesAToB) {
        this.bytesAToB.set(bytesAToB);
    }

    public int getBytesBToA() {
        return bytesBToA.get();
    }

    public IntegerProperty bytesBToAProperty() {
        return bytesBToA;
    }

    public void setBytesBToA(int bytesBToA) {
        this.bytesBToA.set(bytesBToA);
    }

    public int getMinTTL() {
        return minTTL.get();
    }

    public IntegerProperty minTTLProperty() {
        return minTTL;
    }

    public void setMinTTL(int minTTL) {
        this.minTTL.set(minTTL);
    }

    public int getMaxTTL() {
        return maxTTL.get();
    }

    public IntegerProperty maxTTLProperty() {
        return maxTTL;
    }

    public void setMaxTTL(int maxTTL) {
        this.maxTTL.set(maxTTL);
    }

    public int getAvgTTL() {
        return avgTTL.get();
    }

    public IntegerProperty avgTTLProperty() {
        return avgTTL;
    }

    public void setAvgTTL(int avgTTL) {
        this.avgTTL.set(avgTTL);
    }

    public int getRetAToB() {
        return retAToB.get();
    }

    public IntegerProperty retAToBProperty() {
        return retAToB;
    }

    public void setRetAToB(int retAToB) {
        this.retAToB.set(retAToB);
    }

    public int getRetBToA() {
        return retBToA.get();
    }

    public IntegerProperty retBToAProperty() {
        return retBToA;
    }

    public void setRetBToA(int retBToA) {
        this.retBToA.set(retBToA);
    }

    public double getBpsAToB() {
        return bpsAToB.get();
    }

    public DoubleProperty bpsAToBProperty() {
        return bpsAToB;
    }

    public void setBpsAToB(double bpsAToB) {
        this.bpsAToB.set(bpsAToB);
    }

    public double getBpsBToA() {
        return bpsBToA.get();
    }

    public DoubleProperty bpsBToAProperty() {
        return bpsBToA;
    }

    public void setBpsBToA(double bpsBToA) {
        this.bpsBToA.set(bpsBToA);
    }

    public double getDuration() {
        return duration.get();
    }

    public DoubleProperty durationProperty() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration.set(duration);
    }
}
