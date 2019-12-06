package com.nts;

import com.nts.util.AudioUtil;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.junit.Test;

import java.io.File;
import java.text.DecimalFormat;

public class TestAudio extends BasicTest {
    @Test
    public void test0() throws Exception {
        File file = new File("D:\\test2.mp3");
        MP3File f = (MP3File) AudioFileIO.read(file);
        MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
        Integer time = audioHeader.getTrackLength();
        if (time % 60 == 0) {
            System.out.println("音乐时长为：" + time / 60 + "分");
        } else {
            if (time % 60 < 10) {
                System.out.println("音乐时长为：" + time / 60 + "分0" + time % 60 + "秒");
            } else {
                System.out.println("音乐时长为：" + time / 60 + "分" + time % 60 + "秒");
            }
        }
        long size = file.length() % 1024 == 0 ? file.length() / 1024 : file.length() / 1024 + 1;
        Double newSize = Double.valueOf(size) / 1024;
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("文件大小：" + df.format(newSize) + "Mb");
    }

    @Test
    public void test2() {
        File file = new File("D:\\test2.mp3");
        String size = AudioUtil.getSize(file);
        String time = AudioUtil.getTime(file);
        System.out.println("文件大小：" + size);
        System.out.println(time);
    }
}
