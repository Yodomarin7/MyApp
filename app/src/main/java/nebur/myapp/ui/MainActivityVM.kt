package nebur.myapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nebur.data.repository.AllUserSaveREPO
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val allUserSaveREPO: AllUserSaveREPO
): ViewModel() {

    data class State(
        val currentGraphId: Int = nebur.main.R.id.main_graph
    )

    private val _s = MutableStateFlow(State())
    val s: StateFlow<State> = _s
    private fun setState(state: State.()-> State) {
        _s.value = _s.value.state()
    }

    fun saveLocality(str: String) {
        viewModelScope.launch {
            allUserSaveREPO.saveLocality(str)
        }
    }

    val navListener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { n, destination, _ ->
            getNearestGraph(destination)?.let {
                setState { copy(currentGraphId = it) }
            }
        }


    private fun getNearestGraph(destination: NavDestination): Int? {
        for (d in destination.hierarchy) {
            when(d.id) {
                nebur.main.R.id.main_graph -> return nebur.main.R.id.main_graph
                nebur.bag.R.id.bag_graph -> return nebur.bag.R.id.bag_graph
            }
        }
        return null
    }
}












