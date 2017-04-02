package pcap_analyzer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Jed on 2017-04-02.
 */
public class BarController implements Initializable, ControlledScreen{

    @FXML
    private BarChart barChart;

    @FXML
    NumberAxis yAxis;

    @FXML
    CategoryAxis xAxis;

    private ObservableList<String> categories = FXCollections.observableArrayList();

    private XYChart.Series<String, Integer> dataSeries = new XYChart.Series<>();

    ScreensController myController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataFromSharedModel();

        barChart.getData().add( dataSeries);

        xAxis.setCategories(categories);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void onSetScreen() {
        loadDataFromSharedModel();
    }

    public void loadDataFromSharedModel() {

        List<DataItem> data = ShareableData.getInstance().getDistributionData();

        dataSeries.getData().clear();

        categories.clear();

        for (DataItem item : data) {
            categories.add(item.getCategory());
            dataSeries.getData().add(new XYChart.Data<>(item.getCategory(), item.getValue()));
        }
    }
}
