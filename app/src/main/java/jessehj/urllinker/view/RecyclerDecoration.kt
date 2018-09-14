package jessehj.urllinker.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerDecoration : RecyclerView.ItemDecoration {

    private var mTopSpace = -1
    private var mBottomSpace = -1
    private var mLeftSpace = -1
    private var mRightSpace = -1

    constructor(bottomSpace: Int) {
        mBottomSpace = bottomSpace
    }

    constructor(topSpace: Int, bottomSpace: Int, leftSpace: Int, rightSpace: Int) {
        mTopSpace = topSpace
        mBottomSpace = bottomSpace
        mLeftSpace = leftSpace
        mRightSpace = rightSpace
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        if (mTopSpace != -1 && position != 0) {
            outRect.top = mTopSpace
        }
        if (mBottomSpace != -1 && position != state.itemCount - 1) {
            outRect.bottom = mBottomSpace
        }

        if (mLeftSpace > 0) outRect.left = mLeftSpace
        if (mRightSpace > 0) outRect.right = mRightSpace
    }
}
