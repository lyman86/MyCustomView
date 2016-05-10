package com.ly.luoyan.mycustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ly.luoyan.mycustomview.R;
import com.ly.luoyan.mycustomview.utils.DensityUtils;


public class CustomSelectItem extends View {
    private Context context;
    /**
     * 定义左边，右边，和右边旁边的类型
     */
    private int leftType;
    private int rightType;
    private int centerType;
    private int rightSideType;
    /**
     * 左边文字的属性
     */
    private String leftText = "heh";
    private int leftTextColor = Color.BLACK;
    private float leftTextSize = 30;
    private float leftTextPaddingLeft;

    /**
     * 左边图片的属性
     */
    private int leftImageScource;
    private float leftImagePaddingLeft;
    private int leftImageWidth;
    private float leftImagePaddingTop;
    /**
     * 右边文字的属性
     */
    private String rightText = "";
    private int rightTextColor;
    private float rightTextSize;
    private float rightTextPaddingRight;
    private float rightTextPaddingTop;
    /**
     * 右边旁边文字的属性
     */
    private String rightSideText = "gg";
    private int rightSideTextColor = Color.BLACK;
    ;
    private float rightSideTextSize = 30;
    private float rightSideTextPaddingRight;
    /**
     * 右边图片的属性
     */
    private int rightImageScource;
    private float rightImagePaddingRight;
    private int rightImageWidth;

    /**
     * 中间文字的属性
     */
    private String centerText = "";
    private int centerTextColor;
    private float centerTextSize;
    private float centerTextPaddingTop;

    /**
     * 中间图片的属性
     */
    private int centerImageScource;
    private float centerImagePaddingRight;
    private int centerImageWidth;
    /**
     * 整个View的宽高
     */
    private int viewWidth;
    private int viewHeight;
    /**
     * 定义画笔
     */
    private Paint paint;
    /**
     * 定义设置文字的矩形
     */
    private Rect bounds;
    private final int LEFT_TYPE_TEXT = 0;
    private final int LEFT_TYPE_IMAGE = 1;
    private final int RIGHT_TYPE_TEXT = 0;
    private final int RIGHT_TYPE_IMAGE = 1;
    private final int CENTER_TYPE_TEXT = 0;
    private final int CENTER_TYPE_IMAGE = 1;
    private final int RIGHT_SIDE_TYPE_TEXT = 0;
    private final int RIGHT_SIDE_TYPE_IMAGE = 1;

    public CustomSelectItem(Context context) {
        this(context, null);
    }

