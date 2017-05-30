package com.example.vxg.vxgffmpeg;

import java.io.File;

/**
 * Created by mobao.libo on 2017-05-09-0009.
 */

public class FFMpegUtils {
    static FFMpegUtils instance;

    public static FFMpegUtils getInstance() {
        if (instance == null)
            instance = new FFMpegUtils();
        return instance;
    }

    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("mbff");
    }

    public static boolean dirIsExist(String path){
        File file = new File(path);
        return file.exists();
    }

    public int crop_backgroud_music(String srcBGM,String dstBGM){
        String [] commonds = new String[10];
        commonds[0] = "ffmpeg";
        commonds[1] = "-i";
        commonds[2] = srcBGM;
        commonds[3] = "-ss";
        commonds[4] = "00:00:00";
        commonds[5] = "-to";
        commonds[6] = "00:00:09";
        commonds[7] = "-c";
        commonds[8] = "copy";
        commonds[9] = dstBGM + "bg_9s.mp3";
        return crop_background_music(commonds);
    }

    public int remove_bgm_from_video(String src,String dst){
        String [] commonds = new String[7];
        commonds[0] = "ffmpeg";
        commonds[1] = "-i";
        commonds[2] = src;
        commonds[3] = "-vcodec";
        commonds[4] = "copy";
        commonds[5] = "-an";
        commonds[6] = dst;
        return remove_bgm_from_video(commonds);
    }

    public int merge_bgm_to_video(String srcBGM,String srcVideo,String dstVideo){
        return 1;
    }

    public native int crop_background_music(String []commonds);

    public native int merge(String srcVideo,
                            String srcBGM,
                            String dstVideo,
                            String dstBGMPath);

    public native int remove_bgm_from_video(String []commonds);
}
