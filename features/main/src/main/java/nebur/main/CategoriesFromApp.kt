package nebur.main

import nebur.main.model.CategoriesModel

interface CategoriesFromApp {
    suspend fun getAll(): List<CategoriesModel>?
}