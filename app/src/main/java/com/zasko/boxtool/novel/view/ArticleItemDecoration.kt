package com.zasko.boxtool.novel.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.zasko.boxtool.utils.dp

class ArticleItemDecoration : ItemDecoration() {


    private var bottomOffset = 0

    init {

        bottomOffset = 2.dp
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = bottomOffset
    }

}