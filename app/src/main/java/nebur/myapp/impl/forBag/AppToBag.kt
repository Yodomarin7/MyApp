package nebur.myapp.impl.forBag

import nebur.bag.IAppToBag
import javax.inject.Inject

class AppToBag @Inject constructor(

): IAppToBag {

    override fun getMainGraph(): Int {
        return nebur.main.R.id.main_graph
    }
}