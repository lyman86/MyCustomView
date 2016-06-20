package com.ly.luoyan.centerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.luoyan.centerview.R;
import com.ly.luoyan.centerview.utils.DensityUtils;

/**
 * Created by luoyan on 16/6/5.
 */
public class CenterView extends LinearLayout {
    private Canvas mCanvas;
    private Paint mPaint;
    private RectF rect;
    private int bottomBarHeight;
    private ImageView imageView;
    private TextView textView;
    public CenterView(Context context) {
        super(context);
    }

    public CenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        bottomBarHeight = DensityUtils.dp2px(getContext(),65);
        initPaint();
        imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.zhibo);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,0);
        params.topMargin = 10;
        params.weight = 2;
        imageView.setLayoutParams(params);
        addView(imageView);


        textView = new TextView(getContext());
        LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT,0);
        params2.weight = 1;
        textView.setLayoutParams(params2);
        textView.setText("直播");
        textView.setGravity(Gravity.CENTER);

        addView(textView);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.gray));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.d("init123",textView.getHeight()+"  "+imageView.getHeight());
        int left= (getWidth()-imageView.getWidth())/2;

        Log.d("init123",getWidth()+"  "+imageView.getWidth());
//        Log.d("init123",left+"  "+imageView.getWidth());
        rect = new RectF(left,0,left+imageView.getWidth(),getHeight()-textView.getHeight());
        canvas.drawArc(rect,180,180,true, mPaint);
//        canvas.drawArc();
    }


}
