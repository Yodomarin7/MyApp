package nebur.myapp.di.vm.forMain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nebur.main.LocalityFromApp
import nebur.myapp.impl.forMain.LocalityForMain

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocalityForMainModule {

    @Binds
    abstract fun bindLocalityFromApp(
        localityForMain: LocalityForMain
    ): LocalityFromApp

}











