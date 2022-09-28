package com.example.jigsawpuzzle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game9 extends AppCompatActivity {
    private static final String TAG = "leo" + Game9.class.getSimpleName();
    private Puzzle puzzle = new Puzzle();
    private MoveButton card;
    //    private List<ImageView> backList = new ArrayList<>();
    private List<Space> spaceList = new ArrayList<>();
    private Map<Integer, Chip> chipMap;
    int [] imgIds = {R.drawable.doraemon911, R.drawable.doraemon912,R.drawable.doraemon913,R.drawable.doraemon921,R.drawable.doraemon922,R.drawable.doraemon923,R.drawable.doraemon931,R.drawable.doraemon932,R.drawable.doraemon933};
    int[] chipIds = {R.id.chip1, R.id.chip2, R.id.chip3, R.id.chip4, R.id.chip5, R.id.chip6, R.id.chip7, R.id.chip8, R.id.chip9};
    int imgId = -1;
    private String puzzleImgName = "doraemon";
    private int chipCnt = 9;
    private Button beginBnt;
    private TextView tv;
    private long time;
//    private float x1, x2, x3, x4, y1, y2, y3, y4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game9);

        // 接受参数
        getMessage();

        // 保存chip的原始位置
//        saveOriginChipPosition();  // have a bug temporarily

        // 初始化组件
        initComponent();

        // 添加响应事件
        addListener();

    }


