package com.zfx.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
//        String path ="D:\\RuoYiCloud\\ruoyi-api";

        String path = "D:\\zfx\\调研文档";
//        traverseFolder1(path,"linux相关命令.md");

        File root=new File(path);
        List<String> fileNameList=new ArrayList<String>();
        showFileList(root,fileNameList);
        for(String fileName:fileNameList){
            System.out.println(fileName);
        }

    }

    public static List<String> traverseFolder1(String path, String fileName) throws IOException {
        List<String> fileList = new ArrayList<String>();
        int fileNum = 0, folderNum = 0;
        String filePath = "";
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    list.add(file2);
                    folderNum++;
                } else {
                    fileList.add(file2.getAbsolutePath());
                    if (file2.getName().equals(fileName)) {
                        filePath = file2.getCanonicalPath();
                    }

                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        list.add(file2);
                        folderNum++;
                    } else {
                        fileList.add(file2.getAbsolutePath());
                        fileList.add(file2.getAbsolutePath());
                        if (file2.getName().equals(fileName)) {
                            filePath = file2.getCanonicalPath();
                        }
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum + ",文件绝对路径：" + filePath);
        return fileList;
    }


    public static void showFileList(File file,List<String> fileNameList){
        if(!file.exists()){
            System.out.println("这是一个错误的路径");
        }
        if(file.isDirectory()){
            File []files=file.listFiles();
            for (File file2 : files) {
                showFileList(file2,fileNameList);
            }
        }else{
            //System.out.println(file.getAbsolutePath());
            //System.out.println(file.getPath()+File.separator+file.getName());
            if(file.isFile()){
                fileNameList.add(file.getAbsolutePath());
            }
        }
    }

}
