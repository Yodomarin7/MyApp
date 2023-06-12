package nebur.bag

import kotlinx.coroutines.flow.Flow
import nebur.bag.model.BagModel

interface BagFromApp {

    suspend fun update(bag: BagModel)
    suspend fun delete(bag: BagModel)
    fun getAll(): Flow<List<BagModel>>
    fun getTotalSum(): Flow<Int>

}