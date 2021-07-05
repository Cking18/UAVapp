package com.zhao.uav.scoket;


import com.zhao.uav.control.TopUIControl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class UAVScoket {

    private static Socket socket;
    private static OutputStream out;


    private static byte[] data = new byte[34];                   //定义通信数组
    private static boolean flag = false;
    private static int x34 = 20;                                 //临时存放油门值
    private static int x56 = 1500;                               //临时存放航向值
    private static int x78 = 1400;                               //临时存放横滚值
    private static int x910 = 1730;                              //临时存放俯仰值
    public static  int gear=100;                                //定义粗调细调挡位
    public static int powergear=20;                             //定义油门挡位


    /**
     * 私有化构造方法
     **/
    private UAVScoket() {
    }

    public static void setYM(int x34){
        UAVScoket.x34=x34;
        data[3] = (byte) (x34 >> 8);
        data[4] = (byte) (x34 & 0xff);
    }
    public static int getYM(){
        return x34;
    }

    /**
     * 链接无人机，调用时需要加线程
     **/
    public static void connectUAV() throws Exception {


        //第3.1步 调用Socket类设置要访问的IP地址和端口号
        socket = new Socket();
        socket.connect(new InetSocketAddress("192.168.4.1", 333), 1000);
        //第3.2步 用输出给服务端发数据
        out = socket.getOutputStream();
        out.write("GEC\r\n".getBytes());       //发送连接指令给无人机
        out.flush();
        initData();
        startUAV();

    }

    /**
     * 关闭无人机连接
     **/
    public static void closeUAV() throws IOException {
        if (socket != null) {
            socket.close();
        }
        socket = null;
    }

    /**
     * 初始化通信数组
     **/
    public static void initData() {
        data[0] = (byte) 0xAA;                            //通信协议固定值
        data[1] = (byte) 0xC0;                            //通信协议固定值
        data[2] = (byte) 0x1C;                            //通信协议固定值

        data[3] = (byte) (x34 >> 8);                         //油门值高八位 控制上下方向
        data[4] = (byte) (x34 & 0xff);                       //油门值低八位 控制上下方向
        data[5] = (byte) (x56 >> 8);                        //航向值八位 控制左旋右旋方向
        data[6] = (byte) (x56 & 0xff);                      //航向值八位 控制左旋右旋方向
        data[7] = (byte) (x78 >> 8);                        //横滚值高八位 控制左右方向
        data[8] = (byte) (x78 & 0xff);                      //横滚值高八位 控制左右方向
        data[9] = (byte) (x910 >> 8);                        //俯仰值高八位 控制前后方向
        data[10] = (byte) (x910 & 0xff);                     //俯仰值高八位 控制前后方向


        data[31] = (byte) 0x1C;                            //通信协议固定值
        data[32] = (byte) 0x0D;                            //通信协议固定值
        data[33] = (byte) 0x0A;                            //通信协议固定值
    }

    /**
     * 启动无人机
     **/
    public static void startUAV() throws Exception {
        if (!flag) {
            flag = true;
            x34 = 20;
            data[3] = (byte) (x34 >> 8);                         //油门值的高八位
            data[4] = (byte) (x34 & 0xff);                        //油门值的低八位

            TopUIControl.photo.photoZoom(true);             //开启动画

            while (flag) {
                out.write(data);                        //发送通信数组给无人机
                Thread.sleep(5);                    //让程序暂停5毫秒
            }
        }
    }

    /**
     * 让飞机停止转动
     **/

    public static void stopUAV() {
        flag = false;
        x34 = 0;
        data[3] = (byte) (x34 >> 8);                         //油门值的高八位
        data[4] = (byte) (x34 & 0xff);                       //油门值的低八位

    }

    /**
     * 增加油门值
     **/
    public static int addPower() {
        if (x34 <= 980) {
            x34 += powergear;
            data[3] = (byte) (x34 >> 8);                         //油门值的高八位
            data[4] = (byte) (x34 & 0xff);                        //油门值的低八位
        }
        return x34;
    }

    /**
     * 减少油门值
     **/
    public static int lessPower() {
        if (x34 >= powergear) {
            x34 -= powergear;
            data[3] = (byte) (x34 >> 8);                         //油门值的高八位
            data[4] = (byte) (x34 & 0xff);                        //油门值的低八位
        }
        return x34;
    }

    /**
     * 设置航向值，向左旋转
     * return 返回航向值
     * **/
    public static int setHXLeft(){
        if (x56<=2990){
            x56+=gear;
            data[5] = (byte) (x56 >> 8);                        //航向值八位 控制左旋右旋方向
            data[6] = (byte) (x56 & 0xff);                      //航向值八位 控制左旋右旋方向
        }
        return x56;
    }

    /**
     * 设置航向值，向右旋转
     * return 返回航向值
     * **/
    public static int setHXRight(){
        if (x56>=gear){
            x56-=gear;
            data[5] = (byte) (x56 >> 8);                        //航向值八位 控制左旋右旋方向
            data[6] = (byte) (x56 & 0xff);                      //航向值八位 控制左旋右旋方向
        }
        return x56;
    }

    /**
     * 设置黄滚值，向左
     * return 返回横滚值
     * **/
    public static int setHGLeft(){
        if (x78<=2990){
            x78+=gear;
            data[7] = (byte) (x78 >> 8);                        //横滚值高八位 控制左右方向
            data[8] = (byte) (x78 & 0xff);                      //横滚值高八位 控制左右方向

        }
        return x78;
    }

    /**
     * 设置黄滚值，向右
     * return 返回横滚值
     * **/
    public static int setHGRight(){
        if (x78>=gear){
            x78-=gear;
            data[7] = (byte) (x78 >> 8);                        //横滚值高八位 控制左右方向
            data[8] = (byte) (x78 & 0xff);                      //横滚值高八位 控制左右方向

        }
        return x78;
    }

    /**
     * 设置俯仰值，向后
     * return 返回俯仰值
     * **/
    public static int setFYFront(){
        if (x910<=2990){
            x910+=gear;
            data[9] = (byte) (x910 >> 8);                        //俯仰值高八位 控制前后方向
            data[10] = (byte) (x910 & 0xff);                     //俯仰值高八位 控制前后方向

        }
        return x910;
    }

    /**
     * 设置俯仰值，向后
     * return 返回俯仰值
     * **/
    public static int setFYAfter(){
        if (x910>=gear){
            x910-=gear;
            data[9] = (byte) (x910 >> 8);                        //俯仰值高八位 控制前后方向
            data[10] = (byte) (x910 & 0xff);                     //俯仰值高八位 控制前后方向

        }
        return x910;
    }

    /**
     * 获取航向值
     * @return 返回航向值
     * **/
    public static int getHX(){
        return x56;
    }

    /**
     * 获取横滚值
     * @return 返回横滚值
     * **/
    public static int getHG(){
        return x78;
    }

    /**
     * 获取俯仰值
     * @return 返回俯仰值
     * **/
    public static int getFY(){
        return x910;
    }


    /**设置航向值
     * @param x56 传入航向值
     * **/
    public static void setHX(int x56){
        UAVScoket.x56=x56;
        data[5] = (byte) (UAVScoket.x56 >> 8);                        //航向值八位 控制左旋右旋方向
        data[6] = (byte) (UAVScoket.x56 & 0xff);                      //航向值八位 控制左旋右旋方向
    }

    /**设置横滚值
     * @param x78 传入横滚值
     * **/
    public static void setHG(int x78){
        UAVScoket.x78=x78;
        data[7] = (byte) (UAVScoket.x78 >> 8);                        //横滚值高八位 控制左右方向
        data[8] = (byte) (UAVScoket.x78 & 0xff);                      //横滚值高八位 控制左右方向
    }

    /**设置俯仰值
     * @param x910 传入俯仰值
     * **/
    public static void setFY(int x910){
        UAVScoket.x910=x910;
        data[9] = (byte) (UAVScoket.x910 >> 8);                        //俯仰值高八位 控制前后方向
        data[10] = (byte) (UAVScoket.x910 & 0xff);                     //俯仰值高八位 控制前后方向

    }

    /**
     * 向前飞
     * **/
    public static void uavFront(){
        data[9] = (byte) ((x910+150) >> 8);                        //俯仰值高八位 控制前后方向
        data[10] = (byte) ((x910+150) & 0xff);                     //俯仰值高八位 控制前后方向
    }

    /**
     * 向后飞
     * **/
    public static void uavAfter(){
        data[9] = (byte) ((x910-150) >> 8);                        //俯仰值高八位 控制前后方向
        data[10] = (byte) ((x910-150) & 0xff);                     //俯仰值高八位 控制前后方向
    }

    /**
     * 向左飞
     * **/
    public static void uavLeft(){
        data[7] = (byte) ((x78+150) >> 8);                        //横滚值高八位 控制左右方向
        data[8] = (byte) ((x78+150) & 0xff);                      //横滚值高八位 控制左右方向
    }

    /**
     * 向右飞
     * **/
    public static void uavRight(){
        data[7] = (byte) ((x78-150) >> 8);                        //横滚值高八位 控制左右方向
        data[8] = (byte) ((x78-150) & 0xff);                      //横滚值高八位 控制左右方向
    }

    /**
     * 左旋转
     * **/
    public static void uavLeftLeft(){
        data[5] = (byte) ((x56+150) >> 8);                        //航向值八位 控制左旋右旋方向
        data[6] = (byte) ((x56+150) & 0xff);                      //航向值八位 控制左旋右旋方向
    }

    /**
     * 右旋转
     * **/
    public static void uavRightRight(){
        data[5] = (byte) ((x56-150) >> 8);                        //航向值八位 控制左旋右旋方向
        data[6] = (byte) ((x56-150) & 0xff);                      //航向值八位 控制左旋右旋方向
    }

    /**
     * 还原航向值到平衡值
     * **/
    public static void uavHX(){
        data[5] = (byte) (x56 >> 8);                        //航向值八位 控制左旋右旋方向
        data[6] = (byte) (x56 & 0xff);                      //航向值八位 控制左旋右旋方向
    }

    /**
     * 还原横滚值到平衡值
     * **/
    public static void uavHG(){
        data[7] = (byte) (x78 >> 8);                        //横滚值高八位 控制左右方向
        data[8] = (byte) (x78 & 0xff);                      //横滚值高八位 控制左右方向
    }

    /**
     * 还原俯仰值到平衡值
     * **/
    public static void uavFY(){
        data[9] = (byte) (x910 >> 8);                        //俯仰值高八位 控制前后方向
        data[10] = (byte) (x910 & 0xff);                     //俯仰值高八位 控制前后方向
    }
}
