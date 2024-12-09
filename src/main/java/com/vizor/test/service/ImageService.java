package com.vizor.test.service;

import com.vizor.test.module.ImageDTO;
import com.vizor.test.module.ImagesHandler;
import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ImageService {
    public static final ImagesHandler images = new ImagesHandler();

    public void addImage(File file) throws MalformedURLException {
        ImageDTO imageDTO = new ImageDTO(file.getName(), new Image(file.toURI().toURL().toExternalForm()), size() + 1);
        images.getImages().add(imageDTO);
    }

    public int size() {
        return images.getImages().size();
    }

    public ImagesHandler getImages() {
        return images;
    }

    public OptionalInt getIndexByFileName(String fileName) {
        return IntStream.range(0, images.getImages().size())
                .filter(i -> {
                    String name = getImageByIndex(i).getName();
                    name = name.substring(0, name.indexOf("."));
                    return name.equalsIgnoreCase(fileName);
                })
                .findFirst();
    }

    public ImageDTO getImageByIndex(int index) {
        return images.getImages().get(index);
    }

    public void setCur(ImageDTO image) {
        images.setCur(image);
    }

    public void setPrev(ImageDTO image) {
        images.setPrev(image);
    }

    public ImageDTO getNext() {
        return images.getNext();
    }

    public ImageDTO getCur() {
        return images.getCur();
    }

    public ImageDTO getPrev() {
        return images.getPrev();
    }

    public void setNext(ImageDTO image) {
        images.setNext(image);
    }
}
