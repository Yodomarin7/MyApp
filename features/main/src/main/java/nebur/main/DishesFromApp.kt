package nebur.main

import nebur.main.model.DishesModel

interface DishesFromApp {
    suspend fun getAll(teg: String): List<DishesModel>?
}