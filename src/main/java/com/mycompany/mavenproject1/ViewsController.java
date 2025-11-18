package com.mycompany.mavenproject1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.stage.FileChooser;
import java.io.FileOutputStream;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;



public class ViewsController  {
    @FXML
    private LineChart<Number, Number> lineChartTauFixed;
    @FXML
    private NumberAxis xAxisTauFixed;
    @FXML
    private NumberAxis yAxisTauFixed;
    @FXML
    private LineChart<Number, Number> lineChartTFixed;
    @FXML
    private NumberAxis xAxisTFixed;
    @FXML
    private NumberAxis yAxisTFixed;
    @FXML
    private TableView<ObservableList<Double>> tauFixedTableView;
    @FXML
    private TableView<ObservableList<Double>> TFixedTableView;
    @FXML
    private Label timeLabel;
    @FXML
    private Label memoryLabel;

    private ResearchData researchData;
    private ArrayList<Double> list1, list2, list3, list4, list5, list6;
    
    @FXML
    void initialize() throws IOException{
        try {
            tauFixedTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            TFixedTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
 
            researchData = (ResearchData) App.getData();
        
            if (researchData != null) {
                String material = researchData.getMaterial();
                int tauMin = researchData.getTauMin();
                int tauMax = researchData.getTauMax();
                int deltaTau = researchData.getDeltaTau();
                int Tmin = researchData.getTmin();
                int Tmax = researchData.getTmax();
                int deltaT = researchData.getDeltaT();

                System.gc();
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                long memoryBefore = getUsedMemory();
                long startTime = System.nanoTime();

                ArrayList<ArrayList<Double>> resultList = Calculation.calculate(tauMin, tauMax, deltaTau, Tmin, Tmax, deltaT, material);

                long endTime = System.nanoTime();
                double durationSeconds = (endTime - startTime) / 1000000000.0;

                long memoryAfter = getUsedMemory();
                double memoryUsedMB = (memoryAfter - memoryBefore) / (1024.0 * 1024.0);

                String timeText = String.format("%.3f с", durationSeconds);
                String memoryText = String.format("%.2f МБ", memoryUsedMB);

                timeLabel.setText(timeText);
                memoryLabel.setText(memoryText);

                if (resultList == null) {
                    ShowAlert.showAlert(Alert.AlertType.ERROR, "Ошибка получения данных!", "Указанный материал не найден.");
                } 

                list1 = resultList.get(0);
                list2 = resultList.get(1);
                list3 = resultList.get(2);
                list4 = resultList.get(3);
                list5 = resultList.get(4);
                list6 = resultList.get(5);


                List<String> temperatures = new ArrayList<>();
                for (int i = Tmin; i <= Tmax; i+= deltaT) {
                    temperatures.add(String.valueOf(i));
                }

                List<String> taus = new ArrayList<>();
                for (int i = tauMin; i <= tauMax; i+= deltaTau) {
                    taus.add(String.valueOf(i));
                }

                for (int i = 0; i < temperatures.size(); i++) {
                    final int index = i;
                    TableColumn<ObservableList<Double>, Double> column1 = new TableColumn<>(temperatures.get(i));

                    column1.setCellValueFactory(cellData -> {
                        List<Double> rowData = cellData.getValue();
                        return new ReadOnlyObjectWrapper<>(rowData.get(index));
                    });

                    tauFixedTableView.getColumns().add(column1);
                }
                
                tauFixedTableView.getItems().add(FXCollections.observableArrayList(list1));
                tauFixedTableView.getItems().add(FXCollections.observableArrayList(list2));
                tauFixedTableView.getItems().add(FXCollections.observableArrayList(list3));

                for (int i = 0; i < taus.size(); i++) {
                    final int index = i;
                    TableColumn<ObservableList<Double>, Double> column2 = new TableColumn<>(taus.get(i));
                    column2.setCellValueFactory(cellData -> {
                        List<Double> rowData = cellData.getValue();
                        return new ReadOnlyObjectWrapper<>(rowData.get(index));
                    });
                    TFixedTableView.getColumns().add(column2);

                }
                TFixedTableView.getItems().add(FXCollections.observableArrayList(list4));
                TFixedTableView.getItems().add(FXCollections.observableArrayList(list5));
                TFixedTableView.getItems().add(FXCollections.observableArrayList(list6));


                xAxisTauFixed.setForceZeroInRange(false);
                yAxisTauFixed.setForceZeroInRange(false);
                xAxisTFixed.setForceZeroInRange(false);
                yAxisTFixed.setForceZeroInRange(false);

                lineChartTauFixed.setTitle("Зависимость Hr от T");
                lineChartTauFixed.setCreateSymbols(false);

                XYChart.Series tauMinSeries = new XYChart.Series();
                XYChart.Series tauAverageSeries = new XYChart.Series();
                XYChart.Series tauMaxSeries = new XYChart.Series();
                
                for (int i = 0; i < list1.size(); i++){
                    tauMinSeries.getData().add(new Data(Tmin + i * deltaT, list1.get(i)));
                    tauAverageSeries.getData().add(new Data(Tmin + i * deltaT, list2.get(i)));
                    tauMaxSeries.getData().add(new Data(Tmin + i * deltaT, list3.get(i)));
                }
                
                tauMinSeries.setName("τ min");
                tauAverageSeries.setName("τ средний");
                tauMaxSeries.setName("τ max");
                
                lineChartTauFixed.getData().add(tauMinSeries);
                lineChartTauFixed.getData().add(tauAverageSeries);
                lineChartTauFixed.getData().add(tauMaxSeries);

                lineChartTFixed.setTitle("Зависимость Hr от τ");
                lineChartTFixed.setCreateSymbols(false);

                XYChart.Series TminSeries = new XYChart.Series();
                XYChart.Series TAverageSeries = new XYChart.Series();
                XYChart.Series TmaxSeries = new XYChart.Series();

                for (int i = 0; i < list4.size(); i++){
                    TminSeries.getData().add(new Data(tauMin + i * deltaTau, list4.get(i)));
                    TAverageSeries.getData().add(new Data(tauMin + i * deltaTau, list5.get(i)));
                    TmaxSeries.getData().add(new Data(tauMin + i * deltaTau, list6.get(i)));
                }

                TminSeries.setName("T min");
                TAverageSeries.setName("T средний");
                TmaxSeries.setName("T max");
                lineChartTFixed.getData().add(TminSeries);
                lineChartTFixed.getData().add(TAverageSeries);
                lineChartTFixed.getData().add(TmaxSeries);
        }
        } catch (SQLException ex) {
            Logger.getLogger(ViewsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML
    private void exit() throws IOException{
    App.setRoot("researcher");
    }
    
    @FXML
    private void createReport() {
        if (researchData == null) {
            ShowAlert.showAlert(Alert.AlertType.ERROR, "Ошибка", "Нет данных для формирования отчета");
            return;
        }
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить отчет Excel");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        fileChooser.setInitialFileName("Отчет_исследования_" + researchData.getMaterial() + ".xlsx");
        
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                generateExcelReport(file);
                ShowAlert.showAlert(Alert.AlertType.INFORMATION, "Успех", "Отчет успешно сохранен: " + file.getAbsolutePath());
            } catch (Exception e) {
                ShowAlert.showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось сохранить отчет: " + e.getMessage());
            }
        }
    }
    
    private void generateExcelReport(File file) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.RIGHT);
            
