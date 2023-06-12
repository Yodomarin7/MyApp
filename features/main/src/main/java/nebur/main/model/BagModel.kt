package nebur.main.model

data class BagModel(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val image_url: String?,
    val count: Int
)