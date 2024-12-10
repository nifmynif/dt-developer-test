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
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageController {
    private ImageService imageService;
    private final DownloadController downloadController;
    private final String folderPath;

    public ImageController(String folderPath) throws IOException {
        this.folderPath = folderPath;
        initialize(folderPath);
        downloadController = new DownloadController(imageService.getImages());
        if (isFolderHasPics())
            getImageByName(imageService.getImageByIndex(0).getName());
    }

    private void initialize(String folderPath) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.mkdirs()) {
            LogController.logError(ConstantsError.FOLDER_CREATE_ERROR, this);
            throw new IOException(ConstantsError.FOLDER_CREATE_ERROR);
        }
        imageService = new ImageService(Objects.requireNonNull(folder.listFiles()).length);
        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .forEach(file -> {
                    checkImage(file);
                    imageService.addImage(file);
                });
        LogController.logInfo("Массив заполнен " + imageService.size() + " элементов", this);
    }

    private void checkImage(File file) throws IllegalArgumentException {
        if (file == null || !file.isFile() || !file.exists()) {
            LogController.logWarn(ConstantsError.FILE_NOT_EXIST, this);
            throw new IllegalArgumentException(ConstantsError.FILE_NOT_EXIST);
        }
        String fileName = file.getName();
        AtomicBoolean isImage = new AtomicBoolean(false);
        Constants.EXTENSION_IMAGE.forEach(ext -> {
            if (fileName.toLowerCase().endsWith(ext))
                isImage.set(true);
        });
        if (!isImage.get()) {
            LogController.logWarn(ConstantsError.FILE_NOT_IMAGE + fileName, this);
            throw new IllegalArgumentException(ConstantsError.FILE_NOT_IMAGE + fileName);
        }
    }

    public void getImageByName(String fileName) {
        int index = getIndexByFileName(fileName);
        if (index >= 0) {
            imageService.setCur(imageService.getImageByIndex(index));
            setPrev(index);
            setNext(index);
        } else {
            LogController.logWarn(ConstantsError.IMAGE_NOT_EXIST + fileName, this);
            throw new IllegalArgumentException(ConstantsError.IMAGE_NOT_EXIST + fileName);
        }
        downloadController.download();
    }

    public int getIndexByFileName(String name) {
        return imageService.getIndexByFileName(name);
    }

    public void moveLeft() {
        imageService.setPostNext(imageService.getNext());
        imageService.setNext(imageService.getCur());
        imageService.setCur(imageService.getPrev());
        setPrev(imageService.getIndexByFileName(getPrev().getName()));
        downloadController.download();
    }

    public void moveRight() {
        imageService.setPrePrev(imageService.getPrev());
        imageService.setPrev(imageService.getCur());
        imageService.setCur(imageService.getNext());
        setNext(imageService.getIndexByFileName(getNext().getName()));
        downloadController.download();
    }

    private void setPrev(int index) {
        if (imageService.size() > 2) {
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
        if (imageService.size() == 2) {
            if (index == 0)
                imageService.setPrev(imageService.getImageByIndex(1));
            else
                imageService.setPrev(imageService.getImageByIndex(0));
        }
    }

    private void setNext(int index) {
        if (imageService.size() > 2) {
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
        } else if (imageService.size() == 2) {
            if (index == 0)
                imageService.setNext(imageService.getImageByIndex(1));
            else
                imageService.setNext(imageService.getImageByIndex(0));
        }
    }

    public void saveImage(File file) throws IOException {
        checkImage(file);
        try {
            getImageByName(file.getName());
        } catch (IllegalArgumentException e) {
            File destinationFile = new File(folderPath, file.getName());
            Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            imageService.addImage(destinationFile);
            LogController.logInfo(ConstantsError.PICTURE + file.getName() + " сохранена", this);
        }
        getImageByName(file.getName());
    }

    public void deleteImage() throws IOException {
        Files.delete(getCur().getFile().toPath());
        imageService.deleteImage(getCur());
        LogController.logInfo(ConstantsError.PICTURE + getCur().getName() + " удалена", this);
        imageService.setCur(imageService.getNext());
        setNext(imageService.getIndexByFileName(imageService.getPostNext().getName()) - 1);
        downloadController.download();
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
