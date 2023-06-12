package nebur.bag.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nebur.bag.IAppToBag
import nebur.bag.R
import nebur.bag.databinding.FrgBagBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class BagFrg : Fragment(R.layout.frg_bag) {

    @Inject
    lateinit var iAppToBag: IAppToBag

    private val vm: BagFrgVM by hiltNavGraphViewModels(R.id.bag_graph)
    private lateinit var b: FrgBagBinding
    private lateinit var n: NavController

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        if (context == null) return

        b = FrgBagBinding.bind(v)
        n = findNavController()

        b.rv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = BagRvAdapter(vm)
        b.rv.adapter = adapter

        val formatter = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        val date = formatter.format(Calendar.getInstance().time)

        b.txtDate.text = date

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.s.collect { s ->
                    b.txtGeo.text = s.locality ?: resources.getString(nebur.common.R.string.unknown)
                    b.btnPay.text = "Оплатить ${s.totalSum} ₽"
                    adapter.submitList(s.items)
                }
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                iAppToBag.getMainGraph()

                n.navigate(iAppToBag.getMainGraph(), null, navOptions {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(n.graph.findStartDestination().id) {
                        saveState = true
                    }
                })
            }
        })
    }
}