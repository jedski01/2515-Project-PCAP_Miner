package pcap_analyzer;

/**
 * Created by Jed on 2017-04-02.
 */


/**
 *
 * @author Angie (original)
 */
public interface ControlledScreen {

    // This method will allow the injection of the Parent ScreenPane
    public void setScreenParent(ScreensController screenPage);

    // RJN added
    // This method allows us to define a function that runs each time a screen is made visible
    public void onSetScreen();
}
