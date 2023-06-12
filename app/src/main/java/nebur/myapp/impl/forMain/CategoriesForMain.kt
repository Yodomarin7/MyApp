package nebur.myapp.impl.forMain

import nebur.data.repository.CategoriesREPO
import nebur.main.CategoriesFromApp
import nebur.main.model.CategoriesModel
import javax.inject.Inject

class CategoriesForMain @Inject constructor(
    private val categoriesREPO: CategoriesREPO
): CategoriesFromApp {

    override suspend fun getAll(): List<CategoriesModel>? {
        val list = categoriesREPO.get() ?: return null

        val result = mutableListOf<CategoriesModel>()
        list.forEach {
            result.add(CategoriesModel(id = it.id, name = it.name, image_url = it.image_url))
        }

        return result
    }

}