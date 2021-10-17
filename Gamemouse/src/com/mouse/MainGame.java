package com.mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

/**
 * @author wen
 */
public  class MainGame extends JFrame implements Runnable{
  private   ImageIcon hummerIcon;
  private   ImageIcon mouseIcon;
  private   ImageIcon bgIcon ;
  private   JLabel[] mouseLbl;
  private   JLabel levelbel;
  private   JLabel secorl;
    //显示时长
    private int showTime=1500;
    //地鼠速度
    private int speed=1000;
    //地鼠个数
    private int count =1;
    //等级
    private int level=1;
    //升级界限（分数）
    private int [] ing={20,40,60,70};
    //每个地鼠的分值
    private  int simpleScore=5;
    //分数
    private int score;

    MainGame(){
        //加载图像资源
        loadImg();

        //初始化窗体
        initWindow();

        //初始化地鼠
        initMouse();

        //初始化背景
        initBg();

        //初始化锤子
//        initHummer();
        //显示窗体
        setVisible(true);
        //启动线程
        new Thread(this).start();
    }
   /**
    * 初始化锤子
    */





    /**
    初始化地鼠
     */
    private void initMouse(){
        //地鼠的坐标
        int[][] mousePostion ={{ 88, 53 }, { 240, 53 }, { 393, 53 },
                { 60, 160 },{ 248, 160 },{ 430, 160 },
                { 36, 296 },{ 240, 296 },{ 445, 296 } };

        //创建地鼠对象数组
        mouseLbl =new JLabel[9];
        for (int i = 0; i <mouseLbl.length ; i++) {
            mouseLbl[i] =new JLabel(mouseIcon);
            //设置地鼠位置
            mouseLbl[i].setBounds(mousePostion[i][0],mousePostion[i][1]+35,mouseIcon.getIconWidth(),mouseIcon.
                    getIconHeight());
            add(mouseLbl[i]);
            //先隐藏
            mouseLbl[i].setVisible(false);
            mouseLbl[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //地鼠点击时执行
                    //事件源
                    JLabel source =(JLabel) e.getSource();
                    //地鼠隐藏
                    source.setVisible(false);
                    //处理分数和等级
                    hitMouse();
                }
            });
        }

    }
    /**
     * 点击地鼠 ，的变化
     */
    private void hitMouse(){
        //加分
        score+=simpleScore;
        //判断是否升级
        if (score>ing[level-1]&&level<ing.length){
            level++;
            count++;
            showTime-=200;
            speed-=200;
        }
        //把修改后的等级和分数更新显示
        secorl.setText("分数"+score);
        levelbel.setText("等级"+level);

    }
    /**
    初始化背景
 */
    private  void initBg(){
        //添加背景
        JLabel back =new JLabel(bgIcon);
        back.setBounds(0,0, bgIcon.getIconWidth(), bgIcon.getIconHeight());
        add(back);
    }

    /**
     * 初始化窗体
     */
    private void initWindow(){

        //初始化窗体大小
        setBounds(700,300,bgIcon.getIconWidth(),bgIcon.getIconHeight()+35);

        //禁用重置窗体
        setResizable(false);

        //设置标题
        setTitle("打地鼠");
        //设置图标
        setIconImage(hummerIcon.getImage());
        //设置关闭窗体时退出jvm
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Font font=new Font("黑体",Font.BOLD,20);
        //显示初始等级
         levelbel=new JLabel("等级"+level);
         //字号
         levelbel.setFont(font);
         //颜色
         levelbel.setForeground(Color.BLUE);
        levelbel.setBounds(60,10,100,40);
        add(levelbel);
        //显示分数
         secorl=new JLabel("分数"+score);
        secorl.setBounds(460,10,100,40);
        add(secorl);
        //自定义鼠标样式
        setCursor(getToolkit().createCustomCursor(hummerIcon.getImage(),new Point(1,1),""));
        //设置绝对布局
        setLayout(null);
    }

    /**
     * 初始化图片
     */
    private void loadImg(){
         hummerIcon =new ImageIcon("img/1.png");
         mouseIcon =new ImageIcon("img/2.png");
         bgIcon =new ImageIcon("img/3.jpg");

    }
    public static void main(String[] args) {
        new MainGame();

    }

    @Override
    public void run() {
        //生成随机数
        Random random=new Random();
        //线程启动后启动这里
        while (true){
            try {
                Thread.sleep(speed);
                for (int i = 0; i < count; i++) {
                    //生成0到8的随机数
                    int index = random.nextInt(mouseLbl.length);
                    //实现地鼠显示一个
                    mouseLbl[index].setVisible(true);
                }

                //出现后停顿2秒
                Thread.sleep(showTime);
                //停顿后所以地鼠消失
                for (int i = 0; i <mouseLbl.length; i++) {
                    mouseLbl[i].setVisible(false);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();

            }

        }
    }
}
