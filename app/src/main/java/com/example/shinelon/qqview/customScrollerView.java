package com.example.shinelon.qqview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Shinelon on 2017/7/27.
 */

public class customScrollerView extends HorizontalScrollView {

    private int mPadding;
    private LinearLayout mLayout;
    private RelativeLayout mMenu;
    private RelativeLayout mContent;
    private boolean once = false;
    private int mMenuWidth;
    private int mScreen;

    public customScrollerView(Context context){
        this(context,null);
    }
    public customScrollerView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public customScrollerView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,R.styleable.customScrollerView,defStyleAttr,0);
        for(int i=0;i<array.getIndexCount();i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.customScrollerView_rightPadding:
                     mPadding = array.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrrics);
        mScreen = outMetrrics.widthPixels;
    }

    @Override
    public void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        if(!once){
            mLayout = (LinearLayout) getChildAt(0);
            mMenu = (RelativeLayout) mLayout.getChildAt(0);
            mContent = (RelativeLayout) mLayout.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreen -mPadding;
            mContent.getLayoutParams().width = mScreen;
        }
        once = true;

        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
    @Override
    public void onLayout(boolean changed,int l,int t,int r,int b){
        super.onLayout(changed,l,t,r,b);
        if(changed){
            this.scrollTo(mMenuWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                if(getScrollX() >= mMenuWidth/2){
                    /**
                     * 此处居然需要post才生效
                     */
                    this.post(new Runnable() {
                        @Override
                        public void run() {
                            smoothScrollTo(mMenuWidth,0);
                        }
                    });
                }else{
                    this.post(new Runnable() {
                        @Override
                        public void run() {
                            smoothScrollTo(0,0);
                        }
                    });
                }
                Log.d("触摸事件","MOVE_UP"+getScrollX());
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
     public void onScrollChanged(int l,int t,int oldl,int oldt){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mMenu,"translationX",0,0.7f*l);
        animator.setDuration(0);
        animator.start();

      /**  float scale = l/mMenuWidth;  //1-0
        float leftScale = 1.0f - 0.3f*scale; //0.7-1.0
        float rightScale = 0.7f + 0.3f*scale;//1.0-0.7
        float alphaScale = 1.0f - 0.3f*scale;//0.7-1.0
        ObjectAnimator leftX = ObjectAnimator.ofFloat(mMenu,"scaleX",0,leftScale);
        leftX.setDuration(0);
        leftX.start();
        ObjectAnimator leftY = ObjectAnimator.ofFloat(mMenu,"scaleY",0,leftScale);
        leftY.setDuration(0);
        leftY.start();

        ObjectAnimator rightX = ObjectAnimator.ofFloat(mContent,"scaleX",0,rightScale);
        rightX.setDuration(0);
        rightX.start();
        ObjectAnimator rightY = ObjectAnimator.ofFloat(mContent,"scaleY",0,rightScale);
        rightY.setDuration(0);
        rightY.start();

        ObjectAnimator alpha = ObjectAnimator.ofFloat(mMenu,"alpha",0.7f,alphaScale);
        alpha.setDuration(0);
        alpha.start();
       **/
    }
}
