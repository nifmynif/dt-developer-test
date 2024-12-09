package com.vizor.test.module;

import javafx.scene.image.Image;
import lombok.Getter;

import java.io.File;
import java.net.MalformedURLException;

@Getter
public class ImageDTO {
    private final File file;
    private Image image;
    private final int pos;

    public ImageDTO(File file, int pos) {
        this.file = file;
        this.pos = pos;
    }

    public String getName() {
        return file.getName();
    }

    public void setImage() throws MalformedURLException {
        this.image = new Image(file.toURI().toURL().toExternalForm());
    }
}
