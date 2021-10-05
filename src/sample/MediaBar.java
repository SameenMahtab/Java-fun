package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MediaBar extends HBox {

    Slider time;
    Slider vol;

    Button pause;

    Label volumeLBL;

    MediaPlayer player;

    public MediaBar(MediaPlayer play) {
        player = play;

        vol = new Slider();
        time = new Slider();

        setAlignment(Pos.CENTER);

        setPadding(new Insets(10,10,10,10));
        setStyle("-fx-background-color:white");

        volumeLBL = new Label("Volume :");
        pause = new Button("||");

        getChildren().add(pause);
        getChildren().add(time);

        getChildren().add(volumeLBL);
        getChildren().add(vol);

        vol.prefWidth(70);
        vol.setMinWidth(30);

        vol.setValue(100);

        pause.setOnAction((e) -> {
            Status status = player.getStatus();

            if (status == Status.PLAYING) {
                player.pause();
                pause.setText(">");
            } else if (status == Status.PAUSED) {
                player.play();
                pause.setText("||");
            }
        });

        player.currentTimeProperty().addListener((o) -> {

            time.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100);
        });

        time.valueProperty().addListener((o) -> {
            if (time.isPressed())
                player.seek(player.getMedia().getDuration().multiply(time.getValue() / 100));
        });

        vol.valueProperty().addListener((o) -> {
            player.setVolume(vol.getValue() / 100);
        });
    }

}
