package com.zhao.uav.control;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import com.example.test04.R;
import com.zhao.uav.scoket.UAVScoket;

public class LeftUIControl {
    private FragmentActivity activity;


    public LeftUIControl(FragmentActivity activity){
        this.activity=activity;

    }
                                                            /**左边UI操作**/
    public void leftUI(){
        //点击向前按钮
        LinearLayout front=activity.findViewById(R.id.front);
        front.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=event.getAction();                              //获取触摸状态值

                if(x==MotionEvent.ACTION_DOWN){                       //如果是按下状态
                    front.setBackgroundColor(Color.argb(50,220,0,0));
                    UAVScoket.uavFront();
                }

                else if(x==MotionEvent.ACTION_UP){                   //如果是松开状态
                    front.setBackgroundColor(Color.argb(0,220,0,0));
                    UAVScoket.uavFY();
                }
                return true;
            }
        });
        //点击向后按钮
        LinearLayout after=activity.findViewById(R.id.after);
        after.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=event.getAction();                              //获取触摸状态值
                if(x==MotionEvent.ACTION_DOWN){                       //如果是按下状态
                    after.setBackgroundColor(Color.argb(50,220,0,0));
                    UAVScoket.uavAfter();
                }
                else if(x==MotionEvent.ACTION_UP){                   //如果是松开状态
                    after.setBackgroundColor(Color.argb(0,220,0,0));
                    UAVScoket.uavFY();
                }
                return true;
            }
        });
        //点击向左按钮
        LinearLayout left=activity.findViewById(R.id.left);
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=event.getAction();                              //获取触摸状态值
                if(x==MotionEvent.ACTION_DOWN){                       //如果是按下状态
                    left.setBackgroundColor(Color.argb(50,220,0,0));
                    UAVScoket.uavLeft();
                }
                else if(x==MotionEvent.ACTION_UP){                   //如果是松开状态
                    left.setBackgroundColor(Color.argb(0,220,0,0));
                    UAVScoket.uavHG();
                }
                return true;
            }
        });
        //点击向右按钮
        LinearLayout right=activity.findViewById(R.id.right);
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=event.getAction();                              //获取触摸状态值
                if(x==MotionEvent.ACTION_DOWN){                       //如果是按下状态
                    right.setBackgroundColor(Color.argb(50,220,0,0));
                    UAVScoket.uavRight();
                }
                else if(x==MotionEvent.ACTION_UP){                   //如果是松开状态
                    right.setBackgroundColor(Color.argb(0,220,0,0));
                    UAVScoket.uavHG();
                }
                return true;
            }
        });
    }

}
