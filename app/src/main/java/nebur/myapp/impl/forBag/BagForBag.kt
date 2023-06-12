package nebur.myapp.impl.forBag

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nebur.bag.BagFromApp
import nebur.bag.model.BagModel
import nebur.data.repository.BagREPO
import nebur.data.source.local.bag.BagEntity
import javax.inject.Inject

class BagForBag @Inject constructor(
    private val bagREPO: BagREPO
): BagFromApp {

    override suspend fun update(bag: BagModel) {
        bagREPO.update(BagEntity(id = bag.id, name = bag.name, price = bag.price,
            weight = bag.weight, imageUrl = bag.image_url, count = bag.count))
    }

    override suspend fun delete(bag: BagModel) {
        bagREPO.delete(BagEntity(id = bag.id, name = bag.name, price = bag.price,
            weight = bag.weight, imageUrl = bag.image_url, count = bag.count))
    }

    override fun getAll(): Flow<List<BagModel>> {
        return flow {
            bagREPO.getAll().collect { list->
                val result = mutableListOf<BagModel>()

                list.forEach { bag->
                    result.add(BagModel(id = bag.id, name = bag.name, price = bag.price,
                        weight = bag.weight, image_url = bag.imageUrl, count = bag.count))
                }

                emit(result)
            }
        }
    }

    override fun getTotalSum(): Flow<Int> {
        return flow {
            bagREPO.getTotalSum().collect { BagSum->
                emit(BagSum.sum)
            }
        }
    }

}













