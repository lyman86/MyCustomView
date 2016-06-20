package com.ly.luoyan.centerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.luoyan.centerview.R;

import java.util.List;

/**
 * Created by luoyan on 16/5/13.
 */
public class ViewPagerIndecator extends LinearLayout {
    //定义绘制三角形的画笔
    private Paint mPaint;
    //绘制三角形的路径
    private Path mPath;
    //三角形宽
    private int mTraingleWidth;
    //三角形高
    private int mTrangleHeight;
    //三角形的宽所占每个Tab的宽的1/6
    private static final float RADIO_TRIANGLE_WIDTH = 1/6F;
    //初始化三角形的位置
    private int mInitTranslationX;
    //三角形移动的位置
    private int mTranslationX;
    //指示器的形状
    private int shapeType;

    private int mTabVisibleCount;
    private static final int DEFAULT_COUNT_TAB = 3;
    private static final int COLOR_TEXT_NORMAL = 0x77ffffff;
    private static final int COLOR_TEXT_SELECT = Color.WHITE;
    private int indecatorColor= Color.WHITE;
    private static final int SHAPE_TRIANGLE = 0;
    private static final int SHAPE_RECTANGLE = 1;
    private List<String>mTabTitles;
    private OnViewPagerChangeListener mListener;
    private int initSelectPosition = 0;
    private boolean first = true;
    private int tvWidth;
    public interface OnViewPagerChangeListener{
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);


        public void onPageSelected(int position);


        public void onPageScrollStateChanged(int state);
    }
    public void setOnViewPagerChangeListener(OnViewPagerChangeListener listener){
        this.mListener = listener;
    }
    public ViewPagerIndecator(Context context) {
        this(context,null);
    }


    public ViewPagerIndecator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取可见tab的数量
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndecator);
        mTabVisibleCount = ta.getInt(R.styleable.ViewPagerIndecator_visible_tab_count,DEFAULT_COUNT_TAB);
        shapeType = ta.getInt(R.styleable.ViewPagerIndecator_shape,SHAPE_TRIANGLE);
        if (mTabVisibleCount<0){
            mTabVisibleCount = DEFAULT_COUNT_TAB;
        }
        ta.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
        if (getWidth()!=0){
            if (first){
                for (String title:mTabTitles){
                    addView(generateTextView(title,initSelectPosition));
                }
                first = false;
            }

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!changed){
            TextView tv = (TextView) getChildAt(0);
            tvWidth = tv.getMeasuredWidth();
            Log.d("srrr",tvWidth+" ");
        }


    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(indecatorColor);
        mPaint.setStyle(Paint.Style.FILL);
        //三角形的每个边为圆角
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //初始化画笔
        initPaint();
        canvas.save();
        canvas.translate(mInitTranslationX+mTranslationX,getHeight()+5);
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (shapeType==SHAPE_TRIANGLE){
            mTraingleWidth = (int) (w/mTabVisibleCount*RADIO_TRIANGLE_WIDTH);
        }else{
            mTraingleWidth = w/mTabVisibleCount;
        }
        mInitTranslationX = w/mTabVisibleCount/2-mTraingleWidth/2;
        initTriangle();
    }

    /**
     * xml文件加载完成后会回调此方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count==0) return;
        for (int i =0;i<count;i++){
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getWidth()/mTabVisibleCount;
            view.setLayoutParams(lp);
        }
    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {
        int start,end;
        start = 80;
        end = mTraingleWidth - 80;
        mPath = new Path();
        mPath.moveTo(start,-20);

        if (shapeType==SHAPE_TRIANGLE){
            mTrangleHeight = mTraingleWidth/2;
            mPath.lineTo(mTraingleWidth,0);
            mPath.lineTo(mTraingleWidth/2,-mTrangleHeight);
        }else{
            mTrangleHeight = mTraingleWidth/20;
            mPath.lineTo(end,-20);
            mPath.lineTo(end,-mTrangleHeight-16);
            mPath.lineTo(start,-mTrangleHeight-16);
        }

        mPath.close();
    }

    /**
     * 三角形随着手指的移动
     * @param position
     * @param positionOffset
     */
    public void scroll(int position, float positionOffset) {
        int tabWidth = getWidth()/mTabVisibleCount;
        mTranslationX = (int) (tabWidth*(position+positionOffset));
        //当tab移动到最后一个时
        if (position>=mTabVisibleCount-2&&positionOffset>0&&getChildCount()>mTabVisibleCount){
            this.scrollTo((int) ((position-(mTabVisibleCount-2))*tabWidth+(tabWidth*positionOffset)),0);
        }
        invalidate();
    }

    public void setTabItemTitles(List<String>titles,int initSelectPosition){
        if (titles!=null&&titles.size()>0){
            this.removeAllViews();
            mTabTitles = titles;
             this.initSelectPosition = initSelectPosition;
        }

    }

    /**
     * 根据title创建tab
     * @param title
     * @return
     */
    private View generateTextView(String title,int initSelectPosition) {
        TextView textView = new TextView(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lp.width = getWidth()/mTabVisibleCount;
        lp.bottomMargin = 40;
        textView.setText(title);
        textView.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        if (this.initSelectPosition==initSelectPosition){
            textView.setTextColor(COLOR_TEXT_SELECT);
        }else{
            textView.setTextColor(COLOR_TEXT_NORMAL);
        }
        this.initSelectPosition++;
        textView.setLayoutParams(lp);
        return textView;
    }
    public void setIndecatorColor(int color){
        indecatorColor = color;
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public void setViewPageChange(ViewPager viewPager){
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (mListener!=null){
                        mListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
                    }
                    scroll(position,positionOffset);
                }

                @Override
                public void onPageSelected(int position) {
                    if (mListener != null) {
                        mListener.onPageSelected(position);
                    }
                    setSelecttitle(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (mListener!=null){
                        mListener.onPageScrollStateChanged(state);
                    }

                }
            });


    }

    private void setSelecttitle(int position) {
        int count = getChildCount();
        for (int i =0;i<count;i++){
            TextView tv = (TextView) getChildAt(i);
            if (i==position){
                tv.setTextColor(COLOR_TEXT_SELECT);
            }else{
                tv.setTextColor(COLOR_TEXT_NORMAL);
            }
        }

    }
}
