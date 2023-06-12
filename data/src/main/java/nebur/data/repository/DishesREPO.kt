package nebur.data.repository

import nebur.data.source.remote.DishesRemote

class DishesREPO(
    private val dishesRemote: DishesRemote
) {

    suspend fun get(teg: String): List<DishesRemote.DishesModel>? {
        return dishesRemote.get(teg)
    }
}