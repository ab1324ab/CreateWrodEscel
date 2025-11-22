package com.myigou.tool;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用 Java Sound API 替代 sun.audio 的工具类
 */
public class AudioPlayerTool {

    private static Clip currentClip; // 当前播放的音频片段

    /**
     * 播放 .wav 音频资源（来自 resources 目录）
     */
    public static void playerStartAudioWav(String wavResourcePath) {
        try {
            URL soundURL = AudioPlayerTool.class.getResource(wavResourcePath);
            if (soundURL == null) {
                System.err.println("音频文件未找到: " + wavResourcePath);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());

            if (currentClip != null) {
                currentClip.stop();
                currentClip.close();
            }

            currentClip = (Clip) AudioSystem.getLine(info);
            currentClip.open(audioInputStream);
            currentClip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止当前播放的音频
     */
    public static void playerStopAudioWav() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }

    /**
     * 获取音频资源目录下的文件名列表（硬编码）
     */
    public static List<String> audioDirList() {
        List<String> audioname = new ArrayList<>();
        audioname.add("Logon.wav");
        audioname.add("Notify Calendar.wav");
        audioname.add("Notify Email.wav");
        audioname.add("Notify Messaging.wav");
        audioname.add("Proximity Connection.wav");
        audioname.add("Proximity Notification.wav");
        audioname.add("Alarm04.wav");
        audioname.add("Alarm06.wav");
        audioname.add("Alarm07.wav");
        audioname.add("Alarm08.wav");
        audioname.add("Alarm09.wav");
        audioname.add("Ring01.wav");
        audioname.add("Ring04.wav");
        audioname.add("Ring07.wav");
        audioname.add("Ring10.wav");
        audioname.add("Speech Off.wav");
        audioname.add("Speech On.wav");
        audioname.add("Speech Sleep.wav");
        audioname.add("Background.wav");
        audioname.add("Hardware Insert.wav");
        audioname.add("Hardware Remove.wav");
        return audioname;
    }
}