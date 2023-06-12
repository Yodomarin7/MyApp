package nebur.main.ui.start

import android.view.Gravity.RIGHT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import nebur.main.R
import nebur.main.databinding.RvCategoriesBinding
import nebur.main.model.CategoriesModel

class CategoriesRvAdapter(
    private val n: NavController
): ListAdapter<CategoriesModel, CategoriesRvAdapter.ViewHolder>(Comparator()) {

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val b = RvCategoriesBinding.bind(v)

        init {
            b.card.setOnClickListener {
                val p = bindingAdapterPosition
                if(p != -1) {
                    val action = StartFrgDirections.actionStartFrgToCategoryFrg(getItem(p).name)
                    n.navigate(action)
                }
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<CategoriesModel>() {
        override fun areItemsTheSame(oldItem: CategoriesModel, newItem: CategoriesModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoriesModel, newItem: CategoriesModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_categories, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(h: ViewHolder, position: Int) {
        val model = getItem(position)

        h.b.txt.text = model.name
        Picasso.get().load(model.image_url).error(nebur.common.R.color.white).fit().centerCrop(RIGHT).into(h.b.img)
    }
}























