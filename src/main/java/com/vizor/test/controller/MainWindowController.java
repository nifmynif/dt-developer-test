package com.vizor.test.controller;

import com.vizor.test.constants.Constants;
import com.vizor.test.module.ImageDTO;
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
    public Button del;
    private ImageController imageController;

    @FXML
    public void initialize() {
        try {
            imageController = new ImageController(Constants.MAIN_FOLDER);
            if (imageController.isFolderHasPics())
                updateImages();
            next.setOnMouseClicked(event -> chooseRight());
            cur.setOnMouseClicked(event -> ImageFullWindowController.openFullScreen(imageController.getCur()));
            prev.setOnMouseClicked(event -> chooseLeft());
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void updateImages() {
        if (imageController.getPrev() != null)
            fill(prev, prevLabel, imageController.getPrev());
        if (imageController.getCur() != null)
            fill(cur, curLabel, imageController.getCur());
        else {
            fill(cur, curLabel, new ImageDTO());
            fill(prev, prevLabel, new ImageDTO());
            fill(next, nextLabel, new ImageDTO());
        }
        if (imageController.getNext() != null)
            fill(next, nextLabel, imageController.getNext());
    }

    private void fill(ImageView imageView, Label label, ImageDTO imageDTO) {
        if (imageDTO.isEmpty()) {
            imageView.setImage(null);
            label.setText(null);
        } else {
            imageView.setImage(imageDTO.getImage());
            label.setText(String.valueOf(
                    imageController.getIndexByFileName(imageDTO.getName()) + 1));
        }
    }

    private void chooseRight() {
        imageController.moveRight();
        updateImages();
    }

    private void chooseLeft() {
        imageController.moveLeft();
        updateImages();
    }

    public void addButtonPress() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выбор файла");
            Stage stage = new Stage();
            File selectedFile = fileChooser.showOpenDialog(stage);
            imageController.saveImage(selectedFile);
            updateImages();
            errorLabel.setText("");
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void searchButtonPress() {
        try {
            imageController.getImageByName(searchField.getText());
            updateImages();
            errorLabel.setText("");
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void delButtonPress() throws IOException {
        if (imageController.getCur() != null) {
            imageController.deleteImage();
            updateImages();
        }
    }
}