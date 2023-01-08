package grifalion.ru.apptest.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import grifalion.ru.apptest.databinding.ItemNameBinding
import grifalion.ru.apptest.model.NameModel



class NameAdapter(private val showDelete: (Boolean) -> Unit, private val itemLongClicked: (List<NameModel>) -> Unit ):RecyclerView.Adapter<NameAdapter.NameViewHolder>() {
    private var nameList = emptyList<NameModel>()
    private var isEnable = false
    private val itemSelectedList = mutableListOf<Int>()

    class NameViewHolder(val binding: ItemNameBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        return NameViewHolder(
            ItemNameBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val item = nameList[position]
        holder.binding.tvName.text = item.name
        holder.binding.checkBox.apply {
            isVisible = item.isCheckBoxVisible
            isChecked = item.isCheckedBox
        }


        holder.binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val currentItem = nameList[holder.adapterPosition]
            nameList = nameList.map {
                if(currentItem==it){
                    it.copy(isCheckedBox = isChecked)
                } else {
                    it
                }
            }
        }

        holder.itemView.setOnLongClickListener{
            itemLongClicked.invoke(nameList)
            selectItem(holder,item,position)
            return@setOnLongClickListener true
        }

        holder.itemView.setOnClickListener {
            if(itemSelectedList.contains(position)){
                itemSelectedList.removeAt(position)
                if(itemSelectedList.isEmpty()) {
                    showDelete(false)
                    isEnable = false
                }

                } else if(isEnable){
                    selectItem(holder,item,position)

            }
        }
    }

    private fun selectItem(holder: NameAdapter.NameViewHolder, item: NameModel, position: Int) {
        showDelete(true)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addName(names: List<NameModel>){
        nameList = names
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = nameList.size

    fun getItems(): List<NameModel> = nameList

}


