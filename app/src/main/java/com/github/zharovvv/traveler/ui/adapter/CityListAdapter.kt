package com.github.zharovvv.traveler.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.zharovvv.traveler.R
import com.github.zharovvv.traveler.ui.model.Widget

class CityListAdapter(
    private val onClick: (Widget, View) -> Unit
) : ListAdapter<Widget, CityListAdapter.CityItemHolder>(CITY_WIDGET_ITEM_DIFF) {

    companion object {
        private val CITY_WIDGET_ITEM_DIFF = object : DiffUtil.ItemCallback<Widget>() {
            override fun areItemsTheSame(oldItem: Widget, newItem: Widget): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Widget, newItem: Widget): Boolean {
                return oldItem == newItem
            }
        }
    }

    class CityItemHolder(
        itemView: View,
        private val onClick: (Widget, View) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.city_image_view)
        private val tvTitle: TextView = itemView.findViewById(R.id.city_title_text_view)
        private val tvDescription: TextView =
            itemView.findViewById(R.id.city_short_description_text_view)
        private var currentCityWidget: Widget? = null

        init {
            itemView.setOnClickListener {
                currentCityWidget?.let { onClick(it, imageView) }
            }
        }

        fun bind(cityWidget: Widget) {
            currentCityWidget = cityWidget
            tvTitle.text = cityWidget.title
            cityWidget.description?.let { tvDescription.text = it }
            cityWidget.imageUrl?.let {
                Glide.with(imageView)
                    .load(it)
                    .into(imageView)
            }
            ViewCompat.setTransitionName(imageView, cityWidget.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemHolder {
        Log.i("recycler_view", "CityListAdapter#onCreateViewHolder")
        return CityItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_city, parent, false),
            onClick
        )
    }

    override fun onBindViewHolder(itemHolder: CityItemHolder, position: Int) {
        Log.i("recycler_view", "CityListAdapter#onBindViewHolder; position: $position")
        val cityWidget: Widget = getItem(position)
        itemHolder.bind(cityWidget)
    }

    fun submitCityWidgets(cityWidgets: List<Widget>) {
        //Проблема при пересоздании экрана: currentList не соответствует реальному списку.
        //val copiedCurrentList = currentList.toMutableList() <- возвращает список, для которого уже был посчитан diffResult.
        //Т.е. при частом вызове addNewCityWidgets - currentList возвращет первоначальный список.
        //copiedCurrentList.addAll(newCityWidgets)
        //submitList(copiedCurrentList)

        /*
         * Передаем копию списка, так как в методе AsyncListDiffer#submitList
         * переданный список сохраняется в поле mList, а затем при повторном вызове проверяется
         * ссылочное равенство mList == newList и если условие выполняется - список не обновляется.
         * UPD: Копия создается в презентере.
         */
        submitList(cityWidgets)
    }
}