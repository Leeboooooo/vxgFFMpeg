package com.example.vxg.vxgffmpeg;

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

    public native int merge(String srcVideo,
                            String srcBGM,
                            String dstVideo,
                            String dstBGMPath);
}
