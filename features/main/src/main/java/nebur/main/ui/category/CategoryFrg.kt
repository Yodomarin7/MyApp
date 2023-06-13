package nebur.main.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nebur.main.R
import nebur.main.databinding.FrgCategoryBinding
import nebur.main.helper.ProductTag
import nebur.main.model.Tegs
import nebur.main.ui.start.StartFrgVM

@AndroidEntryPoint
class CategoryFrg(

) : Fragment(R.layout.frg_category) {

    private val vm: CategoryFrgVM by hiltNavGraphViewModels(R.id.categoryFrg)
    private lateinit var b: FrgCategoryBinding
    private lateinit var n: NavController

    private val args: CategoryFrgArgs by navArgs()

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        if(context == null) return

        b = FrgCategoryBinding.bind(v)
        n = findNavController()

        b.txtTitle.text = args.ctgName

        b.btnBack.setOnClickListener { n.popBackStack() }

        b.chipAll.setOnClickListener { vm.getDishes(Tegs.ALL) }
        b.chipSalad.setOnClickListener { vm.getDishes(Tegs.SALAD) }
        b.chipRice.setOnClickListener { vm.getDishes(Tegs.RICE) }
        b.chipFish.setOnClickListener { vm.getDishes(Tegs.FISH) }

        b.error.btnAgain.setOnClickListener {
            b.chipAll.isChecked = true
            vm.getDishes(Tegs.ALL)
        }

        b.rv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = DishesRvAdapter(vm, requireContext())
        b.rv.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.s.collect { s ->
                    updateUi(s)
                    s.items?.let { adapter.submitList(s.items) }
                }
            }
        }
    }

    private fun updateUi(s: CategoryFrgVM.State) {
        setContainer(s.p)
    }

    private fun setContainer(p: CategoryFrgVM.Page) {
        b.rv.visibility = View.GONE
        b.errorP.visibility = View.GONE
        b.waitP.visibility = View.GONE

        when(p) {
            CategoryFrgVM.Page.Dishes -> b.rv.visibility = View.VISIBLE
            CategoryFrgVM.Page.Error -> b.errorP.visibility = View.VISIBLE
            CategoryFrgVM.Page.Wait -> b.waitP.visibility = View.VISIBLE
        }
    }

}

















