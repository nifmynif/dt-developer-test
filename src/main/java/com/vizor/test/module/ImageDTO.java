package com.vizor.test.module;

import com.vizor.test.controller.LogController;
import javafx.scene.image.Image;
import lombok.*;

import java.io.File;
import java.net.MalformedURLException;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class ImageDTO implements Comparable<String> {
    private final File file;
    private Image image;

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
}
