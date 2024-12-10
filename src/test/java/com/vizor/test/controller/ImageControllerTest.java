package com.vizor.test.controller;

import com.vizor.test.constants.ConstantsError;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class ImageControllerTest {
    private static ImageController imageControllerWithPics;
    private static ImageController imageControllerWithoutPics;
    private static String cur;
    private static File notFile;
    private static File nullFile;
    private static File newFile;
    private static File newFileTwo;

    @BeforeAll
    public static void getImageController() throws IOException {
        imageControllerWithPics = new ImageController("src/test/resources/assetsTest");
        imageControllerWithoutPics = new ImageController("src/test/resources/assetsNull");
        cur = "0a84326a-258e-410d-acb0-75d143de2fce.png";
        notFile = new File("src/test/resources/IntelliJ IDEA Community Edition 2024.3.lnk");
        nullFile = new File("");
        newFile = new File("src/test/resources/1632938986199138400.jpg");
        newFileTwo = new File("src/test/resources/assetsTest/0a84326a-258e-410d-acb0-75d143de2fce.png");
    }

    @BeforeEach
    public void getImage() {
        imageControllerWithPics.getImageByName(cur);
    }

    @Test
    void getImageByName() {
        assertEquals(cur, imageControllerWithPics.getCur().getName());
    }

    @Test
    void moveLeft() {
        assertEquals(leftClick(), imageControllerWithPics.getCur().getName());
        assertEquals(cur, imageControllerWithPics.getNext().getName());
        assertEquals(leftClick(), imageControllerWithPics.getCur().getName());
        assertEquals(leftClick(), imageControllerWithPics.getCur().getName());
        assertEquals(leftClick(), imageControllerWithPics.getCur().getName());
    }

    private String leftClick() {
        String prev = imageControllerWithPics.getPrev().getName();
        imageControllerWithPics.moveLeft();
        return prev;
    }

    @Test
    void moveRight() {
        assertEquals(rightClick(), imageControllerWithPics.getCur().getName());
        assertEquals(cur, imageControllerWithPics.getPrev().getName());
        assertEquals(rightClick(), imageControllerWithPics.getCur().getName());
        assertEquals(rightClick(), imageControllerWithPics.getCur().getName());
    }

    private String rightClick() {
        String next = imageControllerWithPics.getNext().getName();
        imageControllerWithPics.moveRight();
        return next;
    }

    @Test
    void saveImage() throws IOException {
        imageControllerWithPics.saveImage(newFile);
        imageControllerWithPics.getImageByName(newFile.getName());
        assertEquals(newFile.getName(), imageControllerWithPics.getCur().getName());
        imageControllerWithPics.deleteImage();
    }

    @Test
    void ErrorSaveNullFile() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> imageControllerWithPics.saveImage(nullFile));
        assertEquals(ConstantsError.FILE_NOT_EXIST + nullFile.getName(), exception.getMessage());
    }

    @Test
    void errorSaveInk() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> imageControllerWithPics.saveImage(notFile));
        assertEquals(ConstantsError.FILE_NOT_IMAGE + notFile.getName(), exception.getMessage());
    }

    @Test
    void errorGetImageByName() {
        try {
            imageControllerWithPics.getImageByName(nullFile.getName());
        } catch (IllegalArgumentException e) {
            assertEquals(ConstantsError.IMAGE_NOT_EXIST + nullFile.getName(), e.getMessage());
        }
    }

    @Test
    void isFolderHasPics() {
        assertTrue(imageControllerWithPics.isFolderHasPics());
    }

    @Test
    void deleteLastPic() throws IOException {
        imageControllerWithoutPics = new ImageController("src/test/resources/assetsNull");
        imageControllerWithoutPics.saveImage(newFile);
        imageControllerWithoutPics.deleteImage();
        assertFalse(imageControllerWithoutPics.isFolderHasPics());
    }

    @Test
    void moveLastTwoPics() throws IOException {
        imageControllerWithoutPics.saveImage(newFile);
        imageControllerWithoutPics.saveImage(newFileTwo);
        assertEquals(leftClick(), imageControllerWithPics.getCur().getName());
        assertEquals(leftClick(), imageControllerWithPics.getCur().getName());
        assertEquals(rightClick(), imageControllerWithPics.getCur().getName());
        assertEquals(rightClick(), imageControllerWithPics.getCur().getName());
        imageControllerWithoutPics.deleteImage();
        imageControllerWithoutPics.deleteImage();
    }
}