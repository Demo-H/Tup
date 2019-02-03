package com.android.dhunter.common.MpAndroidChart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by dhunter on 2018/11/6.
 */

public class CustomXAxisRenderer extends XAxisRenderer {
    public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        float labelHeight = mXAxis.getTextSize();
        float labelInterval = 25f;
        String line[] = formattedLabel.split("\n");

        Paint mFirstLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirstLinePaint.setColor(Color.BLACK);
        mFirstLinePaint.setTextAlign(Paint.Align.CENTER);
        mFirstLinePaint.setTextSize(Utils.convertDpToPixel(15f));
        mFirstLinePaint.setTypeface(mXAxis.getTypeface());

        Paint mSecondLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondLinePaint.setColor(0xFF9b9b9b);
        mSecondLinePaint.setTextAlign(Paint.Align.CENTER);
        mSecondLinePaint.setTextSize(Utils.convertDpToPixel(10f));
        mSecondLinePaint.setTypeface(mXAxis.getTypeface());

        if(line.length > 1) {
            Utils.drawXAxisValue(c, line[0], x, y, mFirstLinePaint, anchor, angleDegrees);
            Utils.drawXAxisValue(c, line[1], x + mAxisLabelPaint.getTextSize(), y + mAxisLabelPaint.getTextSize(), mSecondLinePaint, anchor, angleDegrees);
        } else {
            Utils.drawXAxisValue(c, formattedLabel, x, y, mAxisLabelPaint, anchor, angleDegrees);
        }
    }
}
