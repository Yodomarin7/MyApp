package nebur.myapp.impl.forBag

import kotlinx.coroutines.flow.Flow
import nebur.bag.LocalityFromApp
import nebur.data.repository.AllUserSaveREPO
import javax.inject.Inject

class LocalityForBag @Inject constructor(
    private val allUserSaveREPO: AllUserSaveREPO
): LocalityFromApp {

    override suspend fun get(): Flow<String?> {
        return allUserSaveREPO.getLocality()
    }

}