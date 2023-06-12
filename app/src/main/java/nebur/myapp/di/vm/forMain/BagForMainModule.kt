package nebur.myapp.di.vm.forMain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nebur.main.BagFromApp
import nebur.myapp.impl.forMain.BagForMain

@Module
@InstallIn(ViewModelComponent::class)
abstract class BagForMainModule {

    @Binds
    abstract fun bindBagFromApp(
        bagForMain: BagForMain
    ): BagFromApp

}