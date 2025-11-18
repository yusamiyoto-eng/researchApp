package com.mycompany.mavenproject1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AuthDataController {
   @FXML
   private TextField changeLoginTextField;
   @FXML
   private TextField changePasswordTextField;
   
   @FXML
   private ComboBox<String> userBox;
   
   @FXML
    void initialize(){
        
        userBox.setItems(FXCollections.observableArrayList("Админинстратор", "Исследователь"));
        
        userBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
               
                   File file = new File("./AuthData.xlsx");
                   
                   if (file.exists()){
                   try (FileInputStream in = new FileInputStream(file)){
                       
                       Workbook workbook = WorkbookFactory.create(in);
                       Sheet sheet = workbook.getSheetAt(0);
                       
                       if (t1.equals("Админинстратор")){
                            Row row = sheet.getRow(0);
                            if(row == null) {
                                changeLoginTextField.clear();
                                changePasswordTextField.clear();
                            } else {
                            changeLoginTextField.setText(row.getCell(0).getStringCellValue());
                            changePasswordTextField.setText(row.getCell(1).getStringCellValue());
                            }
                        }
                       
                        if(t1.equals("Исследователь")){
                            Row row = sheet.getRow(1);
                            if(row == null) {
                                changeLoginTextField.clear();
                                changePasswordTextField.clear();
                            } else {
                            changeLoginTextField.setText(row.getCell(0).getStringCellValue());
                            changePasswordTextField.setText(row.getCell(1).getStringCellValue());
                            }
                        }                     
                       
                        }   catch (FileNotFoundException ex) {
                           Logger.getLogger(AuthDataController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                           Logger.getLogger(AuthDataController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }
               } 
        });  
    }
    @FXML
    private void saveAuthData() throws IOException {
        if (userBox.getSelectionModel().getSelectedIndex() == -1){
            ShowAlert.showAlert(Alert.AlertType.ERROR, "Данные не введены!", "Выберите пользователя и введите логин и пароль!");
        } else {
        
        File file = new File("./AuthData.xlsx");
        Workbook workbook;
        Sheet sheet;
        
        if(file.exists()){
            
            try (FileInputStream in = new FileInputStream(file)) {
                workbook = WorkbookFactory.create(in);
                sheet = workbook.getSheetAt(0);
                }
        }
        
             else {
            
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet();
        }
        
            if(userBox.getValue().equals("Админинстратор")){
                Row adminRow = sheet.getRow(0);
                if (adminRow == null) adminRow = sheet.createRow(0);
                adminRow.createCell(0).setCellValue(changeLoginTextField.getText());
                adminRow.createCell(1).setCellValue(changePasswordTextField.getText());
                
            } else if (userBox.getValue().equals("Исследователь")) {
                Row researcherRow = sheet.getRow(1);
                if (researcherRow == null) researcherRow = sheet.createRow(1);
                researcherRow.createCell(0).setCellValue(changeLoginTextField.getText());
                researcherRow.createCell(1).setCellValue(changePasswordTextField.getText());
            }   
            
            try (FileOutputStream out = new FileOutputStream("./AuthData.xlsx")) {
                workbook.write(out);
            }
            App.setRoot("admin");
        }
        
        }
    
    @FXML
    private void exit() throws IOException{
    App.setRoot("admin");
    }
    }

