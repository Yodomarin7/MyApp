package nebur.myapp.impl.forMain

import nebur.data.repository.DishesREPO
import nebur.main.DishesFromApp
import nebur.main.model.DishesModel
import nebur.main.model.Tegs
import javax.inject.Inject

class DishesForMain @Inject constructor(
    private val dishesREPO: DishesREPO
): DishesFromApp {

    override suspend fun getAll(teg: String): List<DishesModel>? {
        val list = dishesREPO.get(teg) ?: return null

        val result = mutableListOf<DishesModel>()
        list.forEach {
            result.add(DishesModel(id = it.id, name = it.name, price = it.price, weight = it.weight,
                description = it.description, image_url = it.image_url, tegs = getTegsFromString(it.tegs))
            )
        }

        return result
    }

    private fun getTegsFromString(strList: List<String>): List<Tegs> {
        val tegsList = mutableListOf<Tegs>()

        strList.forEach { str->
            val teg = when(str) {
                "Все меню" -> Tegs.ALL
                "Салаты" -> Tegs.SALAD
                "С рисом" -> Tegs.RICE
                "С рыбой" -> Tegs.FISH
                else -> Tegs.ALL
            }
            tegsList.add(teg)
        }

        return tegsList
    }

}