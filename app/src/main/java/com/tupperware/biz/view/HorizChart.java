package com.tupperware.biz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.OverScroller;

import com.android.dhunter.common.utils.ScreenUtil;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.saleenter.SaleReportBean;

import java.util.List;

/**
 * Created by dhunter on 2018/11/7.
 */

public class HorizChart  extends View {
    /**
     * 最大值
     */
    private float maxValue ;
    /**
     * 统计项目
     */
    private List<SaleReportBean.SaleReportModel> barList;

    /**
     * 线的宽度
     */
    private int lineStrokeWidth;
    /**
     * 统计条宽度
     */
    private int barWidth;

    /**
     * 两条统计图之间空间
     */
    private int barSpace;
    /**
     * 滑动的view长度
     */
    private int maxHeight;
    private int defaultHeight;
    private int scrollHeight;

    /**
     * 各画笔
     */
    private Paint barPaint, linePaint, textPaint, scoreTextPaint;

    /**
     * 矩形区域
     */
    private Rect barRect, topRect;

    private Path textPath;

    private int itemNameWidth;

    private int scoreTextHeight;
    private Context mContext;

    /**
     * 项目名和条形图之间的距离
     */
    private int betweenMargin;

    private OverScroller mScroller; //用于辅助View拖动或滑行
    //处理触摸的速率
    private VelocityTracker mVelocityTracker = null ; //速度追踪器
    public static final int  SNAP_VELOCITY = 600 ;  //最小的滑动速率,小于这个速率不滑行


    public HorizChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        this.mContext = context;
    }

    /**
     * 初始化设置
     */
    private void init(Context context) {
        barPaint = new Paint();
        barPaint.setColor(getResources().getColor(R.color.color_f7f2ee));

        linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.lineColor));
        lineStrokeWidth = ScreenUtil.dip2px(context, 0.5f);
        linePaint.setStrokeWidth(lineStrokeWidth);

        textPaint = new Paint();
        textPaint.setColor(getResources().getColor(R.color.black));
        textPaint.setTextSize(ScreenUtil.dip2px(context, 13));
        textPaint.setAntiAlias(true);

        scoreTextPaint = new Paint();
        scoreTextPaint.setTextSize(ScreenUtil.dip2px(context, 13));
        scoreTextPaint.setColor(getResources().getColor(R.color.white));

        barRect = new Rect(0, 0, 0, 0);
        textPath = new Path();

        barWidth = ScreenUtil.dip2px(context, 30);
        barSpace = ScreenUtil.dip2px(context, 10);
        scoreTextHeight = ScreenUtil.dip2px(context, 13);
        itemNameWidth = ScreenUtil.dip2px(context, 120);
        defaultHeight = ScreenUtil.getHeight(context) - ScreenUtil.dip2px(context, 235); //减去标题和下Tab的高度
        scrollHeight = 0;
        betweenMargin = scoreTextHeight / 2;
        mScroller=new OverScroller(context);
        mVelocityTracker=VelocityTracker.obtain();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float lineViewWidth = (float) ((this.getWidth() - itemNameWidth) * 0.8);//线的宽度占总宽度的0.8，剩余的部分显示分数
        if (isInEditMode()) {
            return;
        }
        if(barList == null) {
            return;
        }
        for (int i = 0; i < barList.size(); i++) {
            barRect.left = itemNameWidth;
            barRect.top = barSpace * (i + 2) + barWidth * i;
            barRect.right = (int) (lineViewWidth * (barList.get(i).getSaleNum() / maxValue)) + itemNameWidth;
            barRect.bottom = barRect.top + barWidth;
//            if ((barList.get(i).getSaleNum() / maxValue) >= 0.6) {
//                barPaint.setColor(getResources().getColor(R.color.score_blue));
//            } else {
            barPaint.setColor(getResources().getColor(R.color.color_ff7000));
//            }

            canvas.drawRect(barRect, barPaint);
            /**
             * 减3是因为获取文字高度为39，比scoreTextHeight大5-6，说明有一定占空，所以上移一半
             */
            canvas.drawText(barList.get(i).getSaleNum() + "", barRect.right + 10, barRect.bottom - ((barWidth - scoreTextHeight) /2 ) - 3, textPaint);
            String name = barList.get(i).getProductName();
            if(name.length() <= 8) {
                canvas.drawText(name, itemNameWidth - betweenMargin - textPaint.measureText(barList.get(i).getProductName()), barRect.bottom - ((barWidth - scoreTextHeight) /2 ) - 3, textPaint);
            } else if (name.length() < 16) {
                String before = name.substring(0,8);
                String after = name.substring(8, name.length());
                canvas.drawText(before, itemNameWidth - betweenMargin - textPaint.measureText(before), barRect.bottom - (barWidth / 2 + 10), textPaint);
                canvas.drawText(after, itemNameWidth - betweenMargin - textPaint.measureText(after), barRect.bottom - (barWidth / 2 - scoreTextHeight) -3, textPaint);
            } else {
                String before = name.substring(0,8);
                String after = name.substring(8, 15) + "...";
                canvas.drawText(before, itemNameWidth - betweenMargin - textPaint.measureText(before), barRect.bottom - (barWidth/2 + 10), textPaint);
                canvas.drawText(after, itemNameWidth - betweenMargin - textPaint.measureText(after), barRect.bottom - (barWidth / 2 - scoreTextHeight) -3, textPaint);
            }


//            TextPaint tp = new TextPaint();
//            tp.setColor(Color.BLACK);
//            tp.setStyle(Paint.Style.FILL);
//            tp.setTextSize(50);
//
//            StaticLayout myStaticLayout = new StaticLayout(barList.get(i).getProductName(), tp, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
//            myStaticLayout.draw(canvas);
//            canvas.drawText(String.valueOf(scoreAdd * (i + 1)), itemNameWidth + scoreWidth * (i + 1) - textPaint.measureText(String.valueOf(scoreAdd * (i + 1))) / 2, barSpace, textPaint);
        }
//        canvas.drawText(String.valueOf(maxValue), itemNameWidth + lineViewWidth - textPaint.measureText(String.valueOf(maxValue)) / 2, barSpace, textPaint);
//        canvas.drawText("0(分)", itemNameWidth - betweenMargin - textPaint.measureText("0(分)"), barSpace, textPaint);
//        canvas.drawLine(itemNameWidth, 0, itemNameWidth, this.getHeight(), linePaint);
    }


    /**
     * 设置统计项目列表
     *
     * @param barList
     */
    public void setBarList(List<SaleReportBean.SaleReportModel> barList) {
        this.barList = barList;
        if (barList == null) {
            throw new RuntimeException("BarChartView.setItems(): the param items cannot be null.");
        }
        if (barList.size() == 0) {
            return;
        }
        maxValue = barList.get(0).getSaleNum();
        for (SaleReportBean.SaleReportModel info : barList) {
            if (info.getSaleNum() > maxValue) {
                maxValue = info.getSaleNum();
            }
        }
        maxHeight = (barWidth + barSpace) * barList.size() + ScreenUtil.dip2px(mContext, 20);
        if(maxHeight < defaultHeight) {
            scrollHeight = 0;
        } else {
            scrollHeight = maxHeight - defaultHeight;
        }
        invalidate();

    }

    //根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {

        }
        Log.i("这个控件的宽度----------", "specMode=" + specMode + " specSize=" + specSize);

        return specSize;
    }

    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {

        }
        Log.i("这个控件的高度----------", "specMode:" + specMode + " specSize:" + specSize);

        return specSize;
    }

    /**
     * @return 返回指定的文字高度
     */
    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();

        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }

    /**
     * 添加滑动
     *
     */
    /**
     * 调用Scroller的startScroll后，Scroller会根据偏移量是时间计算当前的X坐标和Y坐标，执行invalidte会让View执行draw()方法，从而调用computeScroll()方法
     * @param dx
     * @param dy
     */
    public void smoothScrollBy(int dx,int dy){
        if(mScroller.getFinalY() +dy > scrollHeight) {
            dy = scrollHeight - mScroller.getFinalY();
        } else if(mScroller.getFinalY() +dy < 0) {
            dy = -1*mScroller.getFinalY();
        }
        mScroller.startScroll(0,mScroller.getFinalY(),0,dy,500);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    /**
     * 根据瞬时速度，让画布滑行
     * @param velocityX
     * @param velocityY
     */
    public void fling( int velocityX, int velocityY){
        //最后两个是参数是允许的超过边界值的距离
        mScroller.fling(mScroller.getFinalX(),mScroller.getFinalY(),0,velocityY,0,0,0,scrollHeight,0,100);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }


    int lastY;
    int currentY;
    int distanceY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(mScroller != null){
                    if(!mScroller.isFinished()){
                        mScroller.abortAnimation();
                    }
                }
//                lastX= (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //计算出两次动作间的滑动距离
//                currentX = (int) event.getX();
//                distanceX = currentX-lastX;
//                distanceX= distanceX*-1;
//                lastX = currentX;
//                smoothScrollBy(distanceX,0);
                currentY = (int) event.getY();
                distanceY = currentY-lastY;
                distanceY= distanceY*-1;
                lastY = currentY;
                smoothScrollBy(0,distanceY);
                break;
            case MotionEvent.ACTION_UP:
                //根据触摸位置计算每像素的移动速率。
                //A value of 1 provides pixels per millisecond, 1000 provides pixels per second, etc.
                mVelocityTracker.computeCurrentVelocity(1000);
                //计算速率
                int velocityX = (int) mVelocityTracker.getXVelocity() ;
                int velocityY = (int)mVelocityTracker.getYVelocity()*(-1);

                //计算出两次动作间的滑动距离
//                currentX = (int) event.getX();
//                distanceX = currentX-lastX;
//                distanceX= distanceX*-1;
//                lastX = currentX;
                currentY = (int) event.getY();
                distanceY = currentY-lastY;
                distanceY= distanceY*-1;
                lastY = currentY;
                //如果速率大于最小速率要求，执行滑行，否则拖动到位置
//                if(Math.abs(velocityX)>SNAP_VELOCITY){
//                    if(!mScroller.isFinished()){
//                        mScroller.abortAnimation();
//                    }
//                    fling(velocityX,velocityY);
//                }else{
//                    smoothScrollBy(distanceX,0);
//                }
                if(Math.abs(velocityY)>SNAP_VELOCITY){
                    if(!mScroller.isFinished()){
                        mScroller.abortAnimation();
                    }
                    fling(velocityX,velocityY);
                }else{
                    smoothScrollBy(0,distanceY);
                }
//
                break;
        }
        return true;
    }
}