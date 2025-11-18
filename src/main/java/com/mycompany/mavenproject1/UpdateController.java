package com.mycompany.mavenproject1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class UpdateController {
    
    @FXML
    private TextField materialTextField;
    
    @FXML
    private TextField idTextField;
    
    @FXML
    private TextField b0TextField;
    
    @FXML
    private TextField b1TextField;
    
    @FXML
    private TextField b2TextField;
    
    @FXML
    private TextField b3TextField;
    
    @FXML
    private TextField b4TextField;
    
    @FXML
    private TextField b5TextField;
    
    @FXML
    private TextField b6TextField;
    
    @FXML
    private TextField b7TextField;
    
    @FXML
    private TextField b8TextField;

    @FXML
    void initialize(){
        Coefficients coeff = (Coefficients) App.getData();
        
        idTextField.setText(String.valueOf(coeff.getId()));
        materialTextField.setText(coeff.getMaterial());
        b0TextField.setText(String.valueOf(coeff.getB0()));
        b1TextField.setText(String.valueOf(coeff.getB1()));
        b2TextField.setText(String.valueOf(coeff.getB2()));
        b3TextField.setText(String.valueOf(coeff.getB3()));
        b4TextField.setText(String.valueOf(coeff.getB4()));
        b5TextField.setText(String.valueOf(coeff.getB5()));
        b6TextField.setText(String.valueOf(coeff.getB6()));
        b7TextField.setText(String.valueOf(coeff.getB7()));
        b8TextField.setText(String.valueOf(coeff.getB8())); 
    }
    
    @FXML
    private void finishUpdate() throws IOException {
        
        Coefficients coeff = new Coefficients();
        try{
            coeff.setId(Integer.parseInt(idTextField.getText()));

            if (!materialTextField.getText().equals("")) {
                coeff.setMaterial(materialTextField.getText());
                coeff.setB0(Double.parseDouble(b0TextField.getText()));
                coeff.setB1(Double.parseDouble(b1TextField.getText()));
                coeff.setB2(Double.parseDouble(b2TextField.getText()));
                coeff.setB3(Double.parseDouble(b3TextField.getText()));
                coeff.setB4(Double.parseDouble(b4TextField.getText()));
                coeff.setB5(Double.parseDouble(b5TextField.getText()));
                coeff.setB6(Double.parseDouble(b6TextField.getText()));
                coeff.setB7(Double.parseDouble(b7TextField.getText()));
                coeff.setB8(Double.parseDouble(b8TextField.getText()));

                DBManager.update(coeff);
                App.setRoot("admin");

            } else ShowAlert.showAlert(Alert.AlertType.ERROR, "Некорректный ввод данных!", "Введите название материала!");
        
        } catch (NumberFormatException ex){
            ShowAlert.showAlert(Alert.AlertType.ERROR, "Некорректный ввод данных!", "Введите числовые значения для коэффициентов!");
        } catch (SQLException ex) {
            Logger.getLogger(UpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    @FXML
    private void exit() throws IOException{
    App.setRoot("admin");
    }
    
}
