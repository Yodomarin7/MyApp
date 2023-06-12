package nebur.data.source.local.bag

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

object BagEntityArg {
    const val TABLE = "bag_table"
    const val ID = "id"
    const val NAME = "name"
    const val PRICE = "price"
    const val WEIGHT = "weight"
    const val IMAGE_URL = "image_url"
    const val COUNT = "count"
}

@Entity(
    tableName = BagEntityArg.TABLE,
//    indices = [
//        Index(value = [BagEntityArg.PRODUCT_ID], unique = true),
//    ]
)
data class BagEntity(
    @PrimaryKey
    @ColumnInfo(name = BagEntityArg.ID)
    val id: Int,
    @ColumnInfo(name = BagEntityArg.NAME) val name: String,
    @ColumnInfo(name = BagEntityArg.PRICE) val price: Int,
    @ColumnInfo(name = BagEntityArg.WEIGHT) val weight: Int,
    @ColumnInfo(name = BagEntityArg.IMAGE_URL) val imageUrl: String?,
    @ColumnInfo(name = BagEntityArg.COUNT) val count: Int,
)