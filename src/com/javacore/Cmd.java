package com.javacore;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

public class Cmd {
    private String currentPath;

    public Cmd() {
        this.currentPath = "C:\\";
    }

    public Cmd(String currentPath) {
        this.currentPath = currentPath;
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        List<String> argsList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"(.*)\"|'(.*)'|[^\\s]+");
        Matcher matcher;

        for(;;) {
            System.out.print(currentPath + ">");
            argsList.clear();
            matcher = pattern.matcher(in.nextLine());
            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    argsList.add(matcher.group(1));
                } else if (matcher.group(2) != null) {
                    argsList.add(matcher.group(2));
                } else {
                    argsList.add(matcher.group());
                }
            }
            if (argsList.size() == 0) continue;

            switch (argsList.get(0)){
                case "cd":
                    if (argsList.size() > 1 && checkPath(argsList.get(1))) {
                        cd(argsList.get(1));
                    }
                    break;
                case "dir":
                    if (argsList.size() > 1 && checkPath(argsList.get(1)))
                        dir(argsList.get(1));
                    else
                        dir(currentPath);
                    break;
                case "copy":
                    if (argsList.size() > 2)
                        copy(argsList.get(1), argsList.get(2));
                    else
                        System.out.print("Ошибка в синтаксисе команды.\n");
                    break;
                case "mkdir":
                    if (argsList.size() > 1)
                        mkdir(argsList.get(1));
                    else
                        System.out.print("Ошибка в синтаксисе команды.\n");
                    break;
                case "move":
                    if (argsList.size() > 2)
                        move(argsList.get(1), argsList.get(2));
                    else
                        System.out.print("Ошибка в синтаксисе команды.\n");
                    break;
                case "print":
                    if (argsList.size() > 1 && checkPath(argsList.get(1)))
                        print(argsList.get(1));
                    break;
                case "remove":
                    if (argsList.size() > 1 && checkPath(argsList.get(1)))
                        remove(argsList.get(1));
                    break;
                case "exit":
                    return;
                default:
                    System.out.print(argsList.get(0) + " не является внутренней или внешней\n" +
                            "командой, исполняемой программой или пакетным файлом.\n");
                    break;
            }
        }
    }
    private void remove(String name) {
        try {
            Path directory = (includesFSRoots(name)) ? Paths.get(name) : Paths.get(currentPath, name);
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e){
            System.out.print("Ошибка удаления");
        }
    }

    private void print(String fileName){
        try {
            Path path = (includesFSRoots(fileName)) ? Paths.get(fileName) : Paths.get(currentPath, fileName);
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(String.valueOf(line));
                }
            }
        } catch (Exception e) {
            System.out.print("Отказано в доступе.\n");
        }
    }

    private void move(String fromFileName, String dirName){
        try {
            Path fromFile = (includesFSRoots(fromFileName)) ? Paths.get(fromFileName) : Paths.get(currentPath, fromFileName);
            Path toFile = (includesFSRoots(dirName) && Paths.get(dirName).toFile().isDirectory()) ? Paths.get(dirName, fromFile.getFileName().toString()) :
                    (includesFSRoots(dirName) ? Paths.get(dirName) : (Paths.get(currentPath, dirName).toFile().isDirectory() ? Paths.get(currentPath, dirName + "\\" + fromFile.getFileName()) : Paths.get(currentPath, dirName)));
            //same don't using ternary operator:
//                if (includesFSRoots(dirName) && Paths.get(dirName).toFile().isDirectory()){
//                    Paths.get(dirName, fromFile.getFileName());
//                }
//                else if (includesFSRoots(dirName)){
//                    Paths.get(dirName);
//                }
//                else if (Paths.get(currentPath, dirName).toFile().isDirectory()){
//                    Paths.get(currentPath, dirName + "\\" + fromFile.getFileName());
//                }
//                else{
//                    Paths.get(currentPath, dirName);
//                }
            Files.move(fromFile, toFile);
        } catch (Exception e) {
            System.out.print("Отказано в доступе, возможно файл с таким именем уже существует.\n");
        }
    }

    private void mkdir(String dirName){
        try {
            File file = (includesFSRoots(dirName)) ? new File(dirName) : new File(currentPath, dirName);
            if (!file.mkdirs()) throw new Exception();
        } catch (Exception e) {
            System.out.print("Отказано в доступе.\n");
        }
    }

    private void copy(String fileName, String copyName){
        try {
            Path sourceFile = (includesFSRoots(fileName)) ? Paths.get(fileName) : Paths.get(currentPath, fileName);
            Path copyFile = (includesFSRoots(copyName)) ? Paths.get(copyName) : Paths.get(currentPath, copyName);

            Files.walkFileTree(sourceFile, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path newdir = copyFile.resolve(sourceFile.relativize(dir));
                    Files.copy(dir, newdir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file,copyFile.resolve(sourceFile.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            System.out.print("Ошибка копирования, проверьте права доступа или путь к файлу.\n");
        }
    }

    private boolean includesFSRoots(String fileName){
        for(File diskName : File.listRoots()) {
            if (diskName != null && fileName.toLowerCase().startsWith(diskName.toString().toLowerCase())) return true;
        }
        return false;
    }

    private void dir(String path){
        Path dataFolder = Paths.get(path);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataFolder)) {
            for (Path file : stream) {
                Date date = new Date(file.toFile().lastModified());
                SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy'  'hh:mm");
                System.out.print(formatForDate.format(date));
                if (file.toFile().isDirectory()) System.out.print("\t <DIR> \t");
                else System.out.print("\t\t\t");
                System.out.print(file.getFileName() + "\n");
            }
        } catch (Exception e){
            System.out.print("Ошибка чтения каталога, проверьте права доступа или путь к каталогу.\n");
        }
    }

    private void cd(String path){
        String temp = currentPath + "\\" + path;
        for(File diskName : File.listRoots()) {
            if (path.equals("..")){
                temp = (Paths.get(currentPath).getParent() != null) ? Paths.get(currentPath).getParent().toString() : currentPath;
            }
            else if (diskName != null && (path.equalsIgnoreCase(diskName.toString()) || (path + "\\").equalsIgnoreCase(diskName.toString()))){
                temp = diskName.toString();
            }
            else if(diskName != null && path.toLowerCase().startsWith(diskName.toString().toLowerCase()) ){
                temp = path;
            }
        }

        if(Paths.get(temp).toFile().isDirectory()) currentPath = Paths.get(temp).normalize().toString();
        else System.out.print("Неверно задано имя каталога.\n");
    }

    private boolean checkPath(String path){
        try {
            Paths.get(path);
        } catch (Exception e) {
            System.out.print("Системе не удается найти указанный путь.\n");
            return false;
        }
        return true;
    }
}
