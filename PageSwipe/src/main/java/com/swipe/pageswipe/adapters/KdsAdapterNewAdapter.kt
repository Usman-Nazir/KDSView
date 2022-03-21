package com.swipe.pageswipe.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swipe.pageswipe.R
import com.swipe.pageswipe.adapters.utils.CountDownTimer
import com.swipe.pageswipe.adapters.utils.ForwardCounter
import com.swipe.pageswipe.adapters.utils.ifOneAddZero
import com.swipe.pageswipe.databinding.ItemKdsBinding
import java.util.*
import java.util.concurrent.TimeUnit

data class PageItems(
    var id: String = "",
    var name: String = "",
    var date: String = "",
    var postingTime: String = "",
    var status: String = "",
    var customObj: Any? = null,
)


class KdsAdapterNewAdapter(var items: MutableList<PageItems?>?,
                           var context: Context,
                           var maxStepsPerScreen: Int,
                           var customViewResource: Int?,
                           var adapterChangesCallBack: () -> Unit,
                           var customeResourceLayout: (countDownTimer:CountDownTimer? ,forwardCounter:ForwardCounter? ,layout:View,item:PageItems? , position:Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    var items: MutableList<PageItems?>? = null
//    var maxStepsPerScreen: Int = 3
//    lateinit var context: Context
    var TAG = "KdsAdptrNwAdapter_test"
//    var customViewResource: Int? = null


//    constructor(items: MutableList<PageItems?>?, context: Context, maxStepsPerScreen: Int, customViewResource: Int?, adapterChangesCallBack: () -> Unit) : this() {
//        this.customViewResource = customViewResource
//        this.maxStepsPerScreen = maxStepsPerScreen
//        this.items = items
//        this.context = context
//        this.adapterChangesCallBack = adapterChangesCallBack
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (customViewResource != null){
            val view = LayoutInflater.from(parent.context).inflate(customViewResource!!, parent, false)
            view.layoutParams = ViewGroup.LayoutParams(
                (parent.width / maxStepsPerScreen.toFloat()).toInt(),
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            return ViewHolderCustomResource(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kds, parent, false)
            view.layoutParams = ViewGroup.LayoutParams(
                (parent.width / maxStepsPerScreen.toFloat()).toInt(),
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            var binding = ItemKdsBinding.bind(view)
            return ViewHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when {
            holder is ViewHolder ->{
                val item = items?.getOrNull(position)
                holder.bind(item)
            }
            holder is ViewHolderCustomResource ->{
                val item = items?.getOrNull(position)
                holder.bind(item)
            }
        }

//        holder.tvOrderNo1.text = context.getString(R.string.order_no_) + " ${item?.orderNumber?.ifNullEmpty()}"
//        holder.tvOrderTime1.text = item?.postingTime
//
//        holder.itemContainer.removeAllViews()
//        item?.kitchenViewItems?.forEachIndexed { index, kitchenItem ->
//            val child = LayoutInflater.from(holder.itemContainer.context)
//                .inflate(R.layout.kds_list_item, holder.itemContainer, false)
//            child.findViewById<TextView>(R.id.number).text = (index + 1).toString()
//            child.findViewById<TextView>(R.id.nameText).text = kitchenItem?.itemName.ifNullEmpty()
//            child.findViewById<TextView>(R.id.quantity).text = kitchenItem?.qty.toString()
//            child.findViewById<TextView>(R.id.itemCode).text = kitchenItem?.itemCode.ifNullEmpty()
//            child.findViewById<TextView>(R.id.noteText).text = kitchenItem?.note.ifNullEmpty()
//            holder.itemContainer.addView(child)
//        }
//
//        holder.tvTableNo.text = context.getString(R.string.table_no) + " " + item?.restaurant_table.ifNullEmpty()
//        holder.tvOrderType.text = context.getString(R.string.order_type_) + " " + item?.order_type.ifNullEmpty()


    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }

    fun updateData(items: List<PageItems?>?) {
        this.items = items?.toMutableList()
    }

    fun addData(items: List<PageItems?>?) {
        items?.let {
            val newItems = it.toMutableList()
            this.items?.addAll(0, newItems)
            notifyDataSetChanged()
        }
    }

    fun removeItems(item: PageItems?, success: () -> Unit) {
        val index = this.items?.indexOf(item)
        index?.let {
            if (it != -1) {
                this.items?.removeAt(it)
                notifyItemRemoved(it)
                success.invoke()
            }
        }
    }

    var recallPosition = -1

    inner class ViewHolder(var binding: ItemKdsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PageItems?) {
            var timeValue = 0L
//        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val tz = TimeZone.getTimeZone("GMT+3")
//        sdf.timeZone = tz
//        //val tz = TimeZone.getTimeZone("GMT+3")
//        //TimeZone.setDefault(tz);
//        //sdf.timeZone = tz
//
//        //Log.i("test" , "display name  : "+TimeZone.getDefault().displayName)
//        val currentDate = Calendar.getInstance(tz)// Date()
//        currentDate.time = getTimeByTimeZone("GMT+3")
//        val currentDateHelper = Calendar.getInstance(tz)// Date()
//        currentDateHelper.time = getTimeByTimeZone("GMT+3")
//        val temp = item?.date + " " + item?.postingTime
//        val datePost: Date = try {
//            sdf.parse(temp)
//        } catch (e: Exception) {
//            Log.i("test", "e $e")
//            currentDateHelper.time = getTimeByTimeZone("GMT+3")
//            currentDateHelper.time
//        }
//        val timeDiffrence = currentDate.timeInMillis - datePost.time
//        val differenceSeconds = TimeUnit.SECONDS.convert(timeDiffrence, TimeUnit.MILLISECONDS)
//        val instance = Calendar.getInstance()
//        instance.timeInMillis = currentDate.timeInMillis
//        instance.timeInMillis = datePost.time

//        var isCookingStatusChangedFromDelay = false
            /*timeValue = if (differenceSeconds.toInt() <= prefManager.newOrderColorSeconds.toInt()) {
                if (item?.status == ActivityKDS.COOKING) {
                    (prefManager.newOrderColorSeconds.toInt() + prefManager.cookingSeconds.toInt()) - differenceSeconds
                } else {
                    prefManager.newOrderColorSeconds.toInt() - differenceSeconds
                }
            } else if (differenceSeconds > prefManager.newOrderColorSeconds.toInt()
                && differenceSeconds < (prefManager.newOrderColorSeconds.toInt() + prefManager.cookingSeconds.toInt())
            ) {
                (prefManager.newOrderColorSeconds.toInt() + prefManager.cookingSeconds.toInt()) - differenceSeconds
            } else if (differenceSeconds > (prefManager.newOrderColorSeconds.toInt() + prefManager.cookingSeconds.toInt())
            *//*&& differenceSeconds < (prefManager.newOrderColorSeconds.toInt() + prefManager.delayColorSeconds.toInt() + prefManager.cookingSeconds.toInt())*//*
        ) {
            //need changings
            if (item?.status == ActivityKDS.COOKING) {
                isCookingStatusChangedFromDelay = true
                0L
            } else {
                //item?.status = ActivityKDS.DELAY
                differenceSeconds - (prefManager.newOrderColorSeconds.toInt() + prefManager.cookingSeconds.toInt())//0L//(prefManager.newOrderColorSeconds.toInt() + prefManager.delayColorSeconds.toInt() + prefManager.cookingSeconds.toInt()) - differenceSeconds
            }
        } else {
            0L
        }
*/
//        when (item?.status) {
//            ActivityKDS.NEW -> {
//                holder.statusColor.setBackgroundColor(Color.parseColor(prefManager.newOrderColor))
//                holder.orderStatus.text = context.getString(R.string.new_)
//                /*
//                * check for new time passed
//                * if new time passed automatic hit for cooking
//                *
//                * */
//                if (differenceSeconds.toInt() <= prefManager.newOrderColorSeconds.toInt()) {
//                    timeValue = prefManager.newOrderColorSeconds.toInt() - differenceSeconds
//                } else
//                    holder.statusColor.performClick()
//            }
//            ActivityKDS.DELAY -> {
//                holder.statusColor.setBackgroundColor(Color.parseColor(prefManager.delayColor))
//                holder.orderStatus.text = context.getString(R.string.delay_)
//                try {
//                    val cookingUpdateTime = sdf.parse(item.status_last_updated)
//                    val timeDiffrence_ = (currentDate.timeInMillis - cookingUpdateTime.time)
//                    val differenceSeconds_ =
//                        TimeUnit.SECONDS.convert(timeDiffrence_, TimeUnit.MILLISECONDS)
//                    timeValue = differenceSeconds_ - prefManager.cookingSeconds.toLong()
//                    /* Log.i(
//                         "test",
//                         " ::::::  " + item.orderNumber + " : " + timeValue + " timeDiffrence_ : " + timeDiffrence_
//                     )*/
//                } catch (e: Exception) {
//                    Log.i("test", " e 1  " + e)
//                }
//            }
//            ActivityKDS.COOKING -> {
//                holder.statusColor.setBackgroundColor(Color.parseColor(prefManager.cookingColor))
//                holder.orderStatus.text = context.getString(R.string.cooking_)
//                try {
//                    val cookingUpdateTime = sdf.parse(item.status_last_updated)
//                    val lessDif = TimeUnit.SECONDS.convert(
//                        (currentDate.timeInMillis - cookingUpdateTime.time),
//                        TimeUnit.MILLISECONDS
//                    )
//                    val timeTemp = prefManager.cookingSeconds.toLong() - lessDif
//                    timeValue = if (timeTemp > 0) timeTemp else { //most probably it is delay
//                        item.status = ActivityKDS.DELAY
//                        Handler(Looper.getMainLooper()).post { notifyItemChanged(position) }
//                        0L
//                    }
//                } catch (e: Exception) {
//                    Log.i("test", "Exception : " + e)
//                }
//            }
//        }

//        holder.currentTimeTime.text = currentDate.time.toString()
//        holder.postTime.text = item?.status_last_updated

            /*
            *  new -> cooking -> delay
            *  new -> delay
            *  delay -> cooking
            *  cooking -> nothing
            *
            * */

            /*  ####### */
            timer?.cancel()
            forwardTimer?.cancel()
            if (item?.status == CookingStatus.COOKING.name || item?.status == CookingStatus.NEW.name) {
                timer = object : CountDownTimer(
                    0,
                    TimeUnit.SECONDS,
                    position
                ) {
                    override fun onFinish(position: Int) {
                        synchronized(position) {
                            Handler(Looper.getMainLooper()).post {
                                items?.let {
                                    val itemm = it.getOrNull(position) ?: return@let
                                    if (itemm.status == CookingStatus.NEW.name) {

                                    } else if (itemm.status == CookingStatus.COOKING.name) {
                                        itemm.status = CookingStatus.DELAY.name
                                        binding.orderStatus.text = context.getString(R.string.delay_)
                                        timer?.cancel()
                                        notifyItemChanged(position)

                                    }
//                                    binding.counter?.text = "${TimeUnit.SECONDS.toHours(tickValue)}:${TimeUnit.SECONDS.toMinutes(tickValue)}:${TimeUnit.SECONDS.toSeconds(timeValue)}"
//                                counter.text = "00:00"
                                }
                            }
                        }
                    }

                    override fun onTick(tickValue: Long) {
                        /*synchronized(counter){*/
//                    counter.text = "${TimeUnit.SECONDS.toHours(tickValue).ifOneAddZero()}:${(TimeUnit.SECONDS.toMinutes(tickValue) % 60).toInt().ifOneAddZero()}:${(TimeUnit.SECONDS.toSeconds(tickValue) % 60).toInt().ifOneAddZero()}"
                        /*}*/

                        //counter.text = tickValue.toString()
                    }
                }
                timer?.start()
            } else if (item?.status == CookingStatus.DELAY.name) {

                forwardTimer = object : ForwardCounter(timeValue, TimeUnit.SECONDS, position) {
                    override fun onTick(ticker: Long,) {
                        synchronized(position) {
                            Handler(Looper.getMainLooper()).post {
                                val tickValue = timeValue + ticker//differenceSeconds + ticker
                                binding?.counter1?.text = "${TimeUnit.SECONDS.toHours(tickValue).ifOneAddZero()}:${(TimeUnit.SECONDS.toMinutes(tickValue) % 60).toInt().ifOneAddZero()}:${(TimeUnit.SECONDS.toSeconds(tickValue) % 60).toInt().ifOneAddZero()}"
                            }
                        }
                    }
                }
                forwardTimer?.start()
            }


            /*  ####### */
        }

        var timer: CountDownTimer? = null
        var forwardTimer: ForwardCounter? = null

        init {
        }

    }

    inner class ViewHolderCustomResource(var binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(item: PageItems?) {
            timer?.cancel()
            forwardTimer?.cancel()
            customeResourceLayout?.invoke(timer , forwardTimer ,binding ,item ,adapterPosition)
        }

        var timer: CountDownTimer? = null
        var forwardTimer: ForwardCounter? = null

        init {
        }

    }
}

enum class CookingStatus {
    COOKING, DELAY, NEW
}