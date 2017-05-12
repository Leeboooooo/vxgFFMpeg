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
int merge_voice(int argc,char *argv[]){
    if(argc!=4) errx(1,"usage: %s videoFile voiceFile destFile\n",argv[0]);
    char *cmd_crop_mp3 = (char *) malloc(32);
    sprintf(cmd_crop_mp3," -i %s -ss 00:00:00 -to 00:00:09 -c copy %s",argv[2],__bg_9s);
    char *str_cmd = argv[0];
    str_cmd = strcpy(str_cmd,cmd_crop_mp3);
    return run_cmd(str_cmd);
}

//extern "C"
JNIEXPORT jint JNICALL
Java__com_example_vxg_vxgffmpeg_FFMpegUtils_merge_video(
        JNIEnv *env,
        jclass cls,
        jstring videoFile,
        jstring voiceFile,
        jstring destFile){
    int argc = 4;
    char * argv[argc] ;
    for (int i = 0; i < argc; ++i) {
        argv[i] = (char*)malloc(sizeof(char)*64);
    }

    const char *str_video_file = (const char *)(*env)->GetStringUTFChars(env,videoFile,0);
    const char *str_bg_file = (const char *)(*env)->GetStringUTFChars(env,voiceFile,0);
    const char *str_dest = (const char *)(*env)->GetStringUTFChars(env,destFile,0);

    strncpy(argv[0],(char *) "ffmpeg", sizeof(strlen("ffmpeg")));
    strncpy(argv[1],str_video_file, sizeof(strlen(str_video_file)));
    strncpy(argv[1],str_bg_file, sizeof(strlen(str_bg_file)));
    strncpy(argv[1],str_dest, sizeof(strlen(str_dest)));

    return merge_voice(argc,argv);
}
