package pcap_analyzer;

/**
 *
 * @author Angie (original)
 */
public interface ControlledScreen {

    // This method will allow the injection of the Parent ScreenPane
    void setScreenParent(ScreensController screenPage);

    // RJN added
    // This method allows us to define a function that runs each time a screen is made visible
    void onSetScreen();

    void handleCloseAction();
}
