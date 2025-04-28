package com.LinHao.core.util;

import com.LinHao.core.model.ExcelConfig;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//excelを読む

public class ExcelReader {
    public static List<ExcelConfig> readExcel(String excelPath) throws IOException{
        List<ExcelConfig> configs = new ArrayList<>();

        FileInputStream fis = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet){
            if (row.getRowNum() == 0){
                continue;
            }

            String number = getCellValueAsString(row.getCell(0));
            String productModel = getCellValueAsString(row.getCell(1));
            String productName = getCellValueAsString(row.getCell(2));
            String brandName = getCellValueAsString(row.getCell(3));

            if (number == null || number.isEmpty()){
                continue;
            }

            ExcelConfig config = new ExcelConfig(number, productModel, productName, brandName);
            configs.add(config);
        }

        workbook.close();
        fis.close();
        return configs;
    }

    private static String getCellValueAsString(Cell cell){
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING){
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC){
            return String.valueOf((int) cell.getNumericCellValue());
        } else {
            return "";
        }
    }
}
