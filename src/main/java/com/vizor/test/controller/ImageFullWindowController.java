package com.vizor.test.controller;

import com.vizor.test.module.ImageDTO;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageFullWindowController {
    private Stage fullScreenStage;

    public static void openFullScreen(ImageDTO imageDTO) {
        setStage();
        StackPane root = new StackPane(getImageView(imageDTO));
        fullScreenStage.setScene(getScene(root));
        fullScreenStage.setFullScreen(true);
        fullScreenStage.show();
    }

    private void setStage() {
        fullScreenStage = new Stage();
        fullScreenStage.initStyle(StageStyle.UNDECORATED);
    }

    private ImageView getImageView(ImageDTO imageDTO) {
        ImageView imageView = new ImageView(imageDTO.getImage());
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        fullScreenStage.widthProperty().addListener((obs, oldVal, newVal)
                -> imageView.setFitWidth(newVal.doubleValue()));

        fullScreenStage.heightProperty().addListener((obs, oldVal, newVal)
                -> imageView.setFitHeight(newVal.doubleValue()));
        return imageView;
    }

    private Scene getScene(StackPane root) {
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE)
                fullScreenStage.close();
        });
        return scene;
    }
}