//    /**
//     * 获取chip的初始位置
//     */
//    @SuppressLint("CutPasteId")
//    private void saveOriginChipPosition(){
//        x1 = findViewById(R.id.chip1).getX();
//        x2 = findViewById(R.id.chip2).getX();
//        x3 = findViewById(R.id.chip3).getX();
//        x4 = findViewById(R.id.chip4).getX();
//
//        y1 = findViewById(R.id.chip1).getY();
//        y2 = findViewById(R.id.chip2).getY();
//        y3 = findViewById(R.id.chip3).getY();
//        y4 = findViewById(R.id.chip4).getY();
//    }

    /**
     * 获取正确拼图数量
     *
     * @return
     */
    private int getBINGOChipCnt() {
        int cnt = 0;
        for (Chip chip : chipMap.values()) {
            if (chip.isRightPosition()) cnt++;
        }
        return cnt;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addListener() {

        // 开始按钮

        beginBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = System.currentTimeMillis();
                for(Chip chip: chipMap.values()){
                    chip.getView().setVisibility(View.VISIBLE);
                }

                // ====================添加时钟========================
//                cb.start();
            }
        });

        // 给chip添加响应事件
        for (Chip chip : chipMap.values()) {
            chip.getView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    Log.d(TAG, "onTouch: " +motionEvent.getAction());

                    Chip curChip = chipMap.get(view.getId());

                    // 表示当前放下卡片
                    if (motionEvent.getAction() == 1) {

                        for (Space space : spaceList) {
                            if (inPosition(view, space.getView()) && !space.isOccupy()) {
                                view.setX(space.getView().getX());
                                view.setY(space.getView().getY());

                                curChip.setCurX(space.getX());
                                curChip.setCurY(space.getY());

                                space.setOccupy(true);
                                Log.d(TAG, "onTouch: chipId=" + curChip.getId() + " chipCurX=" + curChip.getCurX() + " chipCurY=" + curChip.getCurY() + " chipRightX=" + curChip.getRightX() + " chipRightY=" + curChip.getRightY());


                                // 判断是否成功完成拼图
                                isBINGO();


                                break;
                            }
                        }
                    } else if (motionEvent.getAction() == 0) {  // 点击卡片
                        // 判断chip是否在space上
                        if (curChip.isInSpace()) {
//                            Log.d(TAG, "onTouch->"+chip.getCurX()+" "+chip.getCurY());
                            for (Space space : spaceList) {
                                if (space.getX() == curChip.getCurX() && space.getY() == curChip.getCurY()) {
                                    space.setOccupy(false);
                                    break;
                                }
                            }
                            curChip.outOfSpace();
                        }
                        Log.d(TAG, "onTouch->" + "chip is in space:" + curChip.isInSpace() + ", chip x:" + curChip.getCurX() + " " + curChip.getCurY());
                    }
                    return false;


                }
            });

        }

    }

    /**
     * 判断是否成功完成拼图 并完成成功响应
     */
    private boolean isBINGO() {
        Log.d(TAG, "onTouch: BINGOcnt = " + getBINGOChipCnt());




        if (getBINGOChipCnt() == chipCnt) {
//            cb.stop();
//            tv.setText("BINGO");
            // 添加弹窗
            Drawable icon = getResources().getDrawable(R.drawable.ok);

            // 创建弹窗
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(icon)
                    .setTitle("拼图完成")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNegativeButton("重新挑战", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            rePlay();
                        }
                    })
                    .setNeutralButton("选择图片及难度", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Game9.this, SelectionDifficulty.class));

                        }
                    })
                    .setMessage("恭喜，您的成绩是: " + getPlayTime())
                    .create()
                    .show();
            return true;
        }
        return false;
    }

    /**
     * 获取游戏时间
     * @return
     */
    private String getPlayTime() {
        Date data = new Date(System.currentTimeMillis()-time);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSSS");
        // 2.1 将Date类实例对象作为实参传入SimpleDateFormat重载构造器中，转换为自定义参数格式的字符串
        String[] timeList = sdf.format(data).split(":");


        return timeList[0] + "'" + timeList[1] + "''" + timeList[2];
    }

    /**
     * 重新挑战游戏
     */
    private void rePlay() {
        for (Space s : spaceList) {
            s.setOccupy(false);
        }
        // 重新设置chip
        setChip();
    }

    /**
     * 判断chip是否在合适的位置
     *
     * @param view
     * @param spaceView
     * @return
     */
    private boolean inPosition(View view, ImageView spaceView) {

        int len = 100;
        return view.getY() <= spaceView.getY() + len && view.getY() >= spaceView.getY() - len && view.getX() <= spaceView.getX() + len && view.getX() >= spaceView.getX() - len;
//        return false;
    }

    /**
     * 接受上一个界面传递的参数
     */
    private void getMessage() {
        Intent intent = getIntent();
        puzzle.setImageName(intent.getStringExtra("imageName"));
        puzzle.setDiff(intent.getIntExtra("diff", 4));
        puzzle.setImageId(intent.getIntExtra("imageId", 1));
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
//        card = findViewById(R.id.chip1);
        tv = findViewById(R.id.tv);
        beginBnt = findViewById(R.id.beginBnt);

        // 初始化空白
        spaceList.add(new Space(R.id.space1, findViewById(R.id.space1), 1, 1));
        spaceList.add(new Space(R.id.space2, findViewById(R.id.space2), 1, 2));
        spaceList.add(new Space(R.id.space3, findViewById(R.id.space3), 1, 3));
        spaceList.add(new Space(R.id.space4, findViewById(R.id.space4), 2, 1));

        spaceList.add(new Space(R.id.space5, findViewById(R.id.space5), 2, 2));
        spaceList.add(new Space(R.id.space6, findViewById(R.id.space6), 2, 3));
        spaceList.add(new Space(R.id.space7, findViewById(R.id.space7), 3, 1));
        spaceList.add(new Space(R.id.space8, findViewById(R.id.space8), 3, 2));
        spaceList.add(new Space(R.id.space9, findViewById(R.id.space9), 3, 3));

        // 初始化chip
        switch (puzzleImgName) {
            case "doraemon":
                imgIds[0] = R.drawable.doraemon911;
                imgIds[1] = R.drawable.doraemon912;
                imgIds[2] = R.drawable.doraemon913;
                imgIds[3] = R.drawable.doraemon921;
                imgIds[4] = R.drawable.doraemon922;
                imgIds[5] = R.drawable.doraemon923;
                imgIds[6] = R.drawable.doraemon931;
                imgIds[7] = R.drawable.doraemon932;
                imgIds[8] = R.drawable.doraemon933;

                imgId = R.drawable.doraemon;
                setChip();
            default:
                break;
        }


    }

    /**
     * 设置chip的图片以及x,y
     */
    private void setChip() {
//        relocateChipPosition();
        // 设置参考图片
        findViewById(R.id.img).setBackground(getResources().getDrawable(imgId));

        // 打乱chipView顺序
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        for (int i = 0; i < 10; i++) {
            int a = Math.abs(random.nextInt()) % chipCnt;
            int b = Math.abs(random.nextInt()) % chipCnt;
            int temp = chipIds[a];
            chipIds[a] = chipIds[b];
            chipIds[b] = temp;
        }

        int k = 0;
        chipMap = new HashMap<>();
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++, k++) {
//                int index = i*(chipCnt/2)+j;
//                Log.d(TAG, "setChip-> chipid:" + chipIds[index]);
                // 设置chip背景图片
                Log.d(TAG, "setChip: imgId=" + imgIds[k] + " i=" + i + " j=" + j + " k=" + k + " viewId="+chipIds[k]);
                MoveButton view = findViewById(chipIds[k]);

                view.setBackground(getResources().getDrawable(imgIds[k]));

                chipMap.put(chipIds[k], new Chip(i, j, view, chipIds[k]));
            }
        }

        // 隐藏chip
        for(Chip chip : chipMap.values()){
            chip.getView().setVisibility(View.INVISIBLE);
        }


        Log.d(TAG, "setChip: chipMapSize=" + chipMap.size());
    }

//    /**
//     * 重新定位chip的位置
//     */
//    private void relocateChipPosition() {
//        findViewById(R.id.chip1).setX(x1);
//        findViewById(R.id.chip2).setX(x2);
//        findViewById(R.id.chip3).setX(x3);
//        findViewById(R.id.chip4).setX(x4);
//
//        findViewById(R.id.chip1).setY(y1);
//        findViewById(R.id.chip2).setY(y2);
//        findViewById(R.id.chip3).setY(y3);
//        findViewById(R.id.chip4).setY(y4);
//
//    }
}