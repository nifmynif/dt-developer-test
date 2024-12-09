package com.vizor.test.controller;

import com.vizor.test.exceptions.MyInterruptedExeprion;
import com.vizor.test.module.ImageDTO;
import com.vizor.test.module.ImagesHandler;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadController implements Callable<Void> {
    private final ImagesHandler imagesHandler;
    private List<Callable<Void>> tasks;
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public DownloadController(ImagesHandler imagesHandler) {
        this.imagesHandler = imagesHandler;
    }

    public static void close() {
        executorService.shutdown();
    }

    public void download() {
        createTasks();
        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MyInterruptedExeprion(e.getMessage());
        }
    }

    private void createTasks() {
        this.tasks = new ArrayList<>(5);
        addTask(imagesHandler.getPrePrev());
        addTask(imagesHandler.getPrev());
        addTask(imagesHandler.getCur());
        addTask(imagesHandler.getNext());
        addTask(imagesHandler.getPostNext());
    }

    private void addTask(ImageDTO imageDTO) {
        if (imageDTO != null) {
            tasks.add(() -> {
                try {
                    imageDTO.setImage();
                    return null;
                } catch (MalformedURLException e) {
                    throw new IllegalArgumentException(e);
                }
            });
        }
    }

    @Override
    public Void call() {
        return null;
    }
}
