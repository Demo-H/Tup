package com.tupperware.biz.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tupperware.biz.R;

/**
 * Created by dhunter on 2018/2/8.
 */

public class LoadingDialogFragment extends DialogFragment {
    private String title;

    private View parent;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static LoadingDialogFragment newInstance(String title) {
        LoadingDialogFragment f = new LoadingDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
        int style = android.support.v4.app.DialogFragment.STYLE_NO_TITLE;
//        int theme = android.R.style.Theme_Holo_Light_Dialog;
        int theme = android.R.style.Theme_Holo_Dialog;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        if(parent == null){
            parent = inflater.inflate(R.layout.fragment_loading_dialog, container, false);
        }
        if(parent != null){
            ViewGroup parentContainer = (ViewGroup) parent.getParent();
            if(parentContainer != null) {
                parentContainer.removeView(parent);
            }
        }
        TextView titleTextView = (TextView) parent.findViewById(R.id.dialog_title);
        titleTextView.setText(title);
        return parent;
    }

}
