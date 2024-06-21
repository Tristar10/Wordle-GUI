package edu.virginia.cs.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import edu.virginia.cs.gui.WordleController;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class WordleApplication extends Application {
    public static String input;
    public static String getInput(){
        return input;
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordleApplication.class.getResource("wordle-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Wordle");
        stage.setScene(scene);
        stage.show();
        WordleController appController=fxmlLoader.getController();
        stage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!appController.terminate()) {
                    input = String.valueOf(event.getCode());
                    if (input.length() == 1) {
                        char inputChar = input.toUpperCase().charAt(0);
                        if (inputChar >= 'A' && inputChar <= 'Z') {
                            appController.setChar(input);
                            appController.addPos();
                        }
                    } else if (event.getCode() == KeyCode.BACK_SPACE) {
                        appController.delCurChar();
                    }
                    appController.submitGuess();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}