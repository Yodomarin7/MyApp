package nebur.myapp.di.vm.forBag

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nebur.bag.LocalityFromApp
import nebur.myapp.impl.forBag.LocalityForBag

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocalityForBagModule {

    @Binds
    abstract fun bindLocalityFromApp(
        localityForBag: LocalityForBag
    ): LocalityFromApp

}