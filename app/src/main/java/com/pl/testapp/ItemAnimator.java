package com.pl.testapp;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAnimator extends BaseItemAnimator {

    @Override
    void animateMoveImpl(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        super.animateMoveImpl(holder, fromX, fromY, toX, toY);
        holder.itemView.animate().rotationY(90)
                .setDuration(500)
                .setListener(new DefaultRemoveAnimatorListener(holder))
                .setStartDelay(getRemoveDelay(holder))
                .start();
    }

    @Override
    void animateAddImpl(RecyclerView.ViewHolder holder) {
        super.animateAddImpl(holder);
        holder.itemView.animate().rotationY(0)
                .setDuration(500)
                .setListener(new DefaultRemoveAnimatorListener(holder))
                .setStartDelay(getAddDuration())
                .start();
    }
}
