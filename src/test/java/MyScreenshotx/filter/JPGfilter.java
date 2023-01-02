package MyScreenshotx.filter;


import java.io.File;

//保存JPG格式的过滤器
public class JPGfilter extends javax.swing.filechooser.FileFilter {
    public JPGfilter() {
    }

    public boolean accept(File file) {
        if (file.toString().toLowerCase().endsWith(".jpg") || file.isDirectory()) return true;
         else return false;
    }

    public String getDescription() {
        return "*.JPG(JPG图像)";
    }
}
