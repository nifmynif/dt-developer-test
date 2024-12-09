package com.vizor.test.controller;

import com.vizor.test.constants.Constants;
import com.vizor.test.constants.ConstantsError;
import com.vizor.test.module.ImageDTO;
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
    private static final ImageService imageService = new ImageService();

    public ImageController() throws IOException {
        initialize();
        if (isFolderHasPics())
            getImage(imageService.getImageByIndex(0).getName());
    }

    public void initialize() throws IOException {
        File folder = new File(Constants.MAIN_FOLDER);
        if (!folder.exists() && !folder.mkdirs())
            throw new IOException(ConstantsError.FOLDER_CREATE_ERROR);
        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .forEach(this::addImage);
    }

    private void addImage(File file) {
        checkImage(file);
        try {
            imageService.addImage(file);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void checkImage(File file) throws IllegalArgumentException {
        String fileName = file.getName();
        AtomicBoolean isImage = new AtomicBoolean(false);
        if (!file.isFile() ||
                !file.exists())
            throw new IllegalArgumentException(ConstantsError.FILE_NOT_EXIST + fileName);
        Constants.EXTENSION_IMAGE.forEach(ext -> {
            if (fileName.toLowerCase().endsWith(ext))
                isImage.set(true);
        });
        if (!isImage.get())
            throw new IllegalArgumentException(ConstantsError.FILE_NOT_IMAGE + fileName);
    }

    public void getImage(String fileName) {
        if (fileName.contains("."))
            fileName = fileName.substring(0, fileName.indexOf("."));
        OptionalInt index = imageService.getIndexByFileName(fileName);
        if (index.isPresent()) {
            imageService.setCur(imageService.getImageByIndex(index.getAsInt()));
            setPrev(index.getAsInt());
            setNext(index.getAsInt());
        } else
            throw new IllegalArgumentException(ConstantsError.IMAGE_NOT_EXIST + fileName);
    }

    public void setPrev(int index) {
        if (index - 1 >= 0)
            imageService.setPrev(imageService.getImageByIndex(index - 1));
        else
            imageService.setPrev(imageService.getImageByIndex(imageService.size() - 1));
    }

    public void setNext(int index) {
        if (index + 1 < imageService.size())
            imageService.setNext(imageService.getImageByIndex(index + 1));
        else
            imageService.setNext(imageService.getImageByIndex(0));
    }

    public void saveImage(File file) throws IOException {
        addImage(file);
        File destinationFile = new File(Constants.MAIN_FOLDER, file.getName());
        Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public boolean isFolderHasPics() {
        return imageService.size() != 0;
    }

    public ImageDTO getCur() {
        return imageService.getCur();
    }

    public ImageDTO getPrev() {
        return imageService.getPrev();
    }

    public ImageDTO getNext() {
        return imageService.getNext();
    }
}
