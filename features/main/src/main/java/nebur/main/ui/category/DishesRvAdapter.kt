package nebur.main.ui.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import nebur.main.R
import nebur.main.databinding.DialogDishBinding
import nebur.main.databinding.RvDishesBinding
import nebur.main.model.BagModel
import nebur.main.model.DishesModel

class DishesRvAdapter(
    private val vm: CategoryFrgVM,
    private val context: Context
): ListAdapter<Triple<DishesModel, DishesModel?, DishesModel?>, DishesRvAdapter.ViewHolder>(
    Comparator()
) {

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val b = RvDishesBinding.bind(v)
        private var dialogB: DialogDishBinding? = null

        private var clickedModel: DishesModel? = null

        init {
            val dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_dish, null, false)
            val d: AlertDialog = MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setView(dialogV).create()
            dialogB = DialogDishBinding.bind(dialogV)

            b.card1.setOnClickListener {
                val p = bindingAdapterPosition
                if(p != -1) {
                    clickedModel = getItem(bindingAdapterPosition).first
                    updateDialogUi(clickedModel!!)
                    d.show()
                }
            }
            b.card2.setOnClickListener {
                val p = bindingAdapterPosition
                if(p != -1) {
                    clickedModel = getItem(bindingAdapterPosition).second
                    clickedModel?.let { updateDialogUi(it) }
                    d.show()
                }
            }
            b.card3.setOnClickListener {
                val p = bindingAdapterPosition
                if(p != -1) {
                    clickedModel = getItem(bindingAdapterPosition).third
                    clickedModel?.let { updateDialogUi(it) }
                    d.show()
                }
            }

            dialogB!!.btnAction.setOnClickListener { _->
                clickedModel?.let { vm.insertBag(BagModel(id = it.id, name = it.name, price = it.price,
                    weight = it.weight, image_url = it.image_url, count = 1)) }
                d.dismiss()
            }
            dialogB!!.btnClose.setOnClickListener { d.dismiss() }
        }

        private fun updateDialogUi(model: DishesModel) {
            dialogB?.let { dialog->
                dialog.name.setText(model.name)
                Picasso.get().load(model.image_url).error(nebur.common.R.color.white).into(dialog.img)
                dialog.desc.setText(model.description)
                dialog.price.text = "${model.price} ₽"
                dialog.mass.text =  " · ${model.weight}г"
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Triple<DishesModel, DishesModel?, DishesModel?>>() {
        override fun areItemsTheSame(oldItem: Triple<DishesModel, DishesModel?, DishesModel?>,
                                     newItem: Triple<DishesModel, DishesModel?, DishesModel?>): Boolean {
            return oldItem.first.id == newItem.first.id && oldItem.second?.id == newItem.second?.id &&
                    oldItem.third?.id == newItem.third?.id
        }

        override fun areContentsTheSame(oldItem: Triple<DishesModel, DishesModel?, DishesModel?>,
                                        newItem: Triple<DishesModel, DishesModel?, DishesModel?>): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_dishes, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(h: ViewHolder, position: Int) {
        val triple = getItem(position)

        Picasso.get().load(triple.first.image_url).error(nebur.common.R.color.white).into(h.b.img1)
        h.b.txt1.setText(triple.first.name)

        if(triple.second != null) {
            h.b.card2.visibility = VISIBLE
            h.b.txt2.visibility = VISIBLE
            Picasso.get().load(triple.second!!.image_url).error(nebur.common.R.color.white).into(h.b.img2)
            h.b.txt2.setText(triple.second!!.name)

            if(triple.third != null) {
                h.b.card3.visibility = VISIBLE
                h.b.txt3.visibility = VISIBLE
                Picasso.get().load(triple.third!!.image_url).error(nebur.common.R.color.white).into(h.b.img3)
                h.b.txt3.setText(triple.third!!.name)
            }
            else {
                h.b.card3.visibility = GONE
                h.b.txt3.visibility = GONE
            }
        }
        else {
            h.b.card2.visibility = GONE
            h.b.txt2.visibility = GONE
            h.b.card3.visibility = GONE
            h.b.txt3.visibility = GONE
        }
    }
}




















