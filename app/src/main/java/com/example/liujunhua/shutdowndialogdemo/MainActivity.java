package com.example.liujunhua.shutdowndialogdemo;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Dialog mTPwDialog;
    private Context mContext = null;

//    public ShutDownView(Context mContext) {
//        this.mContext = mContext;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        mContext = this;
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Configuration newConfig = getResources().getConfiguration();


                if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mTPwDialog.dismiss();
                 }

                    mTPwDialog = new Dialog(mContext, R.style.Dialog_Fullscreen);
                    // mTPwDialog= new Dialog(mContext,R.style.disable_background_dim_dialog);
                    ShutDownView mShutdownView = new ShutDownView(mContext, mTPwDialog.getWindow());


                    ShutDownView.setTpwShutdownAction(new ShutDownView.ShutdownAction() {
                        @Override
                        public void TPwShutdonw() {
                            //mWindowManagerFuncs.shutdown(false);
                            // Toast.makeText(mComtext, "关机", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void TPwReboot() {
                            //Toast.makeText(mComtext, "重启", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void TPwCancel() {
                            mTPwDialog.dismiss();
                        }
                    });


//                mTpwDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
                    mTPwDialog.setContentView(mShutdownView);
                    mTPwDialog.show();

                     //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏

                }
//                else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//                    mTPwDialog= new Dialog(mContext,R.style.Dialog_Fullscreen);
//                    // mTPwDialog= new Dialog(mContext,R.style.disable_background_dim_dialog);
//                    ShutDownViewTwo mShutdownView = new ShutDownViewTwo(mContext,mTPwDialog.getWindow());
//
//
//                    ShutDownViewTwo.setTpwShutdownAction(new ShutDownViewTwo.ShutdownAction() {
//                        @Override
//                        public void TPwShutdonw() {
//                            //mWindowManagerFuncs.shutdown(false);
//                            // Toast.makeText(mComtext, "关机", Toast.LENGTH_SHORT).show();
//                        }
//                        @Override
//                        public void TPwReboot() {
//                            //Toast.makeText(mComtext, "重启", Toast.LENGTH_SHORT).show();
//                        }
//                        @Override
//                        public void TPwCancel() {
//                            mTPwDialog.dismiss();
//                        }
//                    });
//
//
////                mTpwDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//                    mTPwDialog.setContentView(mShutdownView);
//                    mTPwDialog.show();
//
//
//
//                }
//
//
//            }
//        });


    //        }
        });
    }
}