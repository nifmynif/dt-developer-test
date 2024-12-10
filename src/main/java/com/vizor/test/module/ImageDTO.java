package com.vizor.test.module;

import com.vizor.test.controller.LogController;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.net.MalformedURLException;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ImageDTO {
    private final File file;
    private Image image;
    private final int pos;

    public String getName() {
        return file.getName();
    }

    public void setImage() throws MalformedURLException {
        if (image == null) {
            this.image = new Image(file.toURI().toURL().toExternalForm());
            LogController.logInfo("Картинка " + getName() + " скачана", this);
        }
    }
}
