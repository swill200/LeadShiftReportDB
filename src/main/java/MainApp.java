import controller.MainController;
import view.MainView;

public class MainApp {
    public static void main(String[] args) {
        // Create the main view
        MainView mainView = new MainView();

        // Create the controller and link it to the view
        MainController mainController = new MainController(mainView);

        // Show the main view
        mainView.open();
    }
}
