package nebur.data.repository

import nebur.data.source.remote.CategoriesRemote

class CategoriesREPO(
    private val categoriesRemote: CategoriesRemote
) {

    suspend fun get(): List<CategoriesRemote.CategoriesModel>? {
        return categoriesRemote.get()
    }

}