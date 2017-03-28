package pcap_analyzer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import pcap_packets.*;
import conversation.*;


import java.io.File;

public class Controller {
    //TODO [patrick] : Inject interface objects to the controller class
    @FXML
    public FileChooser btnOpenFile;
    @FXML
    public Label lblPackets;
    @FXML
    public Label lblAvgPacketSec;
    @FXML
    public Label lblAvgPacketSize;
    @FXML
    public Label lblPacketsLost;
    @FXML
    public PieChart pieTcpUdp;
    @FXML
    public PieChart pieIP;

    //Implement pcap file opener
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

        try {
            PCapInterface.loadFromFile(inpFileName);
        } catch (PcapNativeException e) {
            e.printStackTrace();
            System.out.println("Error! Cannot read file");
        } catch (NotOpenException e) {
            e.printStackTrace();
            System.out.println("Error! Cannot read file");
        }

        //set metric values
        setLabelValues();
        setPieTcpUdp();
        setPieIP();

        System.out.println(PCapInterface.getIPv4Count() + "\n");
        System.out.println(PCapInterface.getIPv6Count() + "\n");
        System.out.println(PCapInterface.getPacketCount()+ "\n");
        System.out.println(PCapInterface.getTCPCount()+ "\n");
        System.out.println(PCapInterface.getUDPCount()+ "\n");

    };

    //close software after clicking exit
    public void closeWindow(){

    }

    //Set label values for the summary pane
    @FXML
    public void setLabelValues(){
        lblPackets.setText(String.format("%d", PCapInterface.getPacketCount()));
    }

    //Plug in pie chart values for the TCP/UDP distribution chart
    @FXML
    public void setPieTcpUdp(){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("TCP", PCapInterface.getTCPCount()),
                        new PieChart.Data("UDP", PCapInterface.getUDPCount()));
        pieTcpUdp.setData(pieChartData);
    }

    //Plug in pie chart values for the IPV4 VS IPV6 distribution chart
    @FXML
    public void setPieIP(){
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
                    new PieChart.Data("IPV4", PCapInterface.getIPv4Count()),
                    new PieChart.Data("IPV6", PCapInterface.getIPv6Count()));
        pieIP.setData(pieChartData);
    }

}
