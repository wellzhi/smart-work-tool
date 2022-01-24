package com.liuxz.smart.plugin.data;

public class NoteData {
    private String title;
    private String mark;
    private String content;
    private String fileName;
    private String fileType;
    private String absoluteFilePath;

    public NoteData(String title, String mark, String content, String fileName, String fileType, String absoluteFilePath) {
        this.title = title;
        this.mark = mark;
        this.content = content;
        this.fileName = fileName;
        this.fileType = fileType;
        this.absoluteFilePath = absoluteFilePath;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public void setAbsoluteFilePath(String absoluteFilePath) {
        this.absoluteFilePath = absoluteFilePath;
    }

    @Override
    public String toString() {
        return "NoteData{" +
                "title='" + title + '\'' +
                ", mark='" + mark + '\'' +
                ", content='" + content + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
