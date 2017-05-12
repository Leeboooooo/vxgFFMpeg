package com.example.vxg.vxgffmpeg;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ProgressBar loading;
    private String APP_DIR;

    // 成功
    private static final int WHAT_SUCCESS = 1;
    // 失败
    private static final int WHAT_FAIL = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        loading = (ProgressBar) findViewById(R.id.loadding);

        APP_DIR = Environment.getExternalStorageDirectory().toString() + "/JNITest/";
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHAT_SUCCESS:
                    break;
                case WHAT_FAIL:
                    break;
                default:
                    break;
            }
        }
    };

    public void mergeVideo(View view){
        loading.setVisibility(View.VISIBLE);
        new MergeTask().execute();
    }

    public class MergeTask extends AsyncTask<String,Void,Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            String srcVideo = APP_DIR + "src.mp4";
            String bgMusicFile = APP_DIR + "test.mp3";
            String destPath = APP_DIR + "dest.mp4";
            int ret = FFMpegUtils.getInstance().merge_video(srcVideo,bgMusicFile,destPath);
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
}
