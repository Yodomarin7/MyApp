package nebur.bag.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nebur.bag.BagFromApp
import nebur.bag.LocalityFromApp
import nebur.bag.model.BagModel
import javax.inject.Inject

@HiltViewModel
class BagFrgVM @Inject constructor(
    private val bagFromApp: BagFromApp,
    private val localityFromApp: LocalityFromApp,
) : ViewModel() {

    data class State(
        val items: List<BagModel> = listOf(),
        val totalSum: Int = 0,
        val locality: String? = null
    )

    private val _s = MutableStateFlow(State())
    val s: StateFlow<State> = _s
    private fun setState(state: State.()-> State) {
        _s.value = _s.value.state()
    }

    init {
        viewModelScope.launch {
            launch { getLocality() } //flow
            launch { getBags() } //flow
            launch { getTotalSum() } //flow
        }
    }

    private suspend fun getLocality() {
        localityFromApp.get().collect {
            setState { copy(locality = it) }
        }
    }

    private suspend fun getBags() {
        bagFromApp.getAll().collect { list->
            setState { copy(items = list) }
        }
    }

    private suspend fun getTotalSum() {
        bagFromApp.getTotalSum().collect { sum->
            setState { copy(totalSum = sum) }
        }
    }

    fun update(model: BagModel) {
        viewModelScope.launch {
            if(model.count < 1) bagFromApp.delete(model)
            else bagFromApp.update(model)
        }
    }
}








