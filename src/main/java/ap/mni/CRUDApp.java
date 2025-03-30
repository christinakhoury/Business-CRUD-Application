package ap.mni;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class CRUDApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        showLoginView();
    }


    private void showLoginView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ap/mni/views/login-view.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("Login - Company Management System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);
        primaryStage.show();
    }


    public static void showHomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CRUDApp.class.getResource("/ap/mni/views/home-view.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("Company Management System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
