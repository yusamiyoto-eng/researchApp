package com.mycompany.mavenproject1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController {
    
    @FXML
    private TableView<Coefficients> coeffTableView;
    
    @FXML
    private TableColumn<Coefficients, Integer> idColumn;
    
    @FXML
    private TableColumn<Coefficients, String> materialColumn;
    
    @FXML
    private TableColumn<Coefficients, Double> b0Column;
    
    @FXML
    private TableColumn<Coefficients, Double> b1Column;
    
    @FXML
    private TableColumn<Coefficients, Double> b2Column;
    
    @FXML
    private TableColumn<Coefficients, Double> b3Column;
    
    @FXML
    private TableColumn<Coefficients, Double> b4Column;
    
    @FXML
    private TableColumn<Coefficients, Double> b5Column;
    
    @FXML
    private TableColumn<Coefficients, Double> b6Column;
    
    @FXML
    private TableColumn<Coefficients, Double> b7Column;
    
    @FXML
    private TableColumn<Coefficients, Double> b8Column;

    @FXML
    void initialize(){
    coeffTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    coeffTableView.setPlaceholder(new Label("Нет данных"));  
    coeffTableView.setRowFactory(tv -> {
        TableRow<Coefficients> row = new TableRow<>();

        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                
                Coefficients coeffData = row.getItem();
                try {
                    App.setRoot("update", coeffData);
                } catch (IOException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return row ;    
        });

    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
    b0Column.setCellValueFactory(new PropertyValueFactory<>("b0"));
    b1Column.setCellValueFactory(new PropertyValueFactory<>("b1"));
    b2Column.setCellValueFactory(new PropertyValueFactory<>("b2"));
    b3Column.setCellValueFactory(new PropertyValueFactory<>("b3"));
    b4Column.setCellValueFactory(new PropertyValueFactory<>("b4"));
    b5Column.setCellValueFactory(new PropertyValueFactory<>("b5"));
    b6Column.setCellValueFactory(new PropertyValueFactory<>("b6"));
    b7Column.setCellValueFactory(new PropertyValueFactory<>("b7"));
    b8Column.setCellValueFactory(new PropertyValueFactory<>("b8"));
    
        try {
            List<Coefficients> rows = DBManager.selectAll();
            for (Coefficients coeff : rows) {
            coeffTableView.getItems().add(coeff);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    private void exit() throws IOException {
        App.setRoot("authorization");
    }
    
    @FXML
    private void createRow() throws IOException {
        App.setRoot("create");
    }
    
    @FXML
    private void changeAuthData() throws IOException {
        App.setRoot("authdata");
    }
    
    @FXML
    private void deleteRow() {
    Coefficients selectedCoeff = coeffTableView.getSelectionModel().getSelectedItem();
    if (selectedCoeff != null) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Подтверждение удаления");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Вы уверены, что хотите удалить материал \"" + selectedCoeff.getMaterial() + "\"?");
        
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            coeffTableView.getItems().remove(selectedCoeff);
            try {
                DBManager.delete(selectedCoeff);
                ShowAlert.showAlert(Alert.AlertType.INFORMATION, "Успех", "Запись успешно удалена!");
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                ShowAlert.showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось удалить запись из базы данных.");
            }
        }
    } else {
        ShowAlert.showAlert(Alert.AlertType.INFORMATION, "Информация", "Выделите строку таблицы для удаления!");
    }
}
    
}