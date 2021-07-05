package com.zhao.uav.control;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.test04.R;
import com.zhao.uav.scoket.UAVScoket;

import java.io.IOException;

public class TopUIControl {

    private FragmentActivity activity;
    private boolean switchflag=false;           //标记是否开关
    private int x56;
    private int x78;
    private int x910;
    private TextView hx;
    private TextView hg;
    private TextView fy;
    public static Photo photo;                  //定义动画类



    /**
     *调用顶部UI操作类
     * @param activity 传入MainActivity
            **/
    public TopUIControl(FragmentActivity activity){
        this.activity=activity;
        photo=new Photo(activity);              //调用动画类

    }
    /**顶部UI操作**/
    public void topUI(){
        //点击开关控件
        ImageView onoff=activity.findViewById(R.id.onoff);
        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!switchflag){                        //点击开
                    onoff.setBackgroundColor(Color.argb(50,220,0,0));
                    switchflag=true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                UAVScoket.connectUAV();     //连接飞机
                            } catch (Exception e) {
                                e.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        switchflag=false;
                                        BottomUIControl.speed.setText("速度  "+0+"m/s");
                                        onoff.setBackgroundColor(Color.argb(0,220,0,0));
                                        Toast.makeText(activity,"连结失败，请检查网络",Toast.LENGTH_SHORT).show();;
                                        TopUIControl.photo.photoZoom(false);        //关闭动画
                                    }
                                });
                            }
                        }
                    }).start();
                }

                else{                                     //点击关
                    onoff.setBackgroundColor(Color.argb(0,220,0,0));
                    switchflag=false;
                    BottomUIControl.speed.setText("速度  "+0+"m/s");
                    TopUIControl.photo.photoZoom(false);        //关闭动画
                    try {
                        UAVScoket.stopUAV();            //让无人机停止转动
                        UAVScoket.closeUAV();           //关闭无人机连接
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        //点击设置按钮
        ImageView setting=activity.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.动态加载布局文件
                View view = LayoutInflater.from(activity).inflate(R.layout.settinglayout,null);
                //2.对布局进行操作
                settingUAV(view);
                setGear(view);
                savereadUAV(view);
                //3.调用对话框类
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                //4.设置布局
                builder.setView(view);
                //5.监测对话框关闭，当窗口关闭时调用此方法。
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Toast.makeText(activity,"对话关闭",Toast.LENGTH_SHORT).show();
                    }
                });
                //6.显示对话框
                builder.show();
            }
        });


        //点击其他按钮
        ImageView other=activity.findViewById(R.id.other);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.动态加载布局文件
                View view = LayoutInflater.from(activity).inflate(R.layout.otherlayout,null);
                //2.对布局进行操作

                //3.调用对话框类
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                //4.设置布局
                builder.setView(view);
                //5.监测对话框关闭，当窗口关闭时调用此方法。
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Toast.makeText(activity,"对话关闭",Toast.LENGTH_SHORT).show();
                    }
                });
                //6.显示对话框
                builder.show();
            }
        });

        //点击单选框
        RadioGroup rg=activity.findViewById(R.id.gear3);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=activity.findViewById(checkedId);
                String s=rb.getText().toString();


                if ("慢".equals(s)){
                    UAVScoket.powergear=10;
                }
                else if ("中".equals(s)){
                    UAVScoket.powergear=100;
                }
                else if ("快".equals(s)){
                    UAVScoket.powergear=200;
                }
            }
        });
    }

    /**
    *设置对话框里无人机参数
    * **/
    public void settingUAV(View view){
        //调节航向值
        hx=view.findViewById(R.id.hanxiangzhi);
        Button hx_left=view.findViewById(R.id.hangxiang_left);
        hx_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x56 = UAVScoket.setHXLeft();
                hx.setText(x56+"");
            }
        });
        Button hx_right=view.findViewById(R.id.hangxiang_right);
        hx_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x56 = UAVScoket.setHXRight();
                hx.setText(x56+"");
            }
        });

        //调节横滚值
        hg=view.findViewById(R.id.henggunzhi);
        Button hg_left=view.findViewById(R.id.hg_left);
        hg_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x78=UAVScoket.setHGLeft();
                hg.setText(x78+"");
            }
        });


        Button hg_right=view.findViewById(R.id.hg_right);
        hg_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x78=UAVScoket.setHGRight();
                hg.setText(x78+"");
            }
        });

        //调节俯仰值
        fy=view.findViewById(R.id.fuyang);
        Button fy_front=view.findViewById(R.id.fy_front);
        fy_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x910=UAVScoket.setFYFront();
                fy.setText(x910+"");
            }
        });

        Button fy_after=view.findViewById(R.id.fy_after);
        fy_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x910=UAVScoket.setFYAfter();
                fy.setText(x910+"");
            }
        });

        int xx56=UAVScoket.getHX();
        int xx78=UAVScoket.getHG();
        int xx910=UAVScoket.getFY();
        hx.setText(xx56+"");
        hg.setText(xx78+"");
        fy.setText(xx910+"");
    }

    /**
     * 设置对话框细调粗调
     * @param view 传入设置对话框
     * **/
    public void setGear(View view){
        //粗调按钮
        Button gear1=view.findViewById(R.id.gear1);
        gear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UAVScoket.gear=50;
            }
        });
        //细调
        Button gear2=view.findViewById(R.id.gear2);
        gear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UAVScoket.gear=2;
            }
        });
    }

    /**
     * 点击保存和读取按钮
     * @param view 传入对话框对象
     * **/
    public void savereadUAV(View view){
        //点击保存按钮
        Button save_bt=view.findViewById(R.id.save);
        save_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取SharedPreferences对象
                SharedPreferences sp=activity.getSharedPreferences("fly",activity.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                //保存飞机参数：航向值，横滚值，俯仰值
                editor.putInt("hangxiang",x56);
                editor.putInt("henggun",x78);
                editor.putInt("fuyang",x910);
                editor.commit();
                Toast.makeText(activity,"保存成功",Toast.LENGTH_SHORT).show();
            }
        });

        //点击读取按钮
        Button read_bt=view.findViewById(R.id.read);
        read_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取SharedPreferences对象
                SharedPreferences sp=activity.getSharedPreferences("fly",activity.MODE_PRIVATE);

                //读取保存的飞机参数
                int xx56=sp.getInt("hangxiang",0);
                int xx78=sp.getInt("henggun",0);
                int xx910=sp.getInt("fuyang",0);

                //同步显示参数
                hx.setText(xx56+"");
                hg.setText(xx78+"");
                fy.setText(xx910+"");

                //同步参数到飞机
                UAVScoket.setHX(xx56);
                UAVScoket.setHG(xx78);
                UAVScoket.setFY(xx910);
            }
        });
    }
}
