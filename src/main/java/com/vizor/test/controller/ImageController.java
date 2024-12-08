package com.vizor.test.controller;

import com.vizor.test.ImageService;
import com.vizor.test.module.ImagesHandler;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.OptionalInt;

public class ImageController {
    public static final ImageService imageService = new ImageService();

    public ImageController() {
        initialize();
    }

    public void initialize() {
        File folder = new File("assets");
        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .forEach(file -> {
                    checkImage(file);
                    try {
                        imageService.addImage(file);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void getImage(String fileName) {
        OptionalInt index = imageService.getIndexByFileName(fileName);
        if (index.isPresent()) {
            imageService.setCur(imageService.getImageByIndex(index.getAsInt()));
            if (index.getAsInt() - 1 >= 0)
                imageService.setPrev(imageService.getImageByIndex(index.getAsInt() - 1));
            if (index.getAsInt() + 1 < imageService.size())
                imageService.setNext(imageService.getImageByIndex(index.getAsInt() + 1));
        }
    }

    private void checkImage(File file) throws IllegalArgumentException {
        String fileName = file.getName();
        if (!file.isFile() ||
                !file.exists())
            throw new IllegalArgumentException("Файл не существует или это не файл: " + fileName);

        if (!fileName.endsWith(".jpg") &&
                !fileName.endsWith(".jpeg") &&
                !fileName.endsWith(".png"))
            throw new IllegalArgumentException("Это не картинка: " + fileName);
    }

    public ImagesHandler getImage() {
        return imageService.getImage();
    }
}
