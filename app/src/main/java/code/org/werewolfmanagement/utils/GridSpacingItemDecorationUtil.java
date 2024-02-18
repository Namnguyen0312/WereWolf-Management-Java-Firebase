package code.org.werewolfmanagement.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecorationUtil extends RecyclerView.ItemDecoration {
    private int spacing;

    public GridSpacingItemDecorationUtil(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        int column = position % spanCount;

        if (spanCount == 2) {
            if (column == 0) {
                outRect.right = spacing / 2;
            } else {
                outRect.left = spacing / 2;
            }
        }
        outRect.top = spacing;
    }
}
