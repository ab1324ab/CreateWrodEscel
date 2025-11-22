package com.myigou.tool;

import info.monitorenter.cpdetector.io.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 文件处理类*
 * 2023年2月16日18点33分*
 */
public class FilesTool {

    /**
     * 获取文本文件的文件内容编码*
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String getFileCharsetName(String fileName) throws IOException {
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());// 用到antlr.jar、chardet.jar
        // ASCIIDetector用于ASCII编码测定
        detector.add(ASCIIDetector.getInstance());
        // UnicodeDetector用于Unicode家族编码的测定
        detector.add(UnicodeDetector.getInstance());
        java.nio.charset.Charset charset = null;
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
            charset = detector.detectCodepage(in, 2147483647);
            System.out.println(charset);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (charset != null) return charset.name();
        else return Charset.defaultCharset().name();
    }

}
