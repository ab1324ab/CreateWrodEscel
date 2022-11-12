package com.myigou.clientService.model;

import javax.swing.*;
import java.nio.file.Path;

/**
 * 文件修改文件名称
 * 16点46分
 */
public class TextEditingFileType {

    public TextEditingFileType(String fileName, Path filePath, String fileType, String newFileName, long fileSize) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.newFileName = newFileName;
        this.fileSize = fileSize;
    }

    public TextEditingFileType() {
    }

    /**
     * 文件名称*
     */
    private String fileName;

    /**
     * 文件地址*
     */
    private Path filePath;
    /**
     * 文件类型*
     */
    private String fileType;

    /**
     * 文件新名称*
     */
    private String newFileName;

    /**
     * 文件大小*
     */
    private long fileSize;

    private JPanel row;

    public JPanel getRow() {
        return row;
    }

    public void setRow(JPanel row) {
        this.row = row;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

}
