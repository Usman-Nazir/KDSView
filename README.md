# KDSView
Simple library for Kitchen Display System

**Installation:**

Add in root gradle:

        allprojects {
	        repositories {
		        maven { url 'https://jitpack.io' }
	        }
        }
        
Add in app gradle:


	dependencies {
                implementation 'com.github.Usman-Nazir:KDSView:v0.0.1'
	}



**Usage:**

        var pageList: MutableList<PageItems> = arrayListOf()
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

You can pass custome view to manipulate also you can use built in forward and backward timer

<img width="1115" alt="image" src="https://user-images.githubusercontent.com/23031447/159347674-85fcd50f-34fe-440b-98fc-4b8ad7d1508e.png">
