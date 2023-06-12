package nebur.data.source.local.bag

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IBagLocal {

    @Query("SELECT * FROM ${BagEntityArg.TABLE}")
    fun getAll(): Flow<List<BagEntity>>

    @Query("SELECT SUM(price*count) AS sum FROM ${BagEntityArg.TABLE}")
    fun getTotalSum(): Flow<BagSum>

    @Insert(onConflict = IGNORE)
    suspend fun insert(bagEntity: BagEntity)

    @Delete
    suspend fun delete(bagEntity: BagEntity)

    @Update
    suspend fun update(bagEntity: BagEntity)
}