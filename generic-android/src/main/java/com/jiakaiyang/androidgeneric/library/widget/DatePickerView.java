package com.jiakaiyang.androidgeneric.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiakaiyang.androidgeneric.library.R;
import com.jiakaiyang.androidgeneric.library.utils.StringUtils;

/**
 * Created by jiakaiyang on 2015/12/23.
 */
public class DatePickerView extends FrameLayout {
    private OnPickerButtonClickListener onPickerButtonClickListener;

    public DatePickerView(Context context) {
        super(context);
    }

    public DatePickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DatePickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.date_picker, this);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.DatePickerView);
        String title = t.getString(R.styleable.DatePickerView_view_title);
        if(StringUtils.NotEmpty(title)){
            TextView tvTitle = (TextView) findViewById(R.id.date_picker_title);
            tvTitle.setText(title + "：");
        }

        findViewById(R.id.date_picker_btn).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onPickerButtonClickListener.OnPickerButtonClick(DatePickerView.this);
            }
        });
    }

    public void setOnPickerButtonClickListener(OnPickerButtonClickListener onPickerButtonClickListener) {
        this.onPickerButtonClickListener = onPickerButtonClickListener;
    }

    public interface OnPickerButtonClickListener{
        void OnPickerButtonClick(DatePickerView pickerView);
    }

    public void setDateSelectedText(String string){
        ((TextView) findViewById(R.id.date_picker_string)).setText(string);
    }
}
