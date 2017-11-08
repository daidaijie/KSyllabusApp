package com.daijie.ksyllabusapp.widgets.basegridadapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

/**
 * Created by liyujie on 2017/3/1.
 */

public abstract class BaseGridAdapter<VH extends BaseGridAdapter.ViewHolder> {

    private GridLayout mGridLayout;

    public void attachTo(GridLayout gridLayout, boolean isRemote) {
        mGridLayout = gridLayout;
        BindGridLayoutManager.addViews(mGridLayout, this, isRemote);
    }

    public void attachTo(GridLayout gridLayout) {
        attachTo(gridLayout, true);
    }

    public void notifyDataSetChanged() {
        if (mGridLayout != null) {
            BindGridLayoutManager.addViews(mGridLayout, this, true);
        }
    }

    public void detach() {
        mGridLayout = null;
    }

    public int getItemViewType(int column, int row) {
        return 0;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int column, int row, int viewType);

    public abstract boolean isGridExist(int column, int row);

    public abstract int getColumnCount();

    public abstract int getRawCount();

    public int getRawSpan(int column, int row) {
        return 1;
    }

    public int getColumnSpan(int column, int row) {
        return 1;
    }

    public int getGridHeight() {
        return 0;
    }

    public int getGridWidth() {
        return 0;
    }

    public static class ViewHolder {

        public final View itemView;

        private int rowCount;
        private int columnCount;

        public int getRowCount() {
            return rowCount;
        }

        public void setRowCount(int rowCount) {
            this.rowCount = rowCount;
        }

        public int getColumnCount() {
            return columnCount;
        }

        public void setColumnCount(int columnCount) {
            this.columnCount = columnCount;
        }

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}

