package nebur.main

import nebur.main.model.BagModel

interface BagFromApp {

    suspend fun insert(bag: BagModel)

}