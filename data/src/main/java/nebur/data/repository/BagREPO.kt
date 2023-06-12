package nebur.data.repository

import kotlinx.coroutines.flow.Flow
import nebur.data.source.local.bag.BagEntity
import nebur.data.source.local.bag.BagSum
import nebur.data.source.local.bag.IBagLocal

class BagREPO(
    private val iBagLocal: IBagLocal
) {
    fun getAll(): Flow<List<BagEntity>> {
        return iBagLocal.getAll()
    }

    fun getTotalSum(): Flow<BagSum> {
        return iBagLocal.getTotalSum()
    }

    suspend fun delete(bagEntity: BagEntity) {
        iBagLocal.delete(bagEntity)
    }

    suspend fun insert(bagEntity: BagEntity) {
        iBagLocal.insert(bagEntity)
    }

    suspend fun update(bagEntity: BagEntity) {
        iBagLocal.update(bagEntity)
    }
}