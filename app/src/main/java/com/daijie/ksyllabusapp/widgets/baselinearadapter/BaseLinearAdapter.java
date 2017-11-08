package com.daijie.ksyllabusapp.widgets.baselinearadapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by daidaijie on 2017/5/30.
 */

public abstract class BaseLinearAdapter<VH extends BaseLinearAdapter.ViewHolder> {

    private LinearLayout mLinearLayout;

    public void attachTo(LinearLayout linearLayout) {
        attachTo(linearLayout, true);
    }

    public void attachTo(LinearLayout linearLayout, boolean isRemote) {
        mLinearLayout = linearLayout;
        BindLinearLayoutManager.addViews(linearLayout, this, isRemote);
    }

    public void notifyDataSetChanged() {
        if (mLinearLayout != null) {
            BindLinearLayoutManager.addViews(mLinearLayout, this, true);
        }
    }

    public void detach() {
        mLinearLayout = null;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int position, int viewType);

    public abstract int getCount();

    public static class ViewHolder {
        public final View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}
