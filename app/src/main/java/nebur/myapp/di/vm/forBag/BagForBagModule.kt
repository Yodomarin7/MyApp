package nebur.myapp.di.vm.forBag

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nebur.bag.BagFromApp
import nebur.myapp.impl.forBag.BagForBag

@Module
@InstallIn(ViewModelComponent::class)
abstract class BagForBagModule {

    @Binds
    abstract fun bindBagFromApp(
        bagForBag: BagForBag
    ): BagFromApp

}