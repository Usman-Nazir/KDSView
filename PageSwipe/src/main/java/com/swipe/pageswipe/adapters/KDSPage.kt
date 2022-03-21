package com.swipe.pageswipe.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swipe.pageswipe.adapters.utils.CountDownTimer
import com.swipe.pageswipe.adapters.utils.ForwardCounter


class KDSPage {

    var pageUpdateCallback: ((pageNo: Int, steps: Int) -> Unit)? = null
    var width = 0
    var currentPageCount = 1
    var totalPages = 0
    var layoutManager: LinearLayoutManager? = null
    var maxStepsPerScreen = 3
    var kdsAdapterNewAdapter: KdsAdapterNewAdapter? = null
    var orderItems: RecyclerView? = null
    var customViewResource: Int? = null
    var rightButton: View? = null
    var leftButton: View? = null
    var customResourceLayout: ((countDownTimer: CountDownTimer?, forwardCounter: ForwardCounter?, layout: View, item: PageItems?, position: Int) -> Unit)? = null

    init {
    }

    fun setMaxStepsPerScreen(maxStepsPerScreen: Int): KDSPage {
        this.maxStepsPerScreen = maxStepsPerScreen
        return this
    }

    fun setRecyclerView(orderItems: RecyclerView?): KDSPage {
        this.orderItems = orderItems
        return this
    }

    fun setRightButton(rightButton: View?): KDSPage {
        this.rightButton = rightButton
        return this
    }

    fun setCustomViewResource(customViewResource: Int, customResourceLayout: ((countDownTimer: CountDownTimer?, forwardCounter: ForwardCounter?, layout: View, item: PageItems?, position: Int) -> Unit)): KDSPage {
        this.customViewResource = customViewResource
        this.customResourceLayout = customResourceLayout
        return this
    }

    fun setLeftButton(leftButton: View?): KDSPage {
        this.leftButton = leftButton
        return this
    }

    fun loadItems(items: MutableList<PageItems>, context: Context, pageUpdateCallback: ((pageNo: Int, steps: Int) -> Unit)) {

        if (orderItems == null) return
        this.pageUpdateCallback = pageUpdateCallback
        orderItems?.viewTreeObserver?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            @SuppressLint("NewApi")
            override fun onGlobalLayout() {
                val w: Int? = orderItems?.width
                val h: Int? = orderItems?.height
                width = w ?: 0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    orderItems?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                } else {
                    orderItems?.viewTreeObserver?.removeGlobalOnLayoutListener(this)
                }
            }
        })

        kdsAdapterNewAdapter = KdsAdapterNewAdapter(arrayListOf(), context, maxStepsPerScreen, customViewResource, {
            updatePageNumbers()
        }, { counter, forwardCOunter, layout, item, position ->
            this.customResourceLayout?.invoke(counter, forwardCOunter, layout, item, position)
        })

        orderItems?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)//KdsLinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        //layoutManager.spanCount = 3
        orderItems?.layoutManager = layoutManager
        orderItems?.adapter = kdsAdapterNewAdapter

        orderItems?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updatePageNumbers()
            }
        })

        //setPagerText()
        leftButton?.setOnClickListener {
            layoutManager.let {
                if (currentPageCount > 1)
                    --currentPageCount
                val isLastItem = it?.findLastCompletelyVisibleItemPosition()
                val totalItems = kdsAdapterNewAdapter?.itemCount

                if (totalItems != null && isLastItem != null) {
                    if ((isLastItem + 1) == totalItems) {
                        val diff = totalItems % maxStepsPerScreen
                        when {
                            totalItems % maxStepsPerScreen == 0 -> {
                                orderItems?.smoothScrollBy(-width, 0)
                            }
                            (totalItems - diff) % maxStepsPerScreen == 0 -> {
                                orderItems?.smoothScrollBy(-((width / maxStepsPerScreen) * diff), 0)
                            }
                            else -> {
                                orderItems?.smoothScrollBy(-((width / maxStepsPerScreen) * (maxStepsPerScreen - diff)), 0)
                            }
                        }
                    } else
                        orderItems?.smoothScrollBy(-width, 0)
                }
            }
        }
        rightButton?.setOnClickListener {
            if (currentPageCount < totalPages)
                ++currentPageCount
            orderItems?.smoothScrollBy(width, 0)
        }
        totalPages = items.size
        kdsAdapterNewAdapter?.updateData(items)

    }

    fun updatePageNumbers() {
        val totalItems = kdsAdapterNewAdapter?.itemCount
        totalItems?.let {
            layoutManager?.let {
                val posLeft = it.findFirstVisibleItemPosition()
                val isLastItem = it.findLastCompletelyVisibleItemPosition()
                var steps = 0
                for (i in 0 until totalItems step maxStepsPerScreen) {
                    ++steps
                    if (posLeft == i) {
                        currentPageCount = steps
                    } else {
                        val remainder = totalItems % maxStepsPerScreen
                        val divider = totalItems / maxStepsPerScreen
                        val isLess = ((maxStepsPerScreen * divider) + remainder == totalItems)
                        if ((isLastItem + 1) == totalItems) {
                            if (totalItems % maxStepsPerScreen == 0) {
                                currentPageCount = steps
                            } else if (!isLess) {
                                ++currentPageCount
                            } else if (isLess) {
                                currentPageCount = steps
                            }
                        }
                    }
                }
                if (steps != 0) {
                    pageUpdateCallback?.invoke(currentPageCount, steps)
                }
            }
        }

    }
}