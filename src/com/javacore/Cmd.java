package com.javacore;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cmd {
    private String currentPath = "C:\\";

    public void start() throws IOException {
        String data = "C:\\";
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.print(currentPath + ">");
            data = in.nextLine();
            String[] param = data.split(" ");
            if (param.length == 0) continue;
            switch (param[0]){
                case "cd":
                    if (param.length > 1 && checkPath(param[1])) {
                        cd(param[1]);
                    }
                    break;
                case "dir":
                    if (param.length > 1 && checkPath(param[1]))
                        dir(param[1]);
                    else
                        dir(currentPath);
                    break;
                case "copy":
                    if (param.length > 2)
                        copy(param[1], param[2]);
                    else
                        System.out.print("Ошибка в синтаксисе команды.\n");
                    break;
                case "mkdir":
                    if (param.length > 1)
                        mkdir(param[1]);
                    break;
                case "exit":
                    return;
            }
        }
    }

    public void mkdir(String dirName) throws IOException{
        try {
            File file = (dirName.toLowerCase().startsWith("c:") || dirName.toLowerCase().startsWith("d:")) ? new File(dirName) : new File(currentPath, dirName);
            file.mkdirs();
        }catch (Exception e) {
            System.out.print("Отказано в доступе.\n");
        }
    }

    public void copy(String fileName, String copyName) throws IOException{
        try {
            Path file = (fileName.toLowerCase().startsWith("c:") || fileName.toLowerCase().startsWith("d:")) ? Paths.get(fileName) : Paths.get(currentPath, fileName);
            Path copyFile = (copyName.toLowerCase().startsWith("c:") || copyName.toLowerCase().startsWith("d:")) ? Paths.get(copyName) : Paths.get(currentPath, copyName);

            try (InputStream inputStream = Files.newInputStream(file);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);) {

                byte[] buffer = new byte[(int) Files.size(file)];
                bufferedInputStream.read(buffer);

                try (OutputStream outputStream = Files.newOutputStream(copyFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);) {
                    bufferedOutputStream.write(buffer);
                }
            }
        }catch (Exception e) {
            System.out.print("Ошибка копирования, проверьте права доступа или путь к файлу.\n");
        }
    }

    public void dir(String path) throws IOException{
        Path dataFolder = Paths.get(path);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataFolder);) {
            for (Path file : stream) {
                Date date = new Date(file.toFile().lastModified());
                SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy'  'hh:mm");
                System.out.print(formatForDate.format(date));
                if (file.toFile().isDirectory()) System.out.print("\t <DIR> \t");
                else System.out.print("\t\t\t");
                System.out.print(file.getFileName() + "\n");
            }
        }
    }
    public void cd(String path) throws IOException{
        String temp;
        switch (path.toLowerCase()){
            case "..":
                temp = (Paths.get(currentPath).getParent() != null) ? Paths.get(currentPath).getParent().toString() : currentPath;
                break;
            case "d:":
                temp = "D:\\";
                break;
            default:
                temp = (path.toLowerCase().startsWith("c:\\") || path.toLowerCase().startsWith("d:\\")) ? path : currentPath + path;
        }
        if(Paths.get(temp).toFile().isDirectory()) currentPath = temp;
        else System.out.print("Неверно задано имя папки.\n");
}

    public boolean checkPath(String path) throws IOException{
        try {
            Paths.get(path);
        }catch (Exception e) {
            System.out.print("Системе не удается найти указанный путь.\n");
            return false;
        }
        return true;
    }
}
