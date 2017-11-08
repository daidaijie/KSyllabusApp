package com.daijie.ksyllabusapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.daijie.ksyllabusapp.R;
import com.daijie.ksyllabusapp.ext.EditTextExtKt;


/**
 * Created by daidaijie on 2017/3/26.
 */

public class TextInputSpinner extends FrameLayout {

    Spinner mSpinner;

    EditText mEditText;

    TextInputLayout mTextInputLayout;

    String[] mItems;

    public TextInputSpinner(@NonNull Context context) {
        this(context, null);
    }

    public TextInputSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextInputSpinner(@NonNull final Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.text_input_spinner, null, false);
        this.addView(view);

        mSpinner = (Spinner) view.findViewById(R.id.spinner);
        mTextInputLayout = (TextInputLayout) view.findViewById(R.id.textInputLayout);
        mEditText = (EditText) view.findViewById(R.id.editText);

        mEditText.setOnKeyListener(null);
        mSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEditText.requestFocus();
                return false;
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mItems != null && position < mItems.length) {
                    mEditText.setText(mItems[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TextInputSpinner, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.TextInputSpinner_hint:
                    setHint(typedArray.getString(attr));
                    break;
            }
        }
        typedArray.recycle();
    }

    public void setHint(String hint) {
        mTextInputLayout.setHint(hint);
    }

    public void setItems(String[] items) {
        mItems = items;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext()
                , android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    public Spinner getSpinner() {
        return mSpinner;
    }

    public EditText getEditText() {
        return mEditText;
    }

    public void setSelection(int position) {
        mSpinner.setSelection(position);
    }

    public int getSelectedItemPosition() {
        return mSpinner.getSelectedItemPosition();
    }

    public String getCurrentText() {
        return mEditText.getText().toString();
    }
}
