package com.vizor.test.controller;

import com.vizor.test.module.ImageDTO;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImageFullWindowController {

    private ImageFullWindowController() {
    }

    public static void openFullScreen(ImageDTO imageDTO) {
        Stage fullScreenStage = new Stage();
        fullScreenStage.initStyle(StageStyle.UNDECORATED);
        ImageView fullScreenImageView = new ImageView(imageDTO.getImage());

        fullScreenImageView.setPreserveRatio(true);
        fullScreenImageView.setSmooth(true);
        fullScreenStage.widthProperty().addListener((obs, oldVal, newVal)
                -> fullScreenImageView.setFitWidth(newVal.doubleValue()));

        fullScreenStage.heightProperty().addListener((obs, oldVal, newVal)
                -> fullScreenImageView.setFitHeight(newVal.doubleValue()));

        StackPane root = new StackPane(fullScreenImageView);
        Scene fullScreenScene = new Scene(root);
        fullScreenStage.setScene(fullScreenScene);

        fullScreenStage.setFullScreen(true);

        fullScreenScene.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE)
                fullScreenStage.close();
        });
        fullScreenStage.show();
    }
}
