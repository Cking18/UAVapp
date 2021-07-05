package com.zhao.uav.control;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.test04.R;


public class Photo {
    private FragmentActivity activity;
    private  ImageView iv;
    private boolean flag=false;

    public Photo(FragmentActivity activity){
        this.activity=activity;
        iv=activity.findViewById(R.id.imageView);
    }


    /**
     * 开启或关闭动画
     * @param flag 传入true表示开启动画，传入false表示关闭动画
     * **/
    public void photoZoom(boolean flag){
        this.flag=flag;
        if(this.flag){
            new Thread(new Zoom()).start();
        }
    }

    private class Zoom implements Runnable{
        @Override
        public void run() {
            try {
                                                                                            //把要显示的图片转换成Bitmap
                Bitmap bitmap= BitmapFactory.decodeResource(activity.getResources(),R.mipmap.huanghe);
                float imagewidth = bitmap.getWidth();                                     //获取图片宽度
                float imageheight = bitmap.getHeight();                                   //获取图片高度

                Matrix matrix = new Matrix();                                           //调用矩阵
                Matrix matrix2=new Matrix();                                            //保存矩阵对象

                matrix.set(iv.getImageMatrix());
                matrix2.set(matrix);
                while (flag){
                    for(float s=1.0f;s<1.8f;s+=0.001f){                               //遍历位移
                        Thread.sleep(100);
                        matrix.set(matrix2);
                        matrix.postScale(s,s,imagewidth*0.65f,imageheight*0.4f);
                        Message message= Message.obtain();
                        message.obj=matrix;
                        handler.sendMessage(message);
                        if(flag==false){
                            break;
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private  Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            iv.setImageMatrix((Matrix) msg.obj);
        }
    };
}
