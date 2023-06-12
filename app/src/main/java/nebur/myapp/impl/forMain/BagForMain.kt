package nebur.myapp.impl.forMain

import nebur.data.repository.BagREPO
import nebur.data.source.local.bag.BagEntity
import nebur.main.BagFromApp
import nebur.main.model.BagModel
import javax.inject.Inject

class BagForMain @Inject constructor(
    private val bagREPO: BagREPO
): BagFromApp {

    override suspend fun insert(bag: BagModel) {
        bagREPO.insert(BagEntity(id = bag.id, name = bag.name, price = bag.price,
            weight = bag.weight, imageUrl = bag.image_url, count = bag.count))
    }

}