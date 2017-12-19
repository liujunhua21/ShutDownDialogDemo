package com.example.liujunhua.shutdowndialogdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

/**
 * Created by liujunhua on 17-12-7.
 */

public class ShutDownViewTwo extends View {
    private ShutdownViewUtils mShutdownViewUtils;
    private Context mComtext;
    private Bitmap mShutdownButton;
    private Bitmap mRebootButton;
    private Bitmap mShutdownButtonPressd;
    private Bitmap mRebootButtonPressd;
    private Bitmap mCancelButton;

    private static ShutDownView.ShutdownAction mShutdownAction=null;
    private Window mWindow;

    public ShutDownViewTwo(Context context,Window window) {
        super(context);
        mWindow = window;
        mComtext = context ;
        mShutdownButton = BitmapFactory.decodeResource(mComtext.getResources(), R.drawable.shutdownbutton);
        mRebootButton= BitmapFactory.decodeResource(mComtext.getResources(), R.drawable.rebootbutton);
        mShutdownButtonPressd= BitmapFactory.decodeResource(mComtext.getResources(),R.drawable.shutdownpress);
        mRebootButtonPressd=BitmapFactory.decodeResource(mComtext.getResources(),R.drawable.rebootpress);
        //mCancelButton= BitmapFactory.decodeResource(mComtext.getResources(), R.drawable.shutdownbutton1);
    }

    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        // float density1 = dm.density;
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        // Display display = getWindowManager().getDefaultDisplay();
        // Point outSize = new Point();
        // display.getSize(outSize);//不能省略,必须有
        // int screenWidth = outSize.x;//得到屏幕的宽度
        // int screenHeight = outSize.y;//得到屏幕的高度
        super.onLayout(changed, left, top, right, bottom);
        // mDialog.onLayoutCallBack(left, top, right, bottom);

