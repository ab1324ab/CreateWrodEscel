package com.myigou.module;

import java.awt.*;

public class MyProgressBar extends Canvas {
    private float scaleSize;
    private float currentValue;
    public MyProgressBar() {
        this(150, 50);
    }
    public MyProgressBar(float scaleSize, float currentValue) {
        this.scaleSize = scaleSize;
        this.currentValue = currentValue;
        this.setBackground(Color.lightGray);
        this.setForeground(Color.magenta);
        setSize(106, 25);
    }
    public void setCurrentValue(float currentValue) {
        this.currentValue = Math.max(0, currentValue);
        if (this.scaleSize < this.currentValue) {
            this.currentValue = this.scaleSize;
        }
    }
    @Override
    public synchronized void paint(Graphics g) {
        int w = getSize().width;
        int h = getSize().height;
        g.setColor(getForeground());
        g.fillRect(3, 3, (int)currentValue, 20);
    }
}