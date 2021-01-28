package com.zfx.io;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jumpmind.symmetric.csv.CsvReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Test2 {
    public static void main(String[] args) throws IOException {
        String name= "D:\\zfx-demo-2021\\demo-fileIo\\src\\main\\java\\com\\zfx\\io\\nhdz_2021-01-25.csv";
//        //创建输入流
//        InputStream is=new FileInputStream(name);
//        //创建工作簿
//        XSSFWorkbook hb=new XSSFWorkbook(is);
//        //获取sheet页
//        XSSFSheet xh=hb.getSheet("xx");
//        //xh.getLastRowNum() 得到总行数
//        System.out.println("sheet行数:"+xh.getLastRowNum());
//        //xh.getRow(0) 获得0行 getCell(0) 0列数据 即第一个单元格
//        System.out.println(xh.getRow(0).getCell(0));
        csvToXLSX(name);
    }

    public static void csvToXLSX(String outputFilePath) {
        try {
            String csvFileAddress = outputFilePath; //csv file address
            String xlsxFileAddress = outputFilePath.replace("csv","xlsx"); //xlsx file address
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet(outputFilePath);
            sheet.setColumnWidth(35, 10000);
            sheet.setColumnWidth(36, 10000);
            sheet.setColumnWidth(37, 10000);
            sheet.setColumnWidth(38, 10000);
            sheet.setColumnWidth(39, 10000);
            sheet.setColumnWidth(40, 10000);
            CellStyle style = workBook.createCellStyle() ;
            XSSFFont font = workBook.createFont();
            font.setFontName("Courier");
            style.setFont(font);
//            style.setWrapText(true);
            int RowNum=-1;
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(csvFileAddress);
            while (csvReader.readRecord()){
                RowNum++;
                XSSFRow currentRow=sheet.createRow(RowNum);
                for(int i=0;i<csvReader.getColumnCount();i++){
                    currentRow.createCell(i).setCellValue(csvReader.get(i).trim());
                    if (i >= 35){
                        currentRow.getCell(i).setCellStyle(style);
                    }
                }
            }

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("Done");
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
    }
}
