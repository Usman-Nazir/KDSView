package com.swipe.view_pager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.swipe.pageswipe.adapters.KDSPage
import com.swipe.pageswipe.adapters.PageItems
import com.swipe.view_pager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        inits()
    }

    private fun inits() {
        var pageList: MutableList<PageItems> = arrayListOf()
        pageList.add(PageItems("", "", "", "", ""))
        pageList.add(PageItems("", "", "", "", ""))
        pageList.add(PageItems("", "", "", "", ""))
        pageList.add(PageItems("", "", "", "", ""))
        pageList.add(PageItems("", "", "", "", ""))
        pageList.add(PageItems("", "", "", "", ""))
        KDSPage()
            .setMaxStepsPerScreen(4)
            .setLeftButton(binding?.ivLeft)
            .setRightButton(binding?.ivRight)
            .setRecyclerView(binding?.orderItems)
            .setCustomViewResource(R.layout.item_kds) { counter, forwardCounter, layout, item, position ->

            }
            .loadItems(pageList, this) { currentPageCount, Steps ->
                binding?.tvPagesCount?.text = "Page" + currentPageCount + " of " + Steps
            }
    }
}