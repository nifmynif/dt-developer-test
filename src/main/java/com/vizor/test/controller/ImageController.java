package com.vizor.test.controller;

import com.vizor.test.constants.Constants;
import com.vizor.test.constants.ConstantsError;
import com.vizor.test.module.ImageDTO;
import com.vizor.test.service.ImageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageController {
    private static final ImageService imageService = new ImageService();
    DownloadController downloadController;

    public ImageController() throws IOException {
        initialize();
        downloadController = new DownloadController(imageService.getImages());
        if (isFolderHasPics())
            getImageByName(imageService.getImageByIndex(0).getName());
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
        imageService.addImage(file);
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

    public void getImageByName(String fileName) {
        if (fileName.contains("."))
            fileName = fileName.substring(0, fileName.indexOf("."));
        OptionalInt index = imageService.getIndexByFileName(fileName);
        if (index.isPresent()) {
            imageService.setCur(imageService.getImageByIndex(index.getAsInt()));
            setPrev(index.getAsInt());
            setNext(index.getAsInt());
        } else
            throw new IllegalArgumentException(ConstantsError.IMAGE_NOT_EXIST + fileName);
        downloadController.download();
    }

    public void moveLeft() {
        imageService.setPostNext(imageService.getNext());
        imageService.setNext(imageService.getCur());
        imageService.setCur(imageService.getPrev());
        setPrev(imageService.getPrev().getPos() - 1);
        downloadController.download();
    }

    public void moveRight() {
        imageService.setPrePrev(imageService.getPrev());
        imageService.setPrev(imageService.getCur());
        imageService.setCur(imageService.getNext());
        setNext(imageService.getNext().getPos() - 1);
        downloadController.download();
    }

    public void setPrev(int index) {
        if (index - 2 >= 0) {
            imageService.setPrePrev(imageService.getImageByIndex(index - 2));
            imageService.setPrev(imageService.getImageByIndex(index - 1));
        } else if (index - 1 >= 0) {
            imageService.setPrePrev(imageService.getImageByIndex(imageService.size() - 1));
            imageService.setPrev(imageService.getImageByIndex(0));
        } else {
            imageService.setPrePrev(imageService.getImageByIndex(imageService.size() - 2));
            imageService.setPrev(imageService.getImageByIndex(imageService.size() - 1));
        }
    }

    public void setNext(int index) {
        if (index + 2 < imageService.size()) {
            imageService.setNext(imageService.getImageByIndex(index + 1));
            imageService.setPostNext(imageService.getImageByIndex(index + 2));
        } else if (index + 1 < imageService.size()) {
            imageService.setNext(imageService.getImageByIndex(index + 1));
            imageService.setPostNext(imageService.getImageByIndex(0));
        } else {
            imageService.setNext(imageService.getImageByIndex(0));
            imageService.setPostNext(imageService.getImageByIndex(1));
        }
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
