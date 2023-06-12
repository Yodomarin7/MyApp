package nebur.bag.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import nebur.bag.R
import nebur.bag.databinding.RvBagsBinding
import nebur.bag.model.BagModel

class BagRvAdapter(
    private val vm: BagFrgVM
): ListAdapter<BagModel, BagRvAdapter.ViewHolder>(Comparator()) {

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val b = RvBagsBinding.bind(v)

        init {
            b.btnMinus.setOnClickListener {
                val p = bindingAdapterPosition
                if(p != -1) {
                    val bagModel = getItem(p)
                    vm.update(bagModel.copy(count = bagModel.count - 1))
                }
            }

            b.btnPlus.setOnClickListener {
                val p = bindingAdapterPosition
                if(p != -1) {
                    val bagModel = getItem(p)
                    vm.update(bagModel.copy(count = bagModel.count + 1))
                }
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<BagModel>() {
        override fun areItemsTheSame(oldItem: BagModel, newItem: BagModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BagModel, newItem: BagModel): Boolean {
            return oldItem.count == newItem.count
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_bags, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(h: ViewHolder, position: Int) {
        val bag = getItem(position)

        Picasso.get().load(bag.image_url).error(nebur.common.R.color.white).into(h.b.img)
        h.b.name.setText(bag.name)
        h.b.price.text = "${bag.price} ₽"
        h.b.mass.text =  " · ${bag.weight}г"
        h.b.count.text = "${bag.count}"
    }
}















