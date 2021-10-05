package sample;


import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    FileChooser mediaChooser;

    Player mediaPlayer;

    MenuBar menu;
    Menu fileMenu;
    MenuItem openItem;

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);

            mediaChooser = new FileChooser();

            menu = new MenuBar();
            fileMenu = new Menu("file");
            openItem = new MenuItem("open");

            fileMenu.getItems().add(openItem);
            menu.getMenus().add(fileMenu);

            openItem.setOnAction((e) -> {

                try {
                    File mediaFile = mediaChooser.showOpenDialog(primaryStage);
                    if(mediaPlayer != null) {
                        mediaPlayer.player.dispose();
                    }
                    mediaPlayer = new Player(mediaFile.toURI().toURL().toExternalForm());
                    mediaPlayer.view.setFitWidth(scene.getWidth());
                    root.setCenter(mediaPlayer);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }

            });

            root.setTop(menu);
            primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
                if (mediaPlayer != null)
                    mediaPlayer.view.setFitWidth(scene.getWidth());
            });

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}