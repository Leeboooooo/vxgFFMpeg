package com.example.vxg.vxgffmpeg;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ProgressBar loading;
    private String APP_DIR;
    private TextView tvShow;

    // 成功
    private static final int WHAT_SUCCESS = 1;
    // 失败
    private static final int WHAT_FAIL = 0;
    private static final int WHAT_FAIL_NOT_EXIST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        tvShow = (TextView) findViewById(R.id.sample_text);
        loading = (ProgressBar) findViewById(R.id.loadding);

        APP_DIR = Environment.getExternalStorageDirectory().toString() + "/JNITest/";
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHAT_SUCCESS:
                    tvShow.setText("Successed.");
                    break;
                case WHAT_FAIL:
                    tvShow.setText("Failed.");
                    break;
                case WHAT_FAIL_NOT_EXIST:
                    tvShow.setText("源文件不存在");
                    break;
                default:
                    break;
            }
        }
    };

    public void crop(View view){
        loading.setVisibility(View.VISIBLE);
        new Crop9sBGMTask().execute();
    }

    public void removeBGM(View view){
        loading.setVisibility(View.VISIBLE);
        new RemoveBGMTask().execute();
    }

    public void merge(View view){
        loading.setVisibility(View.VISIBLE);
        new MergeVideoAndBGMTask().execute();
    }

    /**
     * 剪裁九秒短视频
     */
    public class Crop9sBGMTask extends AsyncTask<String,Void,Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            String srcVideo = APP_DIR + "src.mp4";
            String bgMusicFile = APP_DIR + "test.mp3";
            String destPath = APP_DIR + "dest.mp4";
            boolean isExist = FFMpegUtils.dirIsExist(APP_DIR);
            isExist = FFMpegUtils.dirIsExist(bgMusicFile);
            int ret = FFMpegUtils.getInstance().crop_backgroud_music(bgMusicFile,APP_DIR);
            if (1 == ret) {
                handler.obtainMessage(WHAT_SUCCESS).sendToTarget();
            }
            return WHAT_SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            loading.setVisibility(View.GONE);
        }
    }

    /**
     * 合并音视频
     */
    public class MergeVideoAndBGMTask extends AsyncTask<String,Void,Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            String srcVideo = APP_DIR + "src.mp4";
            String bgMusicFile = APP_DIR + "9s.mp3";
            String destPath = APP_DIR + "dest.mp4";
            boolean isExist = FFMpegUtils.dirIsExist(srcVideo);
            if (!isExist) return WHAT_FAIL_NOT_EXIST;
            isExist = FFMpegUtils.dirIsExist(bgMusicFile);
            if (!isExist) return WHAT_FAIL_NOT_EXIST;
            int ret = FFMpegUtils.getInstance().merge(srcVideo,bgMusicFile,destPath);
            if (ret < 0) return WHAT_FAIL;
            handler.obtainMessage(WHAT_SUCCESS).sendToTarget();
            return WHAT_SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            loading.setVisibility(View.GONE);
        }
    }

    /**
     * 移除背景音乐
     */
    public class RemoveBGMTask extends AsyncTask<String,Void,Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            String src = APP_DIR + "src.mp4";
            String dest = APP_DIR + "dest.mp4";
            boolean isExist = FFMpegUtils.dirIsExist(APP_DIR);
            isExist = FFMpegUtils.dirIsExist(src);
            if (!isExist) return WHAT_FAIL;
            int ret = FFMpegUtils.getInstance().remove_bgm_from_video(src,dest);
            if (ret < 0) return WHAT_FAIL;
            handler.obtainMessage(WHAT_SUCCESS).sendToTarget();
            return WHAT_SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            loading.setVisibility(View.GONE);
        }
    }
}
