package pcap_analyzer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import pcap_packets.PCapInterface;
import pcap_packets.PacketStat;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by Jed on 2017-04-02.
 */
public class MainSceneController implements Initializable, ControlledScreen{

    private enum PieChartNames{
        IP, TRANSPORT, RETRANSMISSION
    }

    private ShareableData shareableData = ShareableData.getInstance();
    private HashMap<PieChartNames, ArrayList<DataItem>> statDataItems = new HashMap<>();
    @FXML
    private Label lblFilename;
    @FXML
    private Label lblPacketCount;

    @FXML
    private Label lblPacketsPerSec;

    @FXML
    private Label lblAveragePacketLength;

    @FXML
    private Label lblRetransmission;

    @FXML
    private ToggleButton btnTransport;

    @FXML
    private ToggleButton btnIp;

    @FXML
    private ToggleButton btnRetransmission;

    @FXML
    private RadioButton rdoPie;

    @FXML
    private RadioButton rdoBar;

    @FXML
    private Pane replaceablePane;

    @FXML
    private ToggleGroup dataButtonGroup;

    @FXML
    private ToggleGroup chartToggleGroup;

    ScreensController myController;

    @FXML
    public void handleOpenFileAction() {
        FileChooser fileChooser = new FileChooser();

        //apply extension filters for PCAP files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PCAP Files", "*.pcap", "*.pcapng"));

        //set title for the stage
        fileChooser.setTitle("Open PCAP file");
        File pcapFile = fileChooser.showOpenDialog(new Stage());

        if (pcapFile == null) {
            return;
        }
        //get filename of selected file so to use loadFromFile method from the PCapInterface
        String inpFileName = pcapFile.getAbsolutePath();
        String fname = pcapFile.getName();


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
            //e.printStackTrace();
        } catch (NotOpenException e) {
            //e.printStackTrace();
        }

        PCapInterface.updateAllConversationModels();
        setLabelValues(fname, PCapInterface.packetStat);
        setPieChartDataItems();

        if(dataButtonGroup.getSelectedToggle().equals(btnTransport)) {
            shareableData.setDistributionData(statDataItems.get(PieChartNames.TRANSPORT));
        } else if(dataButtonGroup.getSelectedToggle().equals(btnIp)) {
            shareableData.setDistributionData(statDataItems.get(PieChartNames.IP));
        } else if(dataButtonGroup.getSelectedToggle().equals(btnRetransmission)) {
            shareableData.setDistributionData(statDataItems.get(PieChartNames.RETRANSMISSION));
        }

        if(chartToggleGroup.getSelectedToggle().equals(rdoPie)) {
            myController.setScreen(Main.pieChartScreen, replaceablePane);
        } else {
            myController.setScreen(Main.barChartScreen, replaceablePane);
        }
    }

    @FXML
    public void handleTransportButtonAction() {

        shareableData.setDistributionData(statDataItems.get(PieChartNames.TRANSPORT));
        if(chartToggleGroup.getSelectedToggle().equals(rdoPie)) {
            myController.setScreen(Main.pieChartScreen, replaceablePane);
        } else {
            myController.setScreen(Main.barChartScreen, replaceablePane);
        }
    }

    @FXML
    public void handleIPButtonAction() {
        shareableData.setDistributionData(statDataItems.get(PieChartNames.IP));
        if(chartToggleGroup.getSelectedToggle().equals(rdoPie)) {
            myController.setScreen(Main.pieChartScreen, replaceablePane);
        } else {
            myController.setScreen(Main.barChartScreen, replaceablePane);
        }
    }

    @FXML
    public void handleRetransmissionButtonAction() {
        shareableData.setDistributionData(statDataItems.get(PieChartNames.RETRANSMISSION));
        if(chartToggleGroup.getSelectedToggle().equals(rdoPie)) {
            myController.setScreen(Main.pieChartScreen, replaceablePane);
        } else {
            myController.setScreen(Main.barChartScreen, replaceablePane);
        }
    }

    @FXML
    public void handlePieChartRadioAction() {
        myController.setScreen(Main.pieChartScreen, replaceablePane);
    }

    @FXML
    public void handleBarChartRadioAction() {
        myController.setScreen(Main.barChartScreen, replaceablePane);
    }

    //VALUE SETTINGS TO BE USED BY PIE CHART AND LABELS
    private void setLabelValues(String filename, PacketStat stat) {
        lblFilename.setText(filename);
        lblAveragePacketLength.setText(String.format("%d", stat.getAvgPacketSize()));
        lblPacketCount.setText(String.format("%d", stat.getPacketCount()));
        lblPacketsPerSec.setText(String.format("%d",stat.getPacketPerSec()));
        lblRetransmission.setText(String.format("%d",stat.getPacketLost()));
    }

    private void setPieChartDataItems() {
        //TCP and UDP
        statDataItems.put(PieChartNames.TRANSPORT, setTCPUDPDataItems());
        statDataItems.put(PieChartNames.IP, setIPV4IPV6DataItems());
        statDataItems.put(PieChartNames.RETRANSMISSION, setTransmissionDataItems());
    }

    private ArrayList<DataItem> setTCPUDPDataItems() {

        ArrayList<DataItem> result = new ArrayList<>();
        DataItem udp = new DataItem("UDP", PCapInterface.packetStat.getUdpCount());
        DataItem tcp = new DataItem("TCP", PCapInterface.packetStat.getTcpCount());
        result.add(udp);
        result.add(tcp);
        return result;

    }

    private ArrayList<DataItem> setIPV4IPV6DataItems() {
        ArrayList<DataItem> result = new ArrayList<>();
        DataItem ipv4 = new DataItem("IPv4", PCapInterface.packetStat.getIpv4Count());
        DataItem ipv6 = new DataItem("IPv6", PCapInterface.packetStat.getIpv6Count());
        result.add(ipv4);
        result.add(ipv6);
        return result;
    }

    private ArrayList<DataItem>  setTransmissionDataItems () {
        ArrayList<DataItem> result = new ArrayList<>();
        DataItem received = new DataItem("Received",
                PCapInterface.packetStat.getPacketCount()-PCapInterface.packetStat.getPacketLost());
        DataItem lost = new DataItem("Lost", PCapInterface.packetStat.getPacketLost());
        result.add(received);
        result.add(lost);
        return result;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (PieChartNames name : PieChartNames.values()) {
            statDataItems.put(name, new ArrayList<DataItem>());
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void onSetScreen() {

    }
}
