package pcap_analyzer;
import conversation.ConversationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import pcap_packets.*;



import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    //TODO [patrick] : Inject interface objects to the controller class
    //MENU ITEMS
    @FXML
    public FileChooser btnOpenFile;

    //SUMMARY ITEMS
    @FXML
    public Label lblPackets;
    @FXML
    public Label lblAvgPacketSec;
    @FXML
    public Label lblAvgPacketSize;
    @FXML
    public Label lblPacketsLost;

    //PIE CHARTS
    @FXML
    public PieChart pieTcpUdp;
    @FXML
    public PieChart pieIP;

    //TABLEVIEWS
    @FXML
    public TableView<ConversationModel> tblEthernet = new TableView<>();
    @FXML
    public TableView<ConversationModel> tblIPV4 = new TableView<>();
    @FXML
    public TableView<ConversationModel> tblIPV6 = new TableView<>();
    @FXML
    public TableView<ConversationModel> tblTcp = new TableView<>();
    @FXML
    public TableView<ConversationModel> tblUdp = new TableView<>();

    //TableView properties - ETHERNET
    @FXML
    public TableColumn<ConversationModel, String> ethAddressA;
    @FXML
    public TableColumn<ConversationModel, String> ethAddressB;
    @FXML
    public TableColumn<ConversationModel, Integer> ethPackets;
    @FXML
    public TableColumn<ConversationModel, Integer> ethBytes;
    @FXML
    public TableColumn<ConversationModel, Integer> ethPacketsAB;
    @FXML
    public TableColumn<ConversationModel, Integer> ethPacketsBA;
    @FXML
    public TableColumn<ConversationModel, Integer> ethBytesAB;
    @FXML
    public TableColumn<ConversationModel, Integer> ethBytesBA;
    @FXML
    public TableColumn<ConversationModel, Double> ethDuration;
    @FXML
    public TableColumn<ConversationModel, Double> ethBpsAB;
    @FXML
    public TableColumn<ConversationModel, Double> ethBpsBA;

    //TableView properties - IPV4
    @FXML
    public TableColumn<ConversationModel, String> ipv4AddressA;
    @FXML
    public TableColumn<ConversationModel, String> ipv4AddressB;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv4Packets;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv4Bytes;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv4PacketsAB;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv4PacketsBA;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv4BytesAB;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv4BytesBA;
    @FXML
    public TableColumn<ConversationModel, Double> ipv4Duration;
    @FXML
    public TableColumn<ConversationModel, Double> ipv4BpsAB;
    @FXML
    public TableColumn<ConversationModel, Double> ipv4BpsBA;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv4MinTTL;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv4MaxTTL;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv4AvgTTL;

    //TableView properties - IPV6
    @FXML
    public TableColumn<ConversationModel, String> ipv6AddressA;
    @FXML
    public TableColumn<ConversationModel, String> ipv6AddressB;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv6Packets;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv6Bytes;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv6PacketsAB;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv6PacketsBA;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv6BytesAB;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv6BytesBA;
    @FXML
    public TableColumn<ConversationModel, Double> ipv6Duration;
    @FXML
    public TableColumn<ConversationModel, Double> ipv6BpsAB;
    @FXML
    public TableColumn<ConversationModel, Double> ipv6BpsBA;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv6MinHop;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv6MaxHop;
    @FXML
    public TableColumn<ConversationModel, Integer> ipv6AvgHop;

    //TableView properties - TCP
    @FXML
    public TableColumn<ConversationModel, String> tcpAddressA;
    @FXML
    public TableColumn<ConversationModel, String> tcpAddressB;
    @FXML
    public TableColumn<ConversationModel, Integer> tcpPortA;
    @FXML
    public TableColumn<ConversationModel, Integer> tcpPortB;
    @FXML
    public TableColumn<ConversationModel, Integer> tcpPackets;
    @FXML
    public TableColumn<ConversationModel, Integer> tcpBytes;
    @FXML
    public TableColumn<ConversationModel, Integer> tcpPacketsAB;
    @FXML
    public TableColumn<ConversationModel, Integer> tcpPacketsBA;
    @FXML
    public TableColumn<ConversationModel, Integer> tcpBytesAB;
    @FXML
    public TableColumn<ConversationModel, Integer> tcpBytesBA;
    @FXML
    public TableColumn<ConversationModel, Double> tcpDuration;
    @FXML
    public TableColumn<ConversationModel, Double> tcpBpsAB;
    @FXML
    public TableColumn<ConversationModel, Double> tcpBpsBA;

    //TableView properties - UDP
    @FXML
    public TableColumn<ConversationModel, String> udpAddressA;
    @FXML
    public TableColumn<ConversationModel, String> udpAddressB;
    @FXML
    public TableColumn<ConversationModel, Integer> udpPortA;
    @FXML
    public TableColumn<ConversationModel, Integer> udpPortB;
    @FXML
    public TableColumn<ConversationModel, Integer> udpPackets;
    @FXML
    public TableColumn<ConversationModel, Integer> udpBytes;
    @FXML
    public TableColumn<ConversationModel, Integer> udpPacketsAB;
    @FXML
    public TableColumn<ConversationModel, Integer> udpPacketsBA;
    @FXML
    public TableColumn<ConversationModel, Integer> udpBytesAB;
    @FXML
    public TableColumn<ConversationModel, Integer> udpBytesBA;
    @FXML
    public TableColumn<ConversationModel, Double> udpDuration;
    @FXML
    public TableColumn<ConversationModel, Double> udpBpsAB;
    @FXML
    public TableColumn<ConversationModel, Double> udpBpsBA;


    private HashMap<Protocol, ObservableList<ConversationModel>>  observableList = new HashMap<>();


    //Implement pcap file opener
    //TODO [anyone] : show message box when there is an error in opening pcap file
    @FXML
    public void openFile(){
        btnOpenFile = new FileChooser();

        //apply extension filters for PCAP files
        btnOpenFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PCAP Files", "*.pcap", "*.pcapng"));

        //set title for the stage
        btnOpenFile.setTitle("Open PCAP file");
        File pcapFile = btnOpenFile.showOpenDialog(new Stage());

        //get filename of selected file so to use loadFromFile method from the PCapInterface
        String inpFileName = pcapFile.getAbsolutePath();

        PcapHandle handle = null;
        try {
            handle = PCapInterface.openPcapFile(inpFileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error! Cannot read file");
        }

        try {
            PCapInterface.processPcapFile(handle);
        } catch (PcapNativeException e) {
            e.printStackTrace();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }
        PCapInterface.updateAllConversationModels();
        //set metric values
        setLabelValues();
        setPieTcpUdp();
        setPieIP();
        updateTables();
    };

    //TODO [patrick] : implement this
    //close software after clicking exit
    @FXML
    public void closeWindow(){

    }

    //Set label values for the summary pane
    @FXML
    public void setLabelValues(){
        lblPackets.setText(String.format("%d", PCapInterface.packetStat.getPacketCount()));
        lblAvgPacketSize.setText(String.format("%d", PCapInterface.packetStat.getAvgPacketSize()));
        lblAvgPacketSec.setText(String.format("%d", PCapInterface.packetStat.getPacketPerSec()));
        lblPacketsLost.setText(String.format("%d", PCapInterface.packetStat.getPacketLost()));

    }

    //Plug in pie chart values for the TCP/UDP distribution chart
    @FXML
    public void setPieTcpUdp(){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("TCP", PCapInterface.packetStat.getTcpCount()),
                        new PieChart.Data("UDP", PCapInterface.packetStat.getUdpCount()));
        pieTcpUdp.setData(pieChartData);
    }

    //Plug in pie chart values for the IPV4 VS IPV6 distribution chart
    @FXML
    public void setPieIP(){
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
                    new PieChart.Data("IPV4", PCapInterface.packetStat.getIpv4Count()),
                    new PieChart.Data("IPV6", PCapInterface.packetStat.getIpv6Count()));
        pieIP.setData(pieChartData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Protocol protocol : Protocol.values()) {
            observableList.put(protocol, FXCollections.observableArrayList());
        }

        initializeEthernetTable();
        initializeIPV4Table();
        intitializeIPV6Table();
        initializeUDPTable();
        initializeTCPTable();
        
    }

    private void updateTables() {

        for (Protocol protocol : Protocol.values()) {
            ObservableList<ConversationModel> cm = observableList.get(protocol);
            cm.setAll(PCapInterface.getConversationModel(protocol));
            switch (protocol) {
                case ETHERNET:
                    tblEthernet.setItems(cm);
                    break;
                case IPV4:
                    tblIPV4.setItems(cm);
                    break;
                case IPV6:
                    tblIPV6.setItems(cm);
                    break;
                case TCP:
                    tblTcp.setItems(cm);
                    break;
                case UDP:
                    tblUdp.setItems(cm);
                    break;
            }
        }
    }

    private void initializeTCPTable() {

        tcpAddressA.setCellValueFactory(cellData -> cellData.getValue().addressAProperty());
        tcpAddressB.setCellValueFactory(cellData -> cellData.getValue().addressBProperty());
        tcpPortA.setCellValueFactory(cellData -> cellData.getValue().portAProperty().asObject());
        tcpPortB.setCellValueFactory(cellData -> cellData.getValue().portBProperty().asObject());
        tcpPackets.setCellValueFactory(cellData -> cellData.getValue().packetsProperty().asObject());
        tcpBytes.setCellValueFactory(cellData -> cellData.getValue().bytesProperty().asObject());
        tcpPacketsAB.setCellValueFactory(cellData -> cellData.getValue().packetsAToBProperty().asObject());
        tcpPacketsBA.setCellValueFactory(cellData -> cellData.getValue().packetsBToAProperty().asObject());
        tcpBytesAB.setCellValueFactory(cellData -> cellData.getValue().bytesAToBProperty().asObject());
        tcpBytesBA.setCellValueFactory(cellData -> cellData.getValue().bytesBToAProperty().asObject());
        tcpDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        tcpDuration.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.0000")));
        tcpBpsAB.setCellValueFactory(cellData -> cellData.getValue().bpsAToBProperty().asObject());
        tcpBpsAB.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));
        tcpBpsBA.setCellValueFactory(cellData -> cellData.getValue().bpsBToAProperty().asObject());
        tcpBpsBA.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));
    }

    private void initializeUDPTable() {

        udpAddressA.setCellValueFactory(cellData -> cellData.getValue().addressAProperty());
        udpAddressB.setCellValueFactory(cellData -> cellData.getValue().addressBProperty());
        udpPortA.setCellValueFactory(cellData -> cellData.getValue().portAProperty().asObject());
        udpPortB.setCellValueFactory(cellData -> cellData.getValue().portBProperty().asObject());
        udpPackets.setCellValueFactory(cellData -> cellData.getValue().packetsProperty().asObject());
        udpBytes.setCellValueFactory(cellData -> cellData.getValue().bytesProperty().asObject());
        udpPacketsAB.setCellValueFactory(cellData -> cellData.getValue().packetsAToBProperty().asObject());
        udpPacketsBA.setCellValueFactory(cellData -> cellData.getValue().packetsBToAProperty().asObject());
        udpBytesAB.setCellValueFactory(cellData -> cellData.getValue().bytesAToBProperty().asObject());
        udpBytesBA.setCellValueFactory(cellData -> cellData.getValue().bytesBToAProperty().asObject());
        udpDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        udpDuration.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.0000")));
        udpBpsAB.setCellValueFactory(cellData -> cellData.getValue().bpsAToBProperty().asObject());
        udpBpsAB.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));
        udpBpsBA.setCellValueFactory(cellData -> cellData.getValue().bpsBToAProperty().asObject());
        udpBpsBA.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));
    }

    private void intitializeIPV6Table() {
        ipv6AddressA.setCellValueFactory(cellData -> cellData.getValue().addressAProperty());
        ipv6AddressB.setCellValueFactory(cellData -> cellData.getValue().addressBProperty());
        ipv6Packets.setCellValueFactory(cellData -> cellData.getValue().packetsProperty().asObject());
        ipv6Bytes.setCellValueFactory(cellData -> cellData.getValue().bytesProperty().asObject());
        ipv6PacketsAB.setCellValueFactory(cellData -> cellData.getValue().packetsAToBProperty().asObject());
        ipv6PacketsBA.setCellValueFactory(cellData -> cellData.getValue().packetsBToAProperty().asObject());
        ipv6BytesAB.setCellValueFactory(cellData -> cellData.getValue().bytesAToBProperty().asObject());
        ipv6BytesBA.setCellValueFactory(cellData -> cellData.getValue().bytesBToAProperty().asObject());
        ipv6Duration.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        ipv6Duration.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.0000")));
        ipv6BpsAB.setCellValueFactory(cellData -> cellData.getValue().bpsAToBProperty().asObject());
        ipv6Duration.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));
        ipv6BpsBA.setCellValueFactory(cellData -> cellData.getValue().bpsBToAProperty().asObject());
        ipv6BpsBA.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));
        ipv6MinHop.setCellValueFactory(cellData -> cellData.getValue().minTTLProperty().asObject());
        ipv6MaxHop.setCellValueFactory(cellData -> cellData.getValue().maxTTLProperty().asObject());
        ipv6AvgHop.setCellValueFactory(cellData -> cellData.getValue().avgTTLProperty().asObject());
    }

    private void initializeIPV4Table() {
        ipv4AddressA.setCellValueFactory(cellData -> cellData.getValue().addressAProperty());
        ipv4AddressB.setCellValueFactory(cellData -> cellData.getValue().addressBProperty());
        ipv4Packets.setCellValueFactory(cellData -> cellData.getValue().packetsProperty().asObject());
        ipv4Bytes.setCellValueFactory(cellData -> cellData.getValue().bytesProperty().asObject());
        ipv4PacketsAB.setCellValueFactory(cellData -> cellData.getValue().packetsAToBProperty().asObject());
        ipv4PacketsBA.setCellValueFactory(cellData -> cellData.getValue().packetsBToAProperty().asObject());
        ipv4BytesAB.setCellValueFactory(cellData -> cellData.getValue().bytesAToBProperty().asObject());
        ipv4BytesBA.setCellValueFactory(cellData -> cellData.getValue().bytesBToAProperty().asObject());
        ipv4Duration.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        ipv4Duration.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.0000")));
        ipv4BpsAB.setCellValueFactory(cellData -> cellData.getValue().bpsAToBProperty().asObject());
        ipv4BpsAB.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));
        ipv4BpsBA.setCellValueFactory(cellData -> cellData.getValue().bpsBToAProperty().asObject());
        ipv4BpsBA.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));
        ipv4MinTTL.setCellValueFactory(cellData -> cellData.getValue().minTTLProperty().asObject());
        ipv4MaxTTL.setCellValueFactory(cellData -> cellData.getValue().maxTTLProperty().asObject());
        ipv4AvgTTL.setCellValueFactory(cellData -> cellData.getValue().avgTTLProperty().asObject());
    }

    private void initializeEthernetTable() {

        ethAddressA.setCellValueFactory(cellData -> cellData.getValue().addressAProperty());
        ethAddressB.setCellValueFactory(cellData -> cellData.getValue().addressBProperty());
        ethPackets.setCellValueFactory(cellData -> cellData.getValue().packetsProperty().asObject());
        ethBytes.setCellValueFactory(cellData -> cellData.getValue().bytesProperty().asObject());
        ethPacketsAB.setCellValueFactory(cellData -> cellData.getValue().packetsAToBProperty().asObject());
        ethPacketsBA.setCellValueFactory(cellData -> cellData.getValue().packetsBToAProperty().asObject());
        ethBytesAB.setCellValueFactory(cellData -> cellData.getValue().bytesAToBProperty().asObject());
        ethBytesBA.setCellValueFactory(cellData -> cellData.getValue().bytesBToAProperty().asObject());
        ethDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        ethDuration.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.0000")));
        ethBpsAB.setCellValueFactory(cellData -> cellData.getValue().bpsAToBProperty().asObject());
        ethBpsAB.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));
        ethBpsBA.setCellValueFactory(cellData -> cellData.getValue().bpsBToAProperty().asObject());
        ethBpsBA.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));
    }

    public class DecimalColumnFactory<S, T extends Number> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

        private DecimalFormat format;

        public DecimalColumnFactory(DecimalFormat format) {
            super();
            this.format = format;
        }

        @Override
        public TableCell<S, T> call(TableColumn<S, T> param) {
            return new TableCell<S, T>() {

                @Override
                protected void updateItem(T item, boolean empty) {
                    if (!empty && item != null) {
                        if (item.doubleValue() == 0.0) {
                            setText("N/A");
                        } else {
                            setText(format.format(item.doubleValue()));
                        }
                    } else {
                        setText("");
                    }
                }
            };
        }
    }
}


