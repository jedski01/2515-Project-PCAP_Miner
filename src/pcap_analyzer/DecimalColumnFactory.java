package pcap_analyzer;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.text.DecimalFormat;

/**
 * Created by Jed on 2017-04-02.
 */
public class DecimalColumnFactory<S, T extends Number> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    private DecimalFormat format;
    private boolean zeroAsNA;
    public DecimalColumnFactory(DecimalFormat format, boolean zeroAsNA) {
        super();
        this.format = format;
        this.zeroAsNA = zeroAsNA;
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {

            @Override
            protected void updateItem(T item, boolean empty) {
                if (!empty && item != null) {
                    if (item.doubleValue() == 0.0 && zeroAsNA) {
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