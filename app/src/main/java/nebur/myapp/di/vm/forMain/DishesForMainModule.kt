package nebur.myapp.di.vm.forMain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nebur.main.DishesFromApp
import nebur.myapp.impl.forMain.DishesForMain

@Module
@InstallIn(ViewModelComponent::class)
abstract class DishesForMainModule {

    @Binds
    abstract fun bindDishesFromApp(
        dishesForMain: DishesForMain
    ): DishesFromApp

}