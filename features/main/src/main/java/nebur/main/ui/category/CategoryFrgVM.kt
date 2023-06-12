package nebur.main.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nebur.main.BagFromApp
import nebur.main.DishesFromApp
import nebur.main.model.BagModel
import nebur.main.model.DishesModel
import nebur.main.model.Tegs
import javax.inject.Inject

@HiltViewModel
class CategoryFrgVM @Inject constructor(
    private val dishesFromApp: DishesFromApp,
    private val bagFromApp: BagFromApp,
) : ViewModel() {

    sealed interface Page {
        object Wait: Page
        object Error: Page
        object Dishes: Page
    }

    data class State(
        val p: Page = Page.Wait,
        val items: List<Triple<DishesModel, DishesModel?, DishesModel?>>? = null
    )

    private val _s = MutableStateFlow(State())
    val s: StateFlow<State> = _s
    private fun setState(state: State.()-> State) {
        _s.value = _s.value.state()
    }

    init {
        getDishes(Tegs.ALL)
    }

    private var dishesJob: Job? = null
    fun getDishes(tegs: Tegs) {
        dishesJob?.cancel()
        setState { copy(p = Page.Wait) }
        dishesJob = viewModelScope.launch {
            val dishes = dishesFromApp.getAll(tegs.str)
            val p = when(dishes) {
                null -> Page.Error
                else -> Page.Dishes
            }
            setState { copy(p = p, items = getTripleList(dishes)) }
        }
    }

    fun insertBag(bagModel: BagModel) {
        viewModelScope.launch {
            bagFromApp.insert(bagModel)
        }
    }

    private fun getTripleList(list: List<DishesModel>?): List<Triple<DishesModel, DishesModel?,
            DishesModel?>>? {
        return if(list == null) null
        else {
            val result = mutableListOf<Triple<DishesModel, DishesModel?, DishesModel?>>()
            for(i in list.indices step 3) {
                val triple = Triple( list.get(i), list.getOrNull(i+1), list.getOrNull(i+2) )
                result.add(triple)
            }
            result
        }
    }
}





