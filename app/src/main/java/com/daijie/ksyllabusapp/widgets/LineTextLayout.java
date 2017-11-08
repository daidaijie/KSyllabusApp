package com.daijie.ksyllabusapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.daijie.ksyllabusapp.R;
import com.daijie.ksyllabusapp.utils.DrawableTintUtils;

/**
 * Created by daidaijie on 17-10-9.
 */

public class LineTextLayout extends ConstraintLayout {

    private TextView infoTextView;
    private TextView titleTextView;
    private ImageView iconImageView;

    public LineTextLayout(Context context) {
        this(context, null);
    }

    public LineTextLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_line_text, this, true);
        iconImageView = findViewById(R.id.iconImageView);
        titleTextView = findViewById(R.id.titleTextView);
        infoTextView = findViewById(R.id.infoTextView);

        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.LineTextLayout, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.LineTextLayout_icon:
                    int icon = typedArray.getResourceId(attr, 0);
                    Drawable normalDrawable = DrawableTintUtils.getTintDrawableByColorRes(
                            icon, R.color.colorTextSecondly
                    );
                    iconImageView.setImageDrawable(normalDrawable);
                    break;
                case R.styleable.LineTextLayout_title:
                    titleTextView.setText(typedArray.getString(attr));
                    break;
                case R.styleable.LineTextLayout_info:
                    infoTextView.setText(typedArray.getString(attr));
                    break;
            }
        }

        typedArray.recycle();
    }

    public void setInfo(String info) {
        infoTextView.setText(info);
    }
}
