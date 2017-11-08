package com.daijie.ksyllabusapp.adapter

import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.data.Direction
import com.daijie.ksyllabusapp.data.FunctionItem
import com.daijie.ksyllabusapp.utils.DrawableTintUtils
import kotlinx.android.synthetic.main.item_function.view.*

/**
 * Created by daidaijie on 2017/10/18.
 */
class FunctionAdapter
    : BaseQuickAdapter<FunctionItem, FunctionAdapter.ViewHolder>(R.layout.item_function, FunctionItem.functionItems) {

    companion object {
        @JvmStatic
        fun setFunctionToView(item: FunctionItem, cardView: CardView, textView: TextView,
                              @Direction.DirectionInf direction: Int) {
            textView.text = item.name
            val iconDrawable = DrawableTintUtils.getTintDrawableByColorRes(item.icon,
                    R.color.colorPrimary)

            val drawables = arrayOfNulls<Drawable>(4)
            drawables[direction - 1] = iconDrawable
            textView.setCompoundDrawablesWithIntrinsicBounds(
                    drawables[0], drawables[1], drawables[2], drawables[3]
            )

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                val colorStateList = ContextCompat.getColorStateList(
                        App.context, R.color.selected_card_press
                )
                cardView.cardBackgroundColor = colorStateList
                cardView.foreground = null
            }
        }

    }

    override fun convert(helper: ViewHolder, item: FunctionItem) {
        helper.bindFunction(item)
    }

    inner class ViewHolder(val mView: View) : BaseViewHolder(mView) {
        fun bindFunction(functionItem: FunctionItem) {
            setFunctionToView(functionItem, mView.functionCardView, mView.functionTextView, Direction.TOP)
        }
    }
}