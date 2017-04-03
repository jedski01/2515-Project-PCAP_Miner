package pcap_analyzer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Jed on 2017-04-02.
 */
public class PieController implements Initializable, ControlledScreen {

    @FXML
    private PieChart pieChart;

    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    ScreensController myController;

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void onSetScreen() {
        loadDataFromSharedModel();
    }

    @Override
    public void handleCloseAction() {
        //do nothing
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadDataFromSharedModel();

        pieChart.setData(pieChartData);
    }

    public void loadDataFromSharedModel() {

        List<DataItem> data = ShareableData.getInstance().getDistributionData();

        pieChartData.clear();

        int total = 0;

        for (DataItem item : data) {
            total += item.getValue();
        }

        for (DataItem item : data) {
            int percent = (item.getValue() * 100) / total;
            String dataLabel = item.getCategory() + " (" + item.getValue() + ") " + percent + "%";
            pieChartData.add(new PieChart.Data(dataLabel, item.getValue()));
        }
    }



}
