package com.nts.util;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import java.io.File;
import java.text.DecimalFormat;

public class AudioUtil {

    public static String getSize(File file) {
        long size = file.length() % 1024 == 0 ? file.length() / 1024 : file.length() / 1024 + 1;
        Double newSize = Double.valueOf(size) / 1024;
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(newSize) + "MB";
    }

    public static String getTime(File file) {
        MP3File f = null;
        try {
            f = (MP3File) AudioFileIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
        Integer time = audioHeader.getTrackLength();
        if (time % 60 == 0) {
            return time / 60 + "分";
        } else {
            if (time % 60 < 10) {
                return time / 60 + "分0" + time % 60 + "秒";
            } else {
                return time / 60 + "分" + time % 60 + "秒";
            }
        }
    }
}
