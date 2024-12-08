package com.vizor.test;

import com.vizor.test.module.ImageDTO;
import com.vizor.test.module.ImagesHandler;
import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ImageService {
    public static final ImagesHandler images = new ImagesHandler();

    public OptionalInt getIndexByFileName(String fileName) {
        return IntStream.range(0, images.getImages().size())
                .filter(i -> images.getImages().get(i).getName().equalsIgnoreCase(fileName))
                .findFirst();
    }

    public ImageDTO getImageByIndex(int index) {
        return images.getImages().get(index);
    }

    public void setPrev(ImageDTO image) {
        images.setPrev(image);
    }

    public void setNext(ImageDTO image) {
        images.setNext(image);
    }

    public void setCur(ImageDTO image) {
        images.setCur(image);
    }

    public void addImage(File file) throws MalformedURLException {
        ImageDTO imageDTO = new ImageDTO(file.getName(), new Image(file.toURI().toURL().toExternalForm()));
        images.getImages().add(imageDTO);
    }

    public int size() {
        return images.getImages().size();
    }

    public ImagesHandler getImage() {
        return images;
    }
}
