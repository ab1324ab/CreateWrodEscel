package MyScreenshotx;

import java.awt.*;
import java.util.List;

public class BrushLine {

    String color = null;

    Integer size = null;

    java.util.List<Point> apoint = null;

    public BrushLine(String color, Integer size, List<Point> apoint) {
        this.color = color;
        this.size = size;
        this.apoint = apoint;
    }
}
