package com.tupperware.biz.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by dhunter on 2018/12/5.
 * 只显示当前年，从1月至本月
 */

public class MonthPickerFragment extends DialogFragment {

    private DatePicker mDatePicker;
    public OnDialogListener mlistener;

    public static MonthPickerFragment newInstance(Date selectDate, Date currentData) {
        Bundle args = new Bundle();
        args.putSerializable(Constant.ARG_DATE, selectDate);
        args.putSerializable(Constant.CURRENT_DATE, currentData);
        MonthPickerFragment fragment = new MonthPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date selectDate = (Date) getArguments().getSerializable(Constant.ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Date currentDate = (Date) getArguments().getSerializable(Constant.CURRENT_DATE);
        Calendar CurrentCalendar = Calendar.getInstance();
        CurrentCalendar.setTime(currentDate);
        int currentYear = CurrentCalendar.get(Calendar.YEAR);
        int currentMonth = CurrentCalendar.get(Calendar.MONTH);
        int currentDay = CurrentCalendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_month, null);

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_month_date_picker);
        mDatePicker.init(year, month, day, null);

//        设置最大最小能选择的时间
        Calendar minDate = Calendar.getInstance();
        minDate.set(currentYear , 0, 1);
        long minDay = minDate.getTimeInMillis();
        mDatePicker.setMinDate(minDay);
        Calendar maxDate = Calendar.getInstance();
        maxDate.set(currentYear, currentMonth, 1, 0 , 0, 0);
        long maxTime = maxDate.getTimeInMillis();
//        long maxTime = System.currentTimeMillis();
        mDatePicker.setMaxDate(maxTime);
        hideDay(mDatePicker);
        setDatePickerDividerColor(mDatePicker);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.month_picker_title)
                .setPositiveButton(R.string.sure,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
//                                int day = mDatePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year, month, 1).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                        })
                .create();

    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            mlistener.onDialogClick(date);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Constant.EXTRA_DATE, date);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }


    public interface OnDialogListener {
        void onDialogClick(Date date);
    }

    public void setOnDialogListener(OnDialogListener dialogListener){
        this.mlistener = dialogListener;
    }

    private void hideDay(DatePicker mDatePicker) {
        try {
            /* 处理android5.0以上的特殊情况 */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                if (daySpinnerId != 0) {
                    View daySpinner = mDatePicker.findViewById(daySpinnerId);
                    if (daySpinner != null) {
                        daySpinner.setVisibility(View.GONE);
                    }
                }
            } else {
                Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
                for (Field datePickerField : datePickerfFields) {
                    if ("mDaySpinner".equals(datePickerField.getName()) || ("mDayPicker").equals(datePickerField.getName())) {
                        datePickerField.setAccessible(true);
                        Object dayPicker = new Object();
                        try {
                            dayPicker = datePickerField.get(mDatePicker);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        ((View) dayPicker).setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置时间选择器的分割线颜色
     *
     * @param datePicker
     */
    private void setDatePickerDividerColor(DatePicker datePicker) {
        // Divider changing:

        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);

        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(Color.parseColor("#ff7000")));//设置分割线颜色
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

}