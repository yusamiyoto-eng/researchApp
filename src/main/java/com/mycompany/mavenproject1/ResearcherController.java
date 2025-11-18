package com.mycompany.mavenproject1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ResearcherController {
    @FXML
    private TextField tauMinTextField;
    @FXML
    private TextField tauMaxTextField;
    @FXML
    private TextField deltaTauTextField;
    @FXML
    private TextField TminTextField;
    @FXML
    private TextField TmaxTextField;
    @FXML
    private TextField deltaTTextField;
    @FXML
    private ComboBox<String> materialComboBox;
    
    @FXML
    void initialize() throws SQLException{
        List<Coefficients> coeffList = DBManager.selectAll();
        List<String> materials = new ArrayList();
        for (int i=0; i < coeffList.size(); i++) {
            materials.add(coeffList.get(i).getMaterial());
        }
        materialComboBox.setItems(FXCollections.observableArrayList(materials));
        
        tauMinTextField.setText("50");
        tauMaxTextField.setText("70");
        deltaTauTextField.setText("1");
        TminTextField.setText("1200");
        TmaxTextField.setText("1400");
        deltaTTextField.setText("10");
    }

    @FXML
    public void calculate() throws IOException {
        int tauMin, tauMax, deltaTau, Tmin, Tmax, deltaT;
        String material;
 
        try {
            
            tauMin = Integer.parseInt(tauMinTextField.getText());
            tauMax = Integer.parseInt(tauMaxTextField.getText());
            deltaTau = Integer.parseInt(deltaTauTextField.getText());
            
            if (deltaTau < 0 || tauMin < 0 || tauMax < 0){
                ShowAlert.showAlert(Alert.AlertType.ERROR, "Некорректный ввод данных!", "τ min, τ max и Δτ должны быть больше нуля!");
                return;
            }
            if (tauMin >= tauMax) {
                ShowAlert.showAlert(Alert.AlertType.ERROR, "Некорректный ввод данных!", "τ min должно быть меньше, чем τ max!");
                return;
            }
            
            Tmin = Integer.parseInt(TminTextField.getText());
            Tmax = Integer.parseInt(TmaxTextField.getText());
            deltaT = Integer.parseInt(deltaTTextField.getText());
            
            if (deltaT < 0 || Tmin < 0 || Tmax < 0){
                ShowAlert.showAlert(Alert.AlertType.ERROR, "Некорректный ввод данных!", "T min, T max и ΔT должны быть больше нуля!");
                return;
            }
            
            if (Tmin >= Tmax) {
                ShowAlert.showAlert(Alert.AlertType.ERROR, "Некорректный ввод данных!", "T min должно быть меньше, чем T max!");
                return;
            }

            material = materialComboBox.getValue();
            
            if (materialComboBox.getSelectionModel().getSelectedIndex() == -1){
            ShowAlert.showAlert(Alert.AlertType.ERROR, "Некорректный ввод данных!", "Выберите материал!");
            return;
            }
            
            ResearchData researchData = new ResearchData(tauMin, tauMax, deltaTau, Tmin, Tmax, deltaT, material);

            App.setRoot("views", researchData);
            
            } catch (NumberFormatException ex){
            ShowAlert.showAlert(Alert.AlertType.ERROR, "Некорректный ввод данных!", "Введите числовые значения для характеристик объекта.");
        }
    }  
    
    @FXML
    private void exit() throws IOException{
    App.setRoot("authorization");
    }
    
}
