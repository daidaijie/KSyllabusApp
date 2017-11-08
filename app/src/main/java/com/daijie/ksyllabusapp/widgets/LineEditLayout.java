package com.daijie.ksyllabusapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daijie.ksyllabusapp.R;
import com.daijie.ksyllabusapp.utils.DrawableTintUtils;

/**
 * Created by daidaijie on 17-10-2.
 */

public class LineEditLayout extends LinearLayout {

    private EditText editText;
    private TextView textView;
    private ImageView imageView;
    private Drawable highLightDrawble;
    private Drawable nornameDrawable;
    private OnEditerClickListener onEditerClickListener;

    public LineEditLayout(Context context) {
        this(context, null);
    }

    public LineEditLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineEditLayout(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_line_edit, this, true);
        imageView = findViewById(R.id.iconImageView);
        textView = findViewById(R.id.titleTextView);
        editText = findViewById(R.id.infoEditText);


        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.LineEditLayout, defStyleAttr, 0);

        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.LineEditLayout_icon:
                    int icon = typedArray.getResourceId(attr, 0);
                    highLightDrawble = DrawableTintUtils.getTintDrawableByColorRes(
                            icon, R.color.colorPrimary
                    );
                    nornameDrawable = DrawableTintUtils.getTintDrawableByColorRes(
                            icon, R.color.colorTextSecondly
                    );
                    imageView.setImageDrawable(nornameDrawable);
                    break;
                case R.styleable.LineEditLayout_title:
                    textView.setText(typedArray.getString(attr));
                    break;
                case R.styleable.LineEditLayout_editable:
                    boolean editable = typedArray.getBoolean(attr, true);
                    if (!editable) {
                        editText.setKeyListener(null);
                    }
                    break;
                case R.styleable.LineEditLayout_hint:
                    editText.setHint(typedArray.getString(attr));
                    break;
            }
        }

        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onEditerClickListener!=null){
                    onEditerClickListener.onEditerClick();
                }
            }
        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    if (highLightDrawble != null) {
                        imageView.setImageDrawable(highLightDrawble);
                    }
                    if (onEditerClickListener!=null){
                        onEditerClickListener.onEditerClick();
                    }
                } else {
                    textView.setTextColor(ContextCompat.getColor(context, R.color.colorTextSecondly));
                    if (nornameDrawable != null) {
                        imageView.setImageDrawable(nornameDrawable);
                    }
                }
            }
        });

        typedArray.recycle();
    }

    public void setOnEditerClickListener(OnEditerClickListener onEditerClickListener) {
        this.onEditerClickListener = onEditerClickListener;
    }

    public EditText getEditText() {
        return editText;
    }

    public interface OnEditerClickListener {
        void onEditerClick();
    }
}
