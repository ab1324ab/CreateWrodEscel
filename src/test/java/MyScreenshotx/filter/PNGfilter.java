package MyScreenshotx.filter;


import java.io.File;

//保存PNG格式的过滤器
public class PNGfilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
        if (file.toString().toLowerCase().endsWith(".png") ||
                file.isDirectory()) {
            return true;
        } else
            return false;
    }

    public String getDescription() {
        return "*.PNG(PNG图像)";
    }
}
