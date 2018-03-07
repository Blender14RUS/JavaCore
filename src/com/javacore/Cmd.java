package com.javacore;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|'([^']*)'|[^\\s]+");
        Matcher matcher;

        for (; ; ) {
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

            switch (argsList.get(0)) {
                case "cd":
                    if (argsList.size() > 1) {
                        cd(argsList.get(1));
                    }
                    break;
                case "dir":
                    if (argsList.size() > 1)
                        dir(argsList.get(1));
                    else
                        dir(currentPath);
                    break;
                case "copy":
                    if (argsList.size() > 2)
                        copy(argsList.get(1), argsList.get(2));
                    else
                        System.out.print("Syntax error.\n");
                    break;
                case "mkdir":
                    if (argsList.size() > 1)
                        mkdir(argsList.get(1));
                    else
                        System.out.print("Syntax error.\n");
                    break;
                case "move":
                    if (argsList.size() > 2)
                        move(argsList.get(1), argsList.get(2));
                    else
                        System.out.print("Syntax error.\n");
                    break;
                case "print":
                    if (argsList.size() > 1)
                        print(argsList.get(1));
                    break;
                case "remove":
                    if (argsList.size() > 1)
                        remove(argsList.get(1));
                    break;
                case "exit":
                    return;
                case "unzip":
                    if (argsList.size() > 2)
                        unzip(argsList.get(1), argsList.get(2));
                    else
                        System.out.print("Syntax error.\n");
                    break;
                case "zip":
                    if (argsList.size() > 2)
                        zip(argsList.get(1), argsList.get(2));
                    else
                        System.out.print("Syntax error.\n");
                    break;
                default:
                    System.out.print(argsList.get(0) + " is not recognized as an internal or external\n" +
                            "command, operable program or batch file.\n");
                    break;
            }
        }
    }

    private void zip(String dataFileName, String zipFileName) {
        try {
            Archiver archiver = new Archiver();
            if (!zipFileName.endsWith(".zip")) zipFileName += ".zip";
            Path dataPath = (includesFSRoots(dataFileName)) ? Paths.get(dataFileName) : Paths.get(currentPath, dataFileName);
            Path zipFilePath = (includesFSRoots(zipFileName)) ? Paths.get(zipFileName) : Paths.get(currentPath, zipFileName);
            archiver.zip(dataPath, zipFilePath);
        } catch (InvalidPathException e) {
            System.out.print("The system cannot find the path specified.\n");
        } catch (IOException e) {
            System.out.print("Error: Check file path.\n");
        } catch (Exception e) {
            System.out.print(e.getMessage() + "\n");
        }
    }

    private void unzip(String zipFileName, String dataFileName) {
        try {
            Archiver archiver = new Archiver();
            if (!zipFileName.endsWith(".zip")) zipFileName += ".zip";
            Path zipFilePath = (includesFSRoots(zipFileName)) ? Paths.get(zipFileName) : Paths.get(currentPath, zipFileName);
            Path extractPath = (includesFSRoots(dataFileName)) ? Paths.get(dataFileName) : Paths.get(currentPath, dataFileName);
            archiver.unzip(zipFilePath, extractPath);
        } catch (InvalidPathException e) {
            System.out.print("The system cannot find the path specified.\n");
        } catch (IOException e) {
            System.out.print("Error: Check file path.\n");
        } catch (Exception e) {
            System.out.print(e.getMessage() + "\n");
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
        } catch (InvalidPathException e) {
            System.out.print("The system cannot find the path specified.\n");
        } catch (IOException e) {
            System.out.print("Error: could not find this item / you don't have permission to access.\n");
        } catch (Exception e) {
            System.out.print(e.getMessage() + "\n");
        }
    }

    private void print(String fileName) {
        try {
            Path path = (includesFSRoots(fileName)) ? Paths.get(fileName) : Paths.get(currentPath, fileName);
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(String.valueOf(line));
                }
            }
        } catch (InvalidPathException e) {
            System.out.print("The system cannot find the path specified.\n");
        } catch (IOException e) {
            System.out.print("Access denied.\n");
        } catch (Exception e) {
            System.out.print(e.getMessage() + "\n");
        }
    }

    private void move(String fromFileName, String dirName) {
        try {
            Path fromFile = (includesFSRoots(fromFileName)) ? Paths.get(fromFileName) : Paths.get(currentPath, fromFileName);
            Path toFile = (includesFSRoots(dirName) && Paths.get(dirName).toFile().isDirectory()) ? Paths.get(dirName, fromFile.getFileName().toString()) :
                    (includesFSRoots(dirName) ? Paths.get(dirName) : (Paths.get(currentPath, dirName).toFile().isDirectory() ? Paths.get(currentPath, dirName + "\\" + fromFile.getFileName()) : Paths.get(currentPath, dirName)));
            Files.move(fromFile, toFile);
        } catch (InvalidPathException e) {
            System.out.print("The system cannot find the path specified.\n");
        } catch (FileAlreadyExistsException e) {
            System.out.print("File of that name already exists.\n");
        } catch (IOException e) {
            System.out.print("Error reading, you don't have permission to access / invalid path to directory.\n");
        } catch (Exception e) {
            System.out.print(e.getMessage() + "\n");
        }
    }

    private void mkdir(String dirName) {
        try {
            File file = (includesFSRoots(dirName)) ? new File(dirName) : new File(currentPath, dirName);
            if (!file.mkdirs()) throw new Exception();
        } catch (NullPointerException e) {
            System.out.print("The directory name is invalid.\n");
        } catch (Exception e) {
            System.out.print(e.getMessage() + "\n");
        }
    }

    private void copy(String fileName, String copyName) {
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
                    Files.copy(file, copyFile.resolve(sourceFile.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (InvalidPathException e) {
            System.out.print("The system cannot find the path specified.\n");
        } catch (IOException e) {
            System.out.print("Error copying, you don't have permission to access / invalid path to directory.\n");
        } catch (Exception e) {
            System.out.print(e.getMessage() + "\n");
        }
    }

    private void dir(String path) {
        try {
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
            }
        } catch (InvalidPathException e) {
            System.out.print("The system cannot find the path specified.\n");
        } catch (IOException e) {
            System.out.print("Error reading directory, you don't have permission to access / invalid path to directory.\n");
        } catch (Exception e) {
            System.out.print(e.getMessage() + "\n");
        }
    }

    private void cd(String path) {
        try {
            String temp = currentPath + "\\" + path;

            for (File diskName : File.listRoots()) {
                if (path.equals("..")) {
                    temp = (Paths.get(currentPath).getParent() != null) ? Paths.get(currentPath).getParent().toString() : currentPath;
                } else if (diskName != null && (path.equalsIgnoreCase(diskName.toString()) || (path + "\\").equalsIgnoreCase(diskName.toString()))) {
                    temp = diskName.toString();
                } else if (diskName != null && path.toLowerCase().startsWith(diskName.toString().toLowerCase())) {
                    temp = path;
                }
            }

            if (Paths.get(temp).toFile().isDirectory()) currentPath = Paths.get(temp).normalize().toString();
            else System.out.print("Invalid directory name specified.\n");
        } catch (InvalidPathException e) {
            System.out.print("The system cannot find the path specified.\n");
        } catch (Exception e) {
            System.out.print(e.getMessage() + "\n");
        }
    }

    private boolean includesFSRoots(String fileName) {
        for (File diskName : File.listRoots()) {
            if (diskName != null && fileName.toLowerCase().startsWith(diskName.toString().toLowerCase())) return true;
        }
        return false;
    }
}
