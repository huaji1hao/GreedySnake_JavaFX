package snake.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;


public class PhoneController {
    @FXML
    private Label numberDisplay;
    @FXML
    private Button callButton;


    @FXML
    private ImageView logo;


    @FXML
    private void exitButtonCLick() {
        System.exit(0);
    }
}
