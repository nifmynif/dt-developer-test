package com.vizor.test.controller;

import com.vizor.test.module.ImagesHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainWindowController {
    public TextField searchField;
    public Button searchButton;
    public ImageView prev;
    public ImageView cur;
    public ImageView next;
    public Button add;
    public Label nextLabel;
    public Label curLabel;
    public Label prevLabel;
    public Label errorLabel;
    private ImagesHandler imagesHandler;
    private ImageController imageController;

    @FXML
    public void initialize() {
        imageController = new ImageController();
        imagesHandler = imageController.getImage();
        setImages("0a84326a-258e-410d-acb0-75d143de2fce.png");
        next.setOnMouseClicked(event -> chooseRight());
        prev.setOnMouseClicked(event -> chooseLeft());
    }

    private void chooseLeft() {
        setImages(imagesHandler.getPrev().getName());
    }

    private void chooseRight() {
        setImages(imagesHandler.getNext().getName());
    }

    private void setImages(String name) {
        imageController.getImage(name);
        updateImages();
    }

    private void updateImages() {
        prev.setImage(imagesHandler.getPrev().getImage());
        prevLabel.setText(String.valueOf(imagesHandler.getPrev().getPos()));
        cur.setImage(imagesHandler.getCur().getImage());
        curLabel.setText(String.valueOf(imagesHandler.getCur().getPos()));
        next.setImage(imagesHandler.getNext().getImage());
        nextLabel.setText(String.valueOf(imagesHandler.getNext().getPos()));
    }

    public void addButtonPress() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор файла");
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        imageController.saveImage(selectedFile);
        setImages(selectedFile.getName());
    }

    public void searchButtonPress() {
        imageController.getImage(searchField.getText());
        updateImages();
    }
}