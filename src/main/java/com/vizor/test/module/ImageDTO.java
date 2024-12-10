package com.vizor.test.module;

import com.vizor.test.controller.LogController;
import javafx.scene.image.Image;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.File;
import java.net.MalformedURLException;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class ImageDTO implements Comparable<String> {
    private File file;
    private Image image;

    public ImageDTO(File file) {
        this.file = file;
    }

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
    public int compareTo(@NonNull String name) {
        String fileName = getName();
        if (fileName.contains("."))
            fileName = getName().substring(0, getName().indexOf("."));
        if (name.contains("."))
            name = name.substring(0, name.indexOf("."));
        return fileName.compareToIgnoreCase(name);
    }

    public boolean isEmpty() {
        return file == null;
    }
}
