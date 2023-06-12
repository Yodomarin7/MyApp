package nebur.main.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nebur.main.CategoriesFromApp
import nebur.main.LocalityFromApp
import nebur.main.model.CategoriesModel
import javax.inject.Inject

@HiltViewModel
class StartFrgVM @Inject constructor(
    private val localityFromApp: LocalityFromApp,
    private val categoriesFromApp: CategoriesFromApp
): ViewModel() {

    sealed interface Page {
        object Wait: Page
        object Error: Page
        object Categories: Page
    }

    data class State(
        val p: Page = Page.Wait,
        val locality: String? = null,
        val items: List<CategoriesModel>? = null
    )

    private val _s = MutableStateFlow(State())
    val s: StateFlow<State> = _s
    private fun setState(state: State.()-> State) {
        _s.value = _s.value.state()
    }

    init {
        viewModelScope.launch {
            localityFromApp.get().collect {
                setState { copy(locality = it) }
            }
        }
        getCategories()
    }

    private var categoriesJob: Job? = null
    fun getCategories() {
        categoriesJob?.cancel()
        setState { copy(p = Page.Wait) }
        categoriesJob = viewModelScope.launch {
            val categories = categoriesFromApp.getAll()
            val p = when(categories) {
                null -> Page.Error
                else -> Page.Categories
            }
            setState { copy(p = p, items = categories) }
        }
    }
}













