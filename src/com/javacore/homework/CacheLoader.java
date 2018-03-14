package com.javacore.homework;

import java.io.*;
import java.nio.file.*;

public class CacheLoader {
    private String filename;
    private String pathToCacheFolder;
    private Path saveDirectory;

    public CacheLoader() {
        pathToCacheFolder = System.getProperty("user.home") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Media Cache";
        saveDirectory = Paths.get(System.getProperty("user.home") + "\\Desktop\\cacheMP3\\");
    }

    public void load() throws IOException {
        Files.createDirectories(saveDirectory);
        Path dataFolder = Paths.get(pathToCacheFolder);
        //"f_*" - glob: filter out unnecessary files (data, index, etc)
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataFolder, "f_*");) {
            for (Path file : stream) {
                //use try with resources / so we don't have close()
                try (InputStream inputStream = Files.newInputStream(file);
                     BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);) {

                    byte[] buffer = new byte[(int) Files.size(file)];
                    bufferedInputStream.read(buffer);

                    if (checkHeader(buffer)) {
                        System.out.println("header: " + file.getFileName());
                        filename = file.getFileName() + ".mp3";
                        try (OutputStream outputStream = Files.newOutputStream(Paths.get(saveDirectory + "\\" + filename), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);) {
                            bufferedOutputStream.write(buffer);
                        }
                    } else {
                        if (filename == null) continue;
                        System.out.println(file.getFileName() + " size: " + Files.size(file));
                        try (OutputStream outputStream = Files.newOutputStream(Paths.get(saveDirectory + "\\" + filename), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);) {
                            bufferedOutputStream.write(buffer);
                        }
                    }
                }
            }
        }
    }

    private boolean checkHeader(byte[] data) {
        if (data[0] == (byte) 73 && data[1] == (byte) 68 && data[2] == (byte) 51 && data[3] == (byte) 4) return true;
        if (data[0] == (byte) 255 && data[1] == (byte) 251 && data[3] == (byte) 4) return true;

        return false;
    }
}
