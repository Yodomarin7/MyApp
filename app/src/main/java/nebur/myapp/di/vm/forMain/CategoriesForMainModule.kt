package nebur.myapp.di.vm.forMain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nebur.main.CategoriesFromApp
import nebur.myapp.impl.forMain.CategoriesForMain

@Module
@InstallIn(ViewModelComponent::class)
abstract class CategoriesForMainModule {

    @Binds
    abstract fun bindCategoriesFromApp(
        categoriesForMain: CategoriesForMain
    ): CategoriesFromApp

}