package com.daijie.ksyllabusapp.widgets.baselinearadapter;

import android.widget.LinearLayout;

/**
 * Created by liyujie on 2017/3/1.
 */

public class BindLinearLayoutManager {

    static void addViews(final LinearLayout linearLayout, final BaseLinearAdapter baseLinearAdapter, boolean isRemoteBefore) {
        if (linearLayout == null || baseLinearAdapter == null) {
            return;
        }

        if (isRemoteBefore && linearLayout.getChildCount() > 0) {
            linearLayout.removeAllViews();
        }

        final int count = baseLinearAdapter.getCount();

        if (count == 0) {
            return;
        }

        addChildView(linearLayout, baseLinearAdapter, count);
    }

    private static void addChildView(LinearLayout linearLayout, BaseLinearAdapter baseLinearAdapter,
                                     int count) {

        for (int i = 0; i < count; i++) {
            int viewType = baseLinearAdapter.getItemViewType(i);

            BaseLinearAdapter.ViewHolder viewHolder =
                    baseLinearAdapter.onCreateViewHolder(linearLayout, viewType);
            baseLinearAdapter.onBindViewHolder(viewHolder, i, viewType);

            linearLayout.addView(viewHolder.itemView);

        }
    }

}
