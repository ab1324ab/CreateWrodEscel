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
    private JTextField fileNameField;
    /**
     * 文件地址*
     */
    private Path filePath;
    /**
     * 文件类型*
     */
    private String fileType;
    private JTextField fileTypeField;
    /**
     * 文件新名称*
     */
    private String newFileName;
    private JTextPane newFileNameField;
    /**
     * 文件大小*
     */
    private long fileSize;
    private JTextField fileSizeField;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public JTextField getFileNameField() {
        return fileNameField;
    }

    public void setFileNameField(JTextField fileNameField) {
        this.fileNameField = fileNameField;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public JTextField getFileTypeField() {
        return fileTypeField;
    }

    public void setFileTypeField(JTextField fileTypeField) {
        this.fileTypeField = fileTypeField;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public JTextPane getNewFileNameField() {
        return newFileNameField;
    }

    public void setNewFileNameField(JTextPane newFileNameField) {
        this.newFileNameField = newFileNameField;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public JTextField getFileSizeField() {
        return fileSizeField;
    }

    public void setFileSizeField(JTextField fileSizeField) {
        this.fileSizeField = fileSizeField;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }
}
