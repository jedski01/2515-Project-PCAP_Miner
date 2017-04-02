package pcap_analyzer;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;

/**
 * Created by Jed on 2017-04-02.
 */


/**
 * @author Angie(original)
 * @Version Fade-in removed; Add onSetScreen capability (rob)
 */

public class ScreensController extends StackPane{

    private HashMap<String, Node> screens = new HashMap<>();
    private HashMap<String, ControlledScreen> controllers = new HashMap<>();

    public ScreensController() { super(); }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name) {
        return screens.get(name);
    }

    public void addController(String name, ControlledScreen controller) {
        controllers.put(name, controller);
    }

    public ControlledScreen getController(String name) {
        return controllers.get(name);
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
            myScreenController.setScreenParent(this);
            addScreen(name, loadScreen);
            addController(name, myScreenController);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean setScreen(final String name) {
        return setScreen(name, this);
    }

    public boolean setScreen(final String name, Pane targetPane) {
        if (screens.get(name) != null) {

            //final DoubleProperty opacity = opacityProperty();

            if (!targetPane.getChildren().isEmpty()) {
                targetPane.getChildren().remove(0);
                targetPane.getChildren().add(0, screens.get(name));

                ControlledScreen myScreenController = controllers.get(name); //rob added
                myScreenController.onSetScreen();
            } else {

                targetPane.getChildren().add(screens.get(name));
                ControlledScreen myScreenController = controllers.get(name); //rob added
                myScreenController.onSetScreen(); //rob added
            }
            return true;
        } else {
            System.out.println("screen hasn't been loaded");
            return false;
        }
    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen doesn't exists");
            return false;
        } else {
            return true;
        }
    }
}
