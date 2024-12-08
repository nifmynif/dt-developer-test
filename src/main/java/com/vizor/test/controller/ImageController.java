package com.vizor.test.controller;

import com.vizor.test.constants.Constants;
import com.vizor.test.constants.ConstantsError;
import com.vizor.test.module.ImagesHandler;
import com.vizor.test.service.ImageService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageController {
    public static final ImageService imageService = new ImageService();

    public ImageController() {
        initialize();
        if (!isEmpty())
            getImage(imageService.getImageByIndex(0).getName());
    }

    public void initialize() {
        File folder = new File(Constants.MAIN_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .forEach(this::addImage);
    }

    private void addImage(File file) {
        checkImage(file);
        try {
            imageService.addImage(file);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkImage(File file) throws IllegalArgumentException {
        String fileName = file.getName();
        AtomicBoolean isImage = new AtomicBoolean(false);
        if (!file.isFile() ||
                !file.exists())
            throw new IllegalArgumentException(ConstantsError.FILE_NOT_EXIST + fileName);
        Constants.EXTENSION_IMAGE.forEach(ext -> {
            if (fileName.endsWith(ext))
                isImage.set(true);
        });
        if (!isImage.get())
            throw new IllegalArgumentException(ConstantsError.FILE_NOT_IMAGE + fileName);
    }

    public ImagesHandler getImage() {
        return imageService.getImage();
    }

    public void getImage(String fileName) {
        OptionalInt index = imageService.getIndexByFileName(fileName);
        if (index.isPresent()) {
            imageService.setCur(imageService.getImageByIndex(index.getAsInt()));
            if (index.getAsInt() - 1 >= 0)
                imageService.setPrev(imageService.getImageByIndex(index.getAsInt() - 1));
            else
                imageService.setPrev(imageService.getImageByIndex(imageService.size() - 1));
            if (index.getAsInt() + 1 < imageService.size())
                imageService.setNext(imageService.getImageByIndex(index.getAsInt() + 1));
            else
                imageService.setNext(imageService.getImageByIndex(0));
        } else
            throw new IllegalArgumentException(ConstantsError.IMAGE_NOT_EXIST + fileName);
    }

    public void saveImage(File file) throws IOException {
        addImage(file);
        File destinationFile = new File(Constants.MAIN_FOLDER, file.getName());
        Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public boolean isEmpty() {
        return imageService.size() == 0;
    }
}
