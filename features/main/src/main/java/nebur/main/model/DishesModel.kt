package nebur.main.model

enum class Tegs(val str: String) {
    ALL("Все меню"), SALAD("Салаты"), RICE("С рисом"), FISH("С рыбой")
}

data class DishesModel(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val image_url: String?,
    val tegs: List<Tegs>,
)