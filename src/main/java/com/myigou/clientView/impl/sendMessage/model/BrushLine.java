package com.myigou.clientView.impl.sendMessage.model;

import java.awt.*;
import java.util.List;

/**
 * 画笔实体类*
 * 2023年1月6日20点06分*
 * @author RedMiG *
 */
public class BrushLine {

    private String color = null;

    private Integer size = null;

    private List<Point> apoint = null;

    public BrushLine(String color, Integer size, List<Point> apoint) {
        this.color = color;
        this.size = size;
        this.apoint = apoint;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Point> getApoint() {
        return apoint;
    }

    public void setApoint(List<Point> apoint) {
        this.apoint = apoint;
    }

}
