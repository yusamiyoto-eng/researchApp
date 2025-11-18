package com.mycompany.mavenproject1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class AuthorizationController {
    private String adminLogin = "";
    private String adminPassword = "";
    private String researcherLogin = "";
    private String researcherPassword = "";
    
   @FXML
   private TextField loginTextField;
   @FXML
   private TextField passwordTextField;
   
    @FXML
    void initialize(){
        File file = new File("./AuthData.xlsx");
        
                   if (file.exists()){
                   try (FileInputStream in = new FileInputStream(file)){
                       
                       Workbook workbook = WorkbookFactory.create(in);
                       Sheet sheet = workbook.getSheetAt(0);
                       
                            Row row = sheet.getRow(0);
                            adminLogin = row.getCell(0).getStringCellValue();
                            adminPassword = row.getCell(1).getStringCellValue();
                           
                            row = sheet.getRow(1);
                            researcherLogin = row.getCell(0).getStringCellValue();
                            researcherPassword = row.getCell(1).getStringCellValue();
                            
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        ShowAlert.showAlert(Alert.AlertType.ERROR, "Ошибка доступа!", "Невозможно получить данные авторизации!");
                    }
    }

    @FXML
    private void authorization() throws IOException {   
        if (loginTextField.getText().equals(adminLogin) && passwordTextField.getText().equals(adminPassword)){
            App.setRoot("admin");
        } else {
        if (loginTextField.getText().equals(researcherLogin) && passwordTextField.getText().equals(researcherPassword)){
            App.setRoot("researcher");
        } else {
            ShowAlert.showAlert(Alert.AlertType.ERROR, "Ошибка авторизации!", "Неверный логин или пароль!");
        }
        }
    }
    
    @FXML
    private void exit() {
    Platform.exit();
    }
    
}
