package com.example.icepicksample

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

import java.util.ArrayList

class ViewUtil
private constructor() {

    init {
        throw AssertionError()
    }

    companion object {

        /**
         * 検索Viewの中にある全ての子Viewインスタンスをリストで取得する.(検索Viewも含む.)
         * @param view 検索View
         * @param parentView : 親View
         * @return viewリスト
         */
        private fun getViewsRecursive(view: View?, parentView: ViewGroup?): List<View> {
            val views = ArrayList<View>()
            if (view is LinearLayout
                || view is FrameLayout
                || view is RelativeLayout
                || view is ConstraintLayout) {
                // レイアウトクラスの場合
                val childNum = (view as ViewGroup).childCount
                var count = childNum
                while (0 <= count) {
                    val child = view.getChildAt(count - 1)
                    views.addAll(getViewsRecursive(child, (view as ViewGroup?)!!))
                    count--
                }
            }
            if (view != null) {
                views.add(view)
            }
            return views
        }

        /**
         * 指定した親Viewの中にある全ての[Button]一括　クリックリスナー登録する.
         *
         * @param vg 検索したい親View
         * @param l クリックリスナー
         */
        fun setButtonOnClickListener(vg: View, l: View.OnClickListener?) {
            // viewツリーの取得.
            val viewTree = getViewsRecursive(vg, null)

            // view一括処理.
            for (view in viewTree) {
                if (view is Button) {
                    view.setOnClickListener(l)
                }
            }

            // For API level 24
            // viewツリーの取得.
            //val viewTree = getViewsRecursive(vg, null)
            //viewTree.stream().filter { view -> view is Button }.forEach { view -> view.setOnClickListener(l) }
        }
    }

}
