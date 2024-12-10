package com.vizor.test.module;

import com.vizor.test.controller.LogController;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.net.MalformedURLException;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class ImageDTO implements Comparable<String> {
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

    @Override
    public int compareTo(String o) {
        return getName().compareTo(o);
    }
}
