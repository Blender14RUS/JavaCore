package simpleFileArchiver;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.*;

public class Main {

    public static void main(String[] args) throws IOException {
//        Path zipName = Paths.get(System.getProperty("user.home") + "\\Desktop\\test.zip\\");
//        File file = new File(System.getProperty("user.home") + "\\Desktop\\");
//        unzip(zipName, file);
        Path dataFolder = Paths.get(System.getProperty("user.home") + "\\Desktop\\skill-samples-java-master\\");
        Path zipFile = Paths.get(System.getProperty("user.home") + "\\Desktop\\archive.zip\\");
        zip(dataFolder, zipFile);
    }

    public static void unzip(Path zipName, File directory) throws IOException {
        try (InputStream inputStream = Files.newInputStream(zipName);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             ZipInputStream zipFile = new ZipInputStream(bufferedInputStream);) {

            ZipEntry entry;
            while ((entry = zipFile.getNextEntry()) != null) {
                File file = new File(directory + "\\" + entry.getName());
                if (!entry.isDirectory()) {
                    try (OutputStream outputStream = Files.newOutputStream(file.toPath());
                         BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);) {
                        byte[] buffer = new byte[(int) entry.getSize()];
                        zipFile.read(buffer);
                        bufferedOutputStream.write(buffer);
                    }
                } else {
                    file.mkdirs();
                }
            }
        }
    }

    public static void zip(final Path dataFolder, Path zipFilePath) throws IOException{
        try (OutputStream outputStream = Files.newOutputStream(zipFilePath);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
             ZipOutputStream zipFile = new ZipOutputStream(bufferedOutputStream);) {
            //visit each file in a file tree
            Files.walkFileTree(dataFolder, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try (InputStream inputStream = Files.newInputStream(file);
                         BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);) {
                            zipFile.putNextEntry(new ZipEntry(file.subpath(dataFolder.getNameCount(), file.getNameCount()).toString()));
                            //byte[] buffer = new byte[(int) Files.size(file)];
                            //bufferedInputStream.read(buffer);
                        while (bufferedInputStream.available() > 0) {
                            zipFile.write(inputStream.read());
                        }
                            //zipFile.write(buffer);
                            zipFile.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}