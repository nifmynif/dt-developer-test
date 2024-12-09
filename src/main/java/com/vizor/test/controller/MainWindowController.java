package com.vizor.test.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
    private ImageController imageController;

    @FXML
    public void initialize() {
        try {
            imageController = new ImageController();
            if (imageController.isFolderHasPics())
                updateImages();
            next.setOnMouseClicked(event -> chooseRight());
            cur.setOnMouseClicked(event -> ImageFullWindowController.openFullScreen(imageController.getCur()));
            prev.setOnMouseClicked(event -> chooseLeft());
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void setImages(String name) {
        imageController.getImage(name);
        updateImages();
    }

    private void updateImages() {
        prev.setImage(imageController.getPrev().getImage());
        prevLabel.setText(String.valueOf(imageController.getPrev().getPos()));
        cur.setImage(imageController.getCur().getImage());
        curLabel.setText(String.valueOf(imageController.getCur().getPos()));
        next.setImage(imageController.getNext().getImage());
        nextLabel.setText(String.valueOf(imageController.getNext().getPos()));
    }

    private void chooseRight() {
        setImages(imageController.getNext().getName());
    }

    private void chooseLeft() {
        setImages(imageController.getPrev().getName());
    }

    public void addButtonPress() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выбор файла");
            Stage stage = new Stage();
            File selectedFile = fileChooser.showOpenDialog(stage);
            imageController.saveImage(selectedFile);
            setImages(selectedFile.getName());
            errorLabel.setText("");
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void searchButtonPress() {
        try {
            imageController.getImage(searchField.getText());
            updateImages();
            errorLabel.setText("");
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }
}