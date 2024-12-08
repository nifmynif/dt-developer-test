package com.vizor.test.controller;

import com.vizor.test.module.ImagesHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MainWindowController {
    public TextField searchField;
    public Button searchButton;
    public ImageView prev;
    public ImageView cur;
    public ImageView next;
    public Button add;
    private ImagesHandler imagesHandler;

    @FXML
    public void initialize() {
        ImageController imageController = new ImageController();
        imagesHandler = imageController.getImage();
        searchButton.setOnAction(event -> {
            imageController.getImage(searchField.getText());
            updateImages();
        });
        next.setOnMouseClicked(event -> {
            imageController.getImage(imagesHandler.getNext().getName());
            updateImages();
        });
        prev.setOnMouseClicked(event -> {
            imageController.getImage(imagesHandler.getPrev().getName());
            updateImages();
        });
    }

    private void updateImages() {
        prev.setImage(imagesHandler.getPrev().getImage());
        cur.setImage(imagesHandler.getCur().getImage());
        next.setImage(imagesHandler.getNext().getImage());
    }
}