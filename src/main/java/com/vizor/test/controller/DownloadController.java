package com.vizor.test.controller;

import com.vizor.test.module.ImagesHandler;
import lombok.AllArgsConstructor;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
public class DownloadController implements Runnable {
    private ImagesHandler imagesHandler;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void download() {
        executorService.execute(this);
    }

    public void close() {
        executorService.shutdown();
    }

    public void run() {
        try {
            imagesHandler.getPrePrev().setImage();
            imagesHandler.getPrev().setImage();
            imagesHandler.getCur().setImage();
            imagesHandler.getNext().setImage();
            imagesHandler.getPostNext().setImage();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
