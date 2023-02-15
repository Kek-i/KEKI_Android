package com.codepatissier.keki.util.recycler

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewEmptySupport : RecyclerView {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {}

    private var emptyView: View? = null
    private var fullView: View? = null
    private val emptyObserver: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            val adapter = adapter
            if (adapter != null && emptyView != null) {
                if(fullView == null) {
                    if (adapter.itemCount == 0) {
                        emptyView!!.visibility = VISIBLE
                        this@RecyclerViewEmptySupport.visibility = GONE
                    } else {
                        emptyView!!.visibility = GONE
                        this@RecyclerViewEmptySupport.visibility = VISIBLE
                    }
                } else {
                    if (adapter.itemCount == 0) {
                        emptyView!!.visibility = VISIBLE
                        fullView!!.visibility = GONE
                        this@RecyclerViewEmptySupport.visibility = GONE
                    } else {
                        emptyView!!.visibility = GONE
                        fullView!!.visibility = VISIBLE
                        this@RecyclerViewEmptySupport.visibility = VISIBLE
                    }
                }
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(emptyObserver)
        emptyObserver.onChanged()
    }

    fun setEmptyView(emptyView: View?) {
        this.emptyView = emptyView
    }

    fun setFullView(fullView: View?) {
        this.fullView = fullView
    }
}