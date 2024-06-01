package com.myigou.tool;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ab1324ab
 * Created by ab1324ab on 2022年12月6日.
 */
public class AudioPlayerTool {

    /**
     * 播放wav*
     */
    public static void playerStartAudioWav(String wavStr) {
        try {
            InputStream inputStream = AudioPlayerTool.class.getResourceAsStream(wavStr);
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放wav*
     */
    public static void playerStopAudioWav(String wavStr) {
        try {
            InputStream inputStream = AudioPlayerTool.class.getResourceAsStream(wavStr);
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.stop(audioStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取音频的目录文件*
     */
    public static List<String> audioDirList(){
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
