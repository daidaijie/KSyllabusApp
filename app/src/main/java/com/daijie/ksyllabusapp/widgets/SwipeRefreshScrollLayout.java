package com.daijie.ksyllabusapp.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by daidaijie on 2016/7/17.
 * 避免ScrollView和SwipeRefreshLayout滑动冲突
 */
public class SwipeRefreshScrollLayout extends SwipeRefreshLayout {

    private View mScrollView;

    public void setScrollView(View scrollView) {
        mScrollView = scrollView;
    }

    public SwipeRefreshScrollLayout(Context context) {
        super(context);
    }

    public SwipeRefreshScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    @Override
    public boolean canChildScrollUp() {
        if (mScrollView != null)
            return mScrollView.canScrollVertically(-1);
        return super.canChildScrollUp();
    }
}