    public CustomSelectItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSelectItem(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.itemView, defStyleAttr, 0);
        //初始化类型
        initType(ta);
        ta.recycle();
    }

    /**
     * 初始化类型
     */
    private void initType(TypedArray ta) {
        leftType = ta.getInt(R.styleable.itemView_leftTypeSelect, -1);
        rightType = ta.getInt(R.styleable.itemView_rightTypeSelect, -1);
        rightSideType = ta.getInt(R.styleable.itemView_rightSideTypeSelect, -1);
        centerType = ta.getInt(R.styleable.itemView_centerTypeSelect, -1);
        initAttribute(ta);
    }

    /**
     * 初始化各个属性
     *
     * @param ta
     */
    private void initAttribute(TypedArray ta) {
        switch (leftType) {
            case LEFT_TYPE_TEXT:
                getLeftTextFromXml(ta);
                break;
            case LEFT_TYPE_IMAGE:
                basePaintInit();
                getLeftImageFromXml(ta);
                break;
        }
        switch (rightType) {
            case RIGHT_TYPE_TEXT:
                getRightTextFromXml(ta);
                break;
            case RIGHT_TYPE_IMAGE:
                basePaintInit();
                getRightImageFromXml(ta);
                break;
        }
        switch (rightSideType) {
            case RIGHT_SIDE_TYPE_TEXT:
                getRightSideTextFromXml(ta);
                break;
            case RIGHT_SIDE_TYPE_IMAGE:
                //TODO
                break;
        }
        switch (centerType) {
            case CENTER_TYPE_TEXT:
                getCenterTextFromXml(ta);
                break;
            case CENTER_TYPE_IMAGE:
                //TODO
                break;
        }
    }

    private void basePaintInit() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    /**
     * 定义文字时候所初始化的画笔
     *
     * @param text
     * @param textColor
     * @param textSize
     */
    private void initPaint(String text, int textColor, float textSize) {
        basePaintInit();
        bounds = new Rect();
        paint.setColor(textColor);
        paint.setTextSize(DensityUtils.sp2px(context, textSize));
        paint.getTextBounds(text, 0, text.length(), bounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (leftType) {
            case LEFT_TYPE_TEXT:
                initPaint(leftText, leftTextColor, leftTextSize);
                drawLeftText(canvas);
                break;
            case LEFT_TYPE_IMAGE:
                drawLeftImage(canvas);
                break;
        }
        switch (rightType) {
            case RIGHT_TYPE_TEXT:
                initPaint(rightText, rightTextColor, rightTextSize);
                drawRightText(canvas);
                break;
            case RIGHT_TYPE_IMAGE:
                drawRightImage(canvas);
                break;
        }
        switch (rightSideType) {
            case RIGHT_SIDE_TYPE_TEXT:
                initPaint(rightSideText, rightSideTextColor, rightSideTextSize);
                drawRightSideText(canvas);
                break;
            case RIGHT_SIDE_TYPE_IMAGE:
                //TODO
                break;
        }
        switch (centerType) {
            case CENTER_TYPE_TEXT:
                initPaint(centerText, centerTextColor, centerTextSize);
                drawCenterText(canvas);
                break;
            case CENTER_TYPE_IMAGE:
                //TODO
                break;
        }
    }


    /**
     * 画右边的图片
     *
     * @param canvas
     */
    private void drawRightImage(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), rightImageScource);
        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        float scaleWidth = viewWidth / imageWidth;
        float scaleHeight = viewHeight / imageHeight;
        float scale = Math.min(scaleWidth, scaleHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(scale / 2, scale / 2);
        Bitmap res = Bitmap.createBitmap(bitmap, 0, 0, imageWidth, imageHeight, matrix, true);
        canvas.drawBitmap(res, viewWidth - res.getWidth() - rightImagePaddingRight, viewHeight / 2 - res.getHeight() / 2, paint);
        rightImageWidth = res.getWidth();
    }

    /**
     * 画右边的文字
     *
     * @param canvas
     */
    private void drawRightText(Canvas canvas) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        float textCenterVerticalBaselineY = viewHeight / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
        canvas.drawText(rightText, viewWidth - bounds.width() - rightTextPaddingRight, textCenterVerticalBaselineY + rightTextPaddingTop, paint);
    }

    /**
     * 画右边旁边的文字
     *
     * @param canvas
     */
    private void drawRightSideText(Canvas canvas) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        float textCenterVerticalBaselineY = viewHeight / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
        canvas.drawText(rightSideText, viewWidth - bounds.width() - rightSideTextPaddingRight - rightImageWidth - rightImagePaddingRight, textCenterVerticalBaselineY, paint);
    }

    /**
     * 画中间的文字
     *
     * @param canvas
     */
    private void drawCenterText(Canvas canvas) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        float textCenterVerticalBaselineY = viewHeight / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
        Log.d("drawCenterText",bounds.width()+"");
        canvas.drawText(centerText, (viewWidth - bounds.width())/2, textCenterVerticalBaselineY + centerTextPaddingTop, paint);
    }

    /**
     * 画左边的文字
     *
     * @param canvas
     */
    private void drawLeftText(Canvas canvas) {
        canvas.drawText(leftText, leftTextPaddingLeft, bounds.height() + (viewHeight - bounds.height()) / 2 - bounds.bottom, paint);
    }

    /**
     * 画右边的图片
     *
     * @param canvas
     */
    private void drawLeftImage(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), leftImageScource);
        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        float scaleWidth = viewWidth / imageWidth;
        float scaleHeight = viewHeight / imageHeight;
        float scale = Math.min(scaleWidth, scaleHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(scale / 2, scale / 2);
        Bitmap res = Bitmap.createBitmap(bitmap, 0, 0, imageWidth, imageHeight, matrix, true);
        canvas.drawBitmap(res, leftImagePaddingLeft, viewHeight / 2 - res.getHeight() / 2+leftImagePaddingTop, paint);
        leftImageWidth = res.getWidth();
    }

    /**
     * 从xml文件中获取左边文字的属性
     *
     * @param ta
     */
    private void getLeftTextFromXml(TypedArray ta) {
        leftText = ta.getString(R.styleable.itemView_leftTextSelect);
        leftTextColor = ta.getColor(R.styleable.itemView_leftTextColorSelect, Color.BLACK);
        leftTextSize = ta.getDimension(R.styleable.itemView_leftTextSizeSelect, 14.0f);
        if (leftTextSize != 14.0f) {
            leftTextSize = DensityUtils.px2sp(context, leftTextSize);
        }
        // 默认为0
        leftTextPaddingLeft = DensityUtils.px2dp(context, ta.getDimension(R.styleable.itemView_leftTextPaddingLeftSelect, 0));
    }
    /**
     * 从xml文件中获取左边图片的属性
     *
     * @param ta
     */
    private void getLeftImageFromXml(TypedArray ta) {
        leftImageScource = ta.getResourceId(R.styleable.itemView_leftImageSourceSelect, R.mipmap.ic_launcher);
        leftImagePaddingLeft = DensityUtils.px2dp(context, ta.getDimension(R.styleable.itemView_leftImagePaddingLeftSelect, 0));
        leftImagePaddingTop = DensityUtils.px2dp(context, ta.getDimension(R.styleable.itemView_leftImagePaddingTopSelect, 0));
    }
    /**
     * 从xml文件中获取右边文字的属性
     *
     * @param ta
     */
    private void getRightTextFromXml(TypedArray ta) {
        rightText = ta.getString(R.styleable.itemView_rightTextSelect);
        rightTextColor = ta.getColor(R.styleable.itemView_rightTextColorSelect, Color.BLACK);
        rightTextSize = ta.getDimension(R.styleable.itemView_rightTextSizeSelect, 14.0f);
        if (rightTextSize != 14.0f) {
            rightTextSize = DensityUtils.px2sp(context, rightTextSize);
        }
        // 默认为0
        rightTextPaddingRight = DensityUtils.px2dp(context,
                ta.getDimension(R.styleable.itemView_rightTextPaddingRightSelect, 0));
        rightTextPaddingTop = DensityUtils.px2dp(context,
                ta.getDimension(R.styleable.itemView_rightTextPaddingTopSelect, 0));
    }

    /**
     * 从xml文件中获取中间文字的属性
     *
     * @param ta
     */
    private void getCenterTextFromXml(TypedArray ta) {
        centerText = ta.getString(R.styleable.itemView_centerTextSelect);
        centerTextColor = ta.getColor(R.styleable.itemView_centerTextColorSelect, Color.BLACK);
        centerTextSize = ta.getDimension(R.styleable.itemView_centerTextSizeSelect, 14.0f);
        if (centerTextSize != 14.0f) {
            centerTextSize = DensityUtils.px2sp(context, centerTextSize);
        }
        // 默认为0
        centerTextPaddingTop = DensityUtils.px2dp(context, ta.getDimension(R.styleable.itemView_centerTextPaddingTopSelect, 0));
    }

    /**
     * 从xml文件中获取右边旁边文字的属性
     *
     * @param ta
     */
    private void getRightSideTextFromXml(TypedArray ta) {
        rightSideText = ta.getString(R.styleable.itemView_rightSideTextSelect);
        rightSideTextColor = ta.getColor(R.styleable.itemView_rightSideTextColorSelect, Color.BLACK);
        rightSideTextSize = ta.getDimension(R.styleable.itemView_rightSideTextSizeSelect, 14.0f);
        if (rightSideTextSize != 14.0f) {
            rightSideTextSize = DensityUtils.px2sp(context, rightSideTextSize);
        }
        // 默认为0
        rightSideTextPaddingRight = DensityUtils.px2dp(context,
                ta.getDimension(R.styleable.itemView_rightSideTextPaddingRightSelect, 0));

    }

    /**
     * 从xml文件中获取右边图片的属性
     *
     * @param ta
     */
    private void getRightImageFromXml(TypedArray ta) {
        rightImageScource = ta.getResourceId(R.styleable.itemView_rightImageSourceSelect, R.mipmap.ic_launcher);
        rightImagePaddingRight = DensityUtils.px2dp(context, ta.getDimension(R.styleable.itemView_rightImagePaddingRightSelect, 0));
    }

    /**
     * 设置右边的文字,前提是xml文件已经定义了该文字属性
     *
     * @param rightText
     */
    public void setRightText(String rightText) {
        this.rightSideText = rightText;
        postInvalidate();
    }

    /**
     * 设置右边旁边的文字，前提是xml文件已经定义了该文字属性
     *
     * @param rightSideText
     */
    public void setRightSideText(String rightSideText) {
        this.rightSideText = rightSideText;
        postInvalidate();
    }


    /**
     * 设置右边的图片，xml文件没有定义该图片的属性
     *
     * @param imageScouce
     */
    public void setRightImageScouce(int imageScouce, int paddingRight) {
        this.rightImageScource = imageScouce;
        rightType = RIGHT_TYPE_IMAGE;
        rightImagePaddingRight = paddingRight;
        postInvalidate();
    }

    /**
     * 设置右边无类型
     */
    public void setRightTypeNo() {
        rightType = -1;
        postInvalidate();
    }

    public void setLeftText(String leftText, int leftTextColor, int leftTextSize, int leftTextPaddingLeft) {
        leftType = LEFT_TYPE_TEXT;
        this.leftText = leftText;
        this.leftTextColor = leftTextColor;
        this.leftTextSize = DensityUtils.sp2px(context, leftTextSize);
        ;
        this.leftTextPaddingLeft = DensityUtils.dp2px(context, leftTextPaddingLeft);
        ;
        postInvalidate();
    }

}
