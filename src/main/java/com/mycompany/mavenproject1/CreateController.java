package com.mycompany.mavenproject1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class CreateController {
    @FXML
    private TextField materialTextField;
    
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
    private void finishCreate() throws IOException {
        Coefficients coeff = new Coefficients();
        coeff.setId(0);
        try {
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
                List<Coefficients> list = DBManager.selectAll();
                
                for (int i = 0; i < list.size(); i++){
                if (coeff.getMaterial().equals(list.get(i).getMaterial())){
                    ShowAlert.showAlert(Alert.AlertType.ERROR, "Ошибка!", "Материал " + coeff.getMaterial() + " уже есть в базе!");
                    return;
                }
                }
                DBManager.insert(coeff);
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
