package com.themovieguide.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int horizontalSpaceBetweenItems;

    public HorizontalSpaceItemDecoration(int horizontalSpaceBetweenItems) {
        this.horizontalSpaceBetweenItems = horizontalSpaceBetweenItems;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.right = horizontalSpaceBetweenItems;
        outRect.left = horizontalSpaceBetweenItems;
    }
}