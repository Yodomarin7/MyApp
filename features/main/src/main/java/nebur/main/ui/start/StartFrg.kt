package nebur.main.ui.start

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nebur.main.R
import nebur.main.databinding.FrgStartBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class StartFrg(

) : Fragment(R.layout.frg_start) {

    private val vm: StartFrgVM by hiltNavGraphViewModels(R.id.startFrg)
    private lateinit var b: FrgStartBinding
    private lateinit var n: NavController

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        b = FrgStartBinding.bind(v)
        n = findNavController()

        val formatter = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        val date = formatter.format(Calendar.getInstance().time)
        b.txtDate.text = date

        b.error.btnAgain.setOnClickListener {  vm.getCategories() }

        b.rv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CategoriesRvAdapter(n)
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

    private fun updateUi(s: StartFrgVM.State) {
        b.txtGeo.text = s.locality ?: resources.getString(nebur.common.R.string.unknown)
        setContainer(s.p)
    }

    private fun setContainer(p: StartFrgVM.Page) {
        b.rv.visibility = GONE
        b.errorP.visibility = GONE
        b.waitP.visibility = GONE

        when(p) {
            StartFrgVM.Page.Categories -> b.rv.visibility = VISIBLE
            StartFrgVM.Page.Error -> b.errorP.visibility = VISIBLE
            StartFrgVM.Page.Wait -> b.waitP.visibility = VISIBLE
        }
    }

}















