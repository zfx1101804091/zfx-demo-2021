package com.zfx.io;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;

public class write {
    public static void main(String[] args) throws IOException {
        //创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建sheet页
        XSSFSheet sheet = workbook.createSheet("test");
        //创建工作簿样式
//        sheet.setDefaultRowHeight((short)(20 *256));
        //设置第一列 第二列 宽度 即你要的宽度 * 256
        sheet.setColumnWidth(0,50 * 256);
        sheet.setColumnWidth(1,20 * 256);
        //创建XSSFCellStyle 格式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        //设置填充背景颜色
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        //设置填充模式
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
       //字体样式 创建字体对象XSSFFont
        XSSFFont xssfFont = workbook.createFont();
        xssfFont.setFontName("楷体");
        //向单元格设置以上设置的属性
        cellStyle.setFont(xssfFont);
        //以下是正题向文件中写入数据 是向xlsx文件 不是csv(csv是以逗号分隔的)
        //创建行
        XSSFRow row = sheet.createRow(0);
        row.setHeight((short)(30*20));
        //创建单元格
        XSSFCell cell = row.createCell(0);
        XSSFCell cell1 = row.createCell(1);
        //设置值
        cell.setCellValue("hah");
        cell1.setCellValue("hello");
        cell.setCellStyle(cellStyle);
        cell1.setCellStyle(cellStyle);
        //创建输出流
        FileOutputStream outputStream = new FileOutputStream("F:\\学习\\test\\write.xlsx");
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        System.out.println("写入完成");
    }
    }