        setWindowAttribute(screenWidth, screenHeight);

    }

    private void setWindowAttribute(int screenWidth, int screenHeight) {

        android.view.WindowManager.LayoutParams windowParams = mWindow
                .getAttributes();
        windowParams.width = screenWidth;
        windowParams.height = screenHeight;

        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        // float density1 = dm.density;
        int mscreenWidth = dm.widthPixels;
        int mscreenHeight = dm.heightPixels;
        int adjustPix = dipToPix(16);

        windowParams.width += adjustPix;
        windowParams.height += adjustPix;
        if (windowParams.width > mscreenWidth) {
            windowParams.width = mscreenWidth;
        }
        if (windowParams.height > mscreenHeight) {
            windowParams.height = mscreenHeight;
        }
        //this.mWidth = screenWidth;
        //this.mHeight = screenHeight;
        mWindow.setAttributes(windowParams);
    }

    private int dipToPix(int i) {
        return 0;
    }

    private static final int TPW_START_FLAG = 2;
    private static final int TPW_RUNING = 1;
    private static final int TPW_CANCEL_DIALOG = 3;
    private static final int TPW_SHUTDOWN  = 4;
    private static final int TPW_REBOOT = 5;

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what){
                case  TPW_RUNING:
                    postInvalidate();
                    break;
                case  TPW_START_FLAG:
                    postInvalidate();
                    break;
                case  TPW_CANCEL_DIALOG:
                    cancel();
                    break;
                case  TPW_SHUTDOWN:
                    shutdown();
                    break;
                case TPW_REBOOT:
                    reboot();
            }
        }
    };

    private void shutdown() {
        if(mShutdownAction!=null)
            mShutdownAction.TPwShutdonw();

    }

    private void cancel() {
        if(mShutdownAction!=null)
            mShutdownAction.TPwCancel();
    }

    private void reboot() {
        if(mShutdownAction!=null)
            mShutdownAction.TPwReboot();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(mShutdownViewUtils ==null ){
            mShutdownViewUtils = new ShutdownViewUtils();
            mShutdownViewUtils.setHalf(canvas.getWidth()/2,canvas.getHeight()/2);
            //渐入动画 第一次打开画布时，组件有一个渐入的动画过程
            startTheFirstComeIn();

        }
        mShutdownViewUtils.updata();
        // canvas.drawLine(canvas.getWidth()/2,0,canvas.getWidth()/2,canvas.getHeight(),new Paint());
        // canvas.drawLine(0,canvas.getHeight()/2,canvas.getWidth(),canvas.getHeight()/2,new Paint());
        super.onDraw(canvas);
        //DrawCancelButton(canvas);
        //tpwDrawShutdownButton
        DrawBackground(canvas);
        DrawMapshutdown(canvas);
        DrawMapreboot(canvas);
        //tpwDrawText(canvas);
        //DrawRebootButton(canvas);
    }



    private Rect mSrcRect, mDestRect;
    private void DrawMapshutdown(Canvas canvas) {
        int left = (int) (mShutdownViewUtils.getHalfWidth() - mShutdownButton.getWidth() / 1-mShutdownViewUtils.getStartFirst()+mShutdownViewUtils.getStartTwo());
// 计算上边位置
        int top = mShutdownViewUtils.getHalfHeight() - mShutdownButton.getHeight() / 2;

        mSrcRect = new Rect(0, 0, mShutdownButton.getWidth(), mShutdownButton.getHeight());
        mDestRect = new Rect(left, top, left + mShutdownButton.getWidth()/2, top + mShutdownButton.getHeight()/2);
        Paint p = new Paint();
        p.setAntiAlias(true);
        if(pressedFlag){
            canvas.drawBitmap(mShutdownButtonPressd, mSrcRect, mDestRect, p);
        }else {
            canvas.drawBitmap(mShutdownButton, mSrcRect, mDestRect, p);}
        if(!mTheFirstComeInFlag){
            canvas.drawText("关机", left+45,top+185, mShutdownViewUtils.getRebootTextPaint());
        }
    }

    private void DrawMapreboot(Canvas canvas) {
        int left = (int) (mShutdownViewUtils.getHalfWidth() + mRebootButton.getWidth() / 2+mShutdownViewUtils.getStopFirst()-mShutdownViewUtils.getStartTwo());
// 计算上边位置
        int top = mShutdownViewUtils.getHalfHeight() - mRebootButton.getHeight() / 2;

        mSrcRect = new Rect(0, 0, mRebootButton.getWidth(), mRebootButton.getHeight());
        mDestRect = new Rect(left, top, left + mRebootButton.getWidth()/2, top + mRebootButton.getHeight()/2);
        Paint p = new Paint();
        p.setAntiAlias(true);
        if(pressedreboot){
            canvas.drawBitmap(mRebootButtonPressd, mSrcRect, mDestRect, p);
        }else {
            canvas.drawBitmap(mRebootButton, mSrcRect, mDestRect, p);}
        //canvas.drawBitmap(mRebootButton, mSrcRect, mDestRect, mShutdownViewUtils.getRebootButtonPaintPaint());
        if(!mTheFirstComeInFlag){
            canvas.drawText("重启", left+45,top+185, mShutdownViewUtils.getRebootTextPaint());
        }
    }

    //画背景
    private void DrawBackground(Canvas canvas) {
        //黑色区域
        float startAlpha = 200;
        Paint p=new Paint();
        p.setAlpha((int) startAlpha);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),p);
    }

    public static void setTpwShutdownAction(ShutDownViewTwo.ShutdownAction tpwShutdownAction){
        ShutDownViewTwo.mShutdownAction = (ShutDownView.ShutdownAction) tpwShutdownAction;
    }

    private boolean  pressedreboot = false;
    private boolean  pressedFlag = false ;
    private boolean  pressdstop = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mAnimationRuning || mTheFirstComeInFlag) return super.onTouchEvent(event);
        //int action = event.getAction();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                float x = event.getX();
                float y = event.getY();
                if (x > mShutdownViewUtils.getHalfWidth() + mRebootButton.getWidth() / 6
                        && x < mShutdownViewUtils.getHalfWidth() + mRebootButton.getWidth() / 6 + mRebootButton.getWidth() / 2
                        && y > mShutdownViewUtils.getHalfHeight() - mRebootButton.getHeight() / 2
                        && y < mShutdownViewUtils.getHalfHeight()  + mRebootButton.getHeight() / 6) {
                    //Toast.makeText(mComtext, "重启", Toast.LENGTH_SHORT).show();
                    pressedreboot = true;
                    //mHandler.sendEmptyMessage(TPW_START_FLAG);
                    //mHandler.sendEmptyMessage(TPW_REBOOT);
                } else if (x > mShutdownViewUtils.getHalfWidth() - mShutdownButton.getWidth() / 2 - mShutdownButton.getWidth() / 6
                        && x < mShutdownViewUtils.getHalfWidth() - mShutdownButton.getWidth() / 6
                        && y > mShutdownViewUtils.getHalfHeight() - mShutdownButton.getHeight() / 2
                        && y < mShutdownViewUtils.getHalfHeight()  + mShutdownButton.getHeight() / 6) {
                    pressedFlag = true;
                    //mHandler.sendEmptyMessage(TPW_RUNING);
                    // mHandler.sendEmptyMessage(TPW_SHUTDOWN);
                } //else
            } //animateEnd();
            // pressedFlag = false;
            // break;
            return true;
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                if (x > mShutdownViewUtils.getHalfWidth() + mRebootButton.getWidth() / 6
                        && x < mShutdownViewUtils.getHalfWidth() + mRebootButton.getWidth() / 6 + mRebootButton.getWidth() / 2
                        && y > mShutdownViewUtils.getHalfHeight() - mRebootButton.getHeight() / 2
                        && y < mShutdownViewUtils.getHalfHeight()  + mRebootButton.getHeight() / 6) {
                    // Toast.makeText(mComtext, "重启", Toast.LENGTH_SHORT).show();
                    pressedreboot =false;
                    mHandler.sendEmptyMessage(TPW_START_FLAG);
                    mHandler.sendEmptyMessage(TPW_REBOOT);
                } else if (x > mShutdownViewUtils.getHalfWidth() - mShutdownButton.getWidth() / 2 - mShutdownButton.getWidth() / 6
                        && x < mShutdownViewUtils.getHalfWidth() - mShutdownButton.getWidth() / 6
                        && y > mShutdownViewUtils.getHalfHeight() - mShutdownButton.getHeight() / 2
                        && y < mShutdownViewUtils.getHalfHeight()  + mShutdownButton.getHeight() / 6) {
                    pressedFlag = false;
                    mHandler.sendEmptyMessage(TPW_RUNING);
                    mHandler.sendEmptyMessage(TPW_SHUTDOWN);
                } else{
                    // pressedreboot =false;
                    // pressedFlag = false;
                    mHandler.sendEmptyMessage(TPW_RUNING);
                    animateEnd();}
                break;
            case MotionEvent.ACTION_MOVE:
                //pressedreboot =false;
                //animateEnd();
                mHandler.sendEmptyMessage(TPW_RUNING);
                break;



        }

        return super.onTouchEvent(event);
    }



    //渐入动画 第一次打开画布时，组件有一个渐入的动画过程,不会让人觉得突兀
    private boolean mTheFirstComeInFlag = false ;
    private void startTheFirstComeIn() {
        /*总时常为200毫秒，
        *这段时间event事件不作处理 -- 标志符
        * start区域的动画 -200
        * stop区域的动画 100
        * 文字区域的动画  alpha 0-100
        */
        int time = 250;
        final int times = 50 ;
        final int timemode = time/times;
        final float startmode = mShutdownViewUtils.getStartFirst()/times;
        final float stopmode = mShutdownViewUtils.getStopFirst()/times;

        mTheFirstComeInFlag = true ;

        new Thread(){
            public void run(){
                for(int i = 0 ; i < times ; i++) {
                    try {
                        //改变渐入动画的值
                        mShutdownViewUtils.setStartFirst(mShutdownViewUtils.getStartFirst() - startmode);
                        mShutdownViewUtils.setStopFirst(mShutdownViewUtils.getStopFirst() - stopmode);
                        mHandler.sendEmptyMessage(TPW_START_FLAG);
                        Thread.sleep(timemode);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mShutdownViewUtils.setStartFirst(0);
                mShutdownViewUtils.setStopFirst(0);
                mHandler.sendEmptyMessage(TPW_START_FLAG);
                mTheFirstComeInFlag = false ;
            }
        }.start();
    }



    private boolean mAnimationRuning = false ;
    private void animateEnd() {
        //圆球back动画
        final float move = 1000;
        final int sum = 50 ;
        final float mode = move/sum;
        // 动画开始，不接受touch event
        mAnimationRuning = true ;
        mShutdownViewUtils.setAssociatedArcPaintAlpha(100);
        mShutdownViewUtils.setAssociatedArcPaintColor(0XFFFFA07A);

        new Thread(){
            public void run(){
                for(int i = 0 ; i < sum ; i++){
                    mShutdownViewUtils.setStartTwo(mShutdownViewUtils.getStartTwo()-mode);
                    mHandler.sendEmptyMessage(TPW_RUNING);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                mHandler.sendEmptyMessage(TPW_CANCEL_DIALOG);
                //归位
                mShutdownViewUtils.setMovingX(0);
                mHandler.sendEmptyMessage(TPW_RUNING);
                mAnimationRuning = false;
                mShutdownViewUtils.setStartDown(false);
            }
        }.start();
    }



    public interface ShutdownAction{
        void TPwShutdonw();
        void TPwCancel();
        void TPwReboot();
    }
}
