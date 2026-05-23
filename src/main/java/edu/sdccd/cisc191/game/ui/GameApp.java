package edu.sdccd.cisc191.game.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// MODULE 7: JavaFX entry point — run this after starting the Spring Boot server
public class GameApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game-view.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.setTitle("Game Server");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 