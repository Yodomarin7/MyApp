package nebur.myapp.di.fragment

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import nebur.bag.IAppToBag
import nebur.myapp.impl.forBag.AppToBag

@Module
@InstallIn(FragmentComponent::class)
abstract class AppToBagModule {

    @Binds
    abstract fun bindIAppToBag(
        appToBag: AppToBag
    ): IAppToBag

}