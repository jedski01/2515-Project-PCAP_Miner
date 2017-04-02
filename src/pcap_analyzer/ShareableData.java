package pcap_analyzer;

/**
 * This class implements a Singleton pattern (see Wikipedia Singleton for more info). Recall that a singleton only
 * ever has one instance, and it is globally available.
 *
 * Our singleton is nam,ed ShareableData. It contains a DataTable - which is just a List of DataItems.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a Singleton pattern (see Wikipedia Singleton for more info). Recall that a singleton only
 * ever has one instance, and it is globally available.
 *
 * Our singleton is nam,ed ShareableData. It contains a DataTable - which is just a List of DataItems.
 */
public class ShareableData {

     private final static ShareableData instance = new ShareableData();
     private List<DataItem> distributionData = new ArrayList<>();
     private ShareableData() {}

    public static ShareableData getInstance() {
         return instance;
    }

    public List<DataItem> getDistributionData() {
        return distributionData;
    }

    public void setDistributionData(List<DataItem> distData) {
        distributionData = distData;
    }
}
