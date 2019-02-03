package com.tupperware.biz.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.dhunter.common.utils.ScreenUtil;

/**
 * Created by dhunter on 2018/3/7.
 */

@TargetApi(17)
@SuppressWarnings("deprecation")
public class SearchEditText extends AppCompatEditText {

    private Drawable searchDrawable;
    private int offset;
    private int searchWidth;
    private String hintString;
    private int w;
    private int flag = 0;

    public SearchEditText(Context context) {
        super(context);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
/*        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 获得焦点
                    setTextAlignment(TextView.TEXT_ALIGNMENT_VIEW_START);
                } else {
                    // 失去焦点
                    setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                }
            }
        });*/
        setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        searchWidth = getMeasuredWidth();
        hintString = getHint().toString();
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.getTextBounds(hintString, 0, hintString.length(), rect);
        w = ScreenUtil.dip2px(getContext(), rect.width());
        offset = searchWidth / 2 - w;
        if (flag == 0) {
            setTextDrawable();
        }
        flag++;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (searchDrawable == null) {
            getDrawable();
        }
        if (length() > 0) {
//            setTextAlignment(TextView.TEXT_ALIGNMENT_VIEW_START);
            setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            setCompoundDrawables(null, null, null, null);
        } else if (length() == 0) {
            setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            setTextDrawable();
        }
    }

    private void getDrawable() {
        Drawable[] compoundDrawables = getCompoundDrawables();
        searchDrawable = compoundDrawables[0];
    }

    private void setTextDrawable() {
        searchDrawable.setBounds(offset, 0, offset + searchDrawable.getIntrinsicWidth(), searchDrawable.getIntrinsicHeight());
        setCompoundDrawables(searchDrawable, null, null, null);
    }

}