            Sheet inputSheet = workbook.createSheet("Исходные данные");
            
            Row headerRow = inputSheet.createRow(0);
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Исходные параметры исследования");
            headerCell.setCellStyle(headerStyle);
            
            int rowNum = 2;
            createDataRow(inputSheet, rowNum++, "Материал:", researchData.getMaterial(), headerStyle, dataStyle);
            createDataRow(inputSheet, rowNum++, "τ min (мин):", String.valueOf(researchData.getTauMin()), headerStyle, dataStyle);
            createDataRow(inputSheet, rowNum++, "τ max (мин):", String.valueOf(researchData.getTauMax()), headerStyle, dataStyle);
            createDataRow(inputSheet, rowNum++, "Δτ (мин):", String.valueOf(researchData.getDeltaTau()), headerStyle, dataStyle);
            createDataRow(inputSheet, rowNum++, "T min (°C):", String.valueOf(researchData.getTmin()), headerStyle, dataStyle);
            createDataRow(inputSheet, rowNum++, "T max (°C):", String.valueOf(researchData.getTmax()), headerStyle, dataStyle);
            createDataRow(inputSheet, rowNum++, "ΔT (°C):", String.valueOf(researchData.getDeltaT()), headerStyle, dataStyle);
            
            Sheet tauFixedSheet = workbook.createSheet("Hr от T (τ фикс)");
            createResultsSheet(tauFixedSheet, "Зависимость Hr от T при фиксированном τ", 
                             researchData.getTmin(), researchData.getTmax(), researchData.getDeltaT(),
                             list1, list2, list3, "τ min", "τ ср", "τ max", headerStyle, dataStyle);
            
            Sheet tFixedSheet = workbook.createSheet("Hr от τ (T фикс)");
            createResultsSheet(tFixedSheet, "Зависимость Hr от τ при фиксированном T",
                             researchData.getTauMin(), researchData.getTauMax(), researchData.getDeltaTau(),
                             list4, list5, list6, "T min", "T ср", "T max", headerStyle, dataStyle);
            
            for (int i = 0; i < 3; i++) {
                inputSheet.autoSizeColumn(i);
                tauFixedSheet.autoSizeColumn(i);
                tFixedSheet.autoSizeColumn(i);
            }
            
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }
        }
    }
    
    private void createDataRow(Sheet sheet, int rowNum, String parameter, String value, 
                             CellStyle headerStyle, CellStyle dataStyle) {
        Row row = sheet.createRow(rowNum);
        Cell paramCell = row.createCell(0);
        paramCell.setCellValue(parameter);
        paramCell.setCellStyle(headerStyle);
        
        Cell valueCell = row.createCell(1);
        valueCell.setCellValue(value);
        valueCell.setCellStyle(dataStyle);
    }

    private void createResultsSheet(Sheet sheet, String title, int start, int end, int step,
                                  ArrayList<Double> data1, ArrayList<Double> data2, ArrayList<Double> data3,
                                  String series1Name, String series2Name, String series3Name,
                                  CellStyle headerStyle, CellStyle dataStyle) {
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue(title);
        headerCell.setCellStyle(headerStyle);

        Row columnsRow = sheet.createRow(2);
        Cell paramCell = columnsRow.createCell(0);
        paramCell.setCellValue("Параметр");
        paramCell.setCellStyle(headerStyle);
        
        Cell series1Cell = columnsRow.createCell(1);
        series1Cell.setCellValue(series1Name);
        series1Cell.setCellStyle(headerStyle);
        
        Cell series2Cell = columnsRow.createCell(2);
        series2Cell.setCellValue(series2Name);
        series2Cell.setCellStyle(headerStyle);
        
        Cell series3Cell = columnsRow.createCell(3);
        series3Cell.setCellValue(series3Name);
        series3Cell.setCellStyle(headerStyle);
        
        int rowNum = 3;
        for (int i = 0; i < data1.size(); i++) {
            Row dataRow = sheet.createRow(rowNum++);
            
            Cell paramNameCell = dataRow.createCell(0);
            paramNameCell.setCellValue(start + i * step);
            paramNameCell.setCellStyle(dataStyle);
            
            Cell data1Cell = dataRow.createCell(1);
            data1Cell.setCellValue(data1.get(i));
            data1Cell.setCellStyle(dataStyle);
            
            Cell data2Cell = dataRow.createCell(2);
            data2Cell.setCellValue(data2.get(i));
            data2Cell.setCellStyle(dataStyle);
            
            Cell data3Cell = dataRow.createCell(3);
            data3Cell.setCellValue(data3.get(i));
            data3Cell.setCellStyle(dataStyle);
        }
    }
    
    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
