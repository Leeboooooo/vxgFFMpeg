//
// Created by mobao.libo on 2017-05-09-0009.
//

#include <jni.h>
#include <err.h>
#include <string.h>
#include <stdio.h>
#include <comm/ffmpeg.h>

#define __bg_9s ("bg_9s.mp3")

//int ffmpeg_cmd(int argc, char **argv);

int run_cmd(char * str_cmd){
    int argc = 1;
    char * argv[argc];
    argv[0] = str_cmd;
    return ffmpeg_cmd(argc,argv);
}

/**
 * 提取背景音乐前9s
 * @param argc
 * @param argv
 * @return
 */
int merge_voice(const char *src_video,const char *src_bgm,const char *dst_video,const char *dst_bgm_path){
    char *cmd_add_mp3 = (char *) malloc(128);
    sprintf(cmd_add_mp3,"ffmpeg -i %s -ss 00:00:00 -to 00:00:09 -c copy %s%s",dst_video,dst_bgm_path,__bg_9s);
    int ret = run_cmd(cmd_add_mp3);
    free(cmd_add_mp3);
    return ret;
}

//extern "C"
JNIEXPORT jint JNICALL
Java_com_example_vxg_vxgffmpeg_FFMpegUtils_merge(
        JNIEnv *env,
        jclass cls,
        jstring srcVideo,
        jstring srcBGM,
        jstring dstVideo,
        jstring dstBGMPath){

    const char *str_video_file = (const char *)(*env)->GetStringUTFChars(env,srcVideo,0);
    const char *str_bg_file = (const char *)(*env)->GetStringUTFChars(env,srcBGM,0);
    const char *str_dest = (const char *)(*env)->GetStringUTFChars(env,dstVideo,0);
    const char *str_dst_mbg_path = (const char *)(*env)->GetStringUTFChars(env,dstBGMPath,0);

    return merge_voice(str_video_file,str_bg_file,str_dest,str_dst_mbg_path);
}
