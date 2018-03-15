package homework;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archiver {

    public void unzip(Path zipFilePath, Path extractPath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(zipFilePath);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             ZipInputStream zipFile = new ZipInputStream(bufferedInputStream)) {

            ZipEntry entry;
            while ((entry = zipFile.getNextEntry()) != null) {
                File file = new File(extractPath + "\\" + entry.getName());
                if (!entry.isDirectory()) {
                    try (OutputStream outputStream = Files.newOutputStream(file.toPath());
                         BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
                        byte[] buffer = new byte[1024];
                        while (zipFile.read(buffer) >= 0) {
                            bufferedOutputStream.write(buffer);
                        }
                    }
                } else {
                    if (!file.mkdirs()) throw new IOException();
                }
            }
        }
    }

    public void zip(final Path dataPath, Path zipFilePath) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(zipFilePath);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
             ZipOutputStream zipFile = new ZipOutputStream(bufferedOutputStream)) {

            if (dataPath.toFile().isFile()) {
                try (InputStream inputStream = Files.newInputStream(dataPath);
                     BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
                    zipFile.putNextEntry(new ZipEntry(dataPath.getFileName().toString()));
                    byte[] buffer = new byte[1024];
                    while (bufferedInputStream.read(buffer) >= 0) {
                        zipFile.write(buffer);
                    }
                    zipFile.closeEntry();
                }
            } else {
                //visit each file in a file tree
                Files.walkFileTree(dataPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        try (InputStream inputStream = Files.newInputStream(file);
                             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
                            zipFile.putNextEntry(new ZipEntry(file.subpath(dataPath.getNameCount() - dataPath.getFileName().getNameCount(), file.getNameCount()).toString()));
                            byte[] buffer = new byte[1024];
                            while (bufferedInputStream.read(buffer) >= 0) {
                                zipFile.write(buffer);
                            }
                            zipFile.closeEntry();
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        }
    }
}