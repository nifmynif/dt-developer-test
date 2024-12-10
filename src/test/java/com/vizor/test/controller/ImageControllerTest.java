package com.vizor.test.controller;

import com.vizor.test.constants.ConstantsError;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class ImageControllerTest {
    private static ImageController imageController;
    private static String cur;
    private static File notFile;
    private static File nullFile;
    private static File newFile;

    @BeforeAll
    public static void getImageController() throws IOException {
        imageController = new ImageController("src/test/resources/assetsTest");
        cur = "0a84326a-258e-410d-acb0-75d143de2fce.png";
        notFile = new File("src/test/resources/IntelliJ IDEA Community Edition 2024.3.lnk");
        nullFile = new File("");
        newFile = new File("src/test/resources/1632938986199138400.jpg");
    }

    @BeforeEach
    public void getImage() {
        imageController.getImageByName(cur);
    }

    @Test
    void getImageByName() {
        assertEquals(cur, imageController.getCur().getName());
    }

    @Test
    void moveLeft() {
        assertEquals(leftClick(), imageController.getCur().getName());
        assertEquals(cur, imageController.getNext().getName());
        assertEquals(leftClick(), imageController.getCur().getName());
        assertEquals(leftClick(), imageController.getCur().getName());
        assertEquals(leftClick(), imageController.getCur().getName());
    }

    private String leftClick() {
        String prev = imageController.getPrev().getName();
        imageController.moveLeft();
        return prev;
    }

    @Test
    void moveRight() {
        assertEquals(rightClick(), imageController.getCur().getName());
        assertEquals(cur, imageController.getPrev().getName());
        assertEquals(rightClick(), imageController.getCur().getName());
        assertEquals(rightClick(), imageController.getCur().getName());
    }

    private String rightClick() {
        String next = imageController.getNext().getName();
        imageController.moveRight();
        return next;
    }

    @Test
    void saveImage() throws IOException {
        imageController.saveImage(newFile);
        imageController.getImageByName(newFile.getName());
        assertEquals(newFile.getName(), imageController.getCur().getName());
        imageController.deleteImage();
    }

    @Test
    void ErrorSaveNullFile() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> imageController.saveImage(nullFile));
        assertEquals(ConstantsError.FILE_NOT_EXIST + nullFile.getName(), exception.getMessage());
    }

    @Test
    void errorSaveInk() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> imageController.saveImage(notFile));
        assertEquals(ConstantsError.FILE_NOT_IMAGE + notFile.getName(), exception.getMessage());
    }

    @Test
    void errorGetImageByName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> imageController.getImageByName(nullFile.getName()));
        assertEquals(ConstantsError.IMAGE_NOT_EXIST + nullFile.getName(), exception.getMessage());
    }

    @Test
    void isFolderHasPics() {
        assertTrue(imageController.isFolderHasPics());
    }
}