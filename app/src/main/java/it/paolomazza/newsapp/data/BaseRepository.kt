package it.paolomazza.newsapp.data

import it.paolomazza.newsapp.presentation.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

abstract class BaseRepository(
        open val dispatcher: CoroutineDispatcher
) {

    suspend fun <T : Any, Dest : Any> T.toResult(mapper: (T) -> Dest): State<Dest> = withContext(dispatcher) {
        try {
            lateinit var result: Dest


            handleResponse(
                    result = { async { this@toResult } },
                    converter = {
                        result = mapper(it)
                        BaseModel()
                    }


            )

            result.let {
                State.Success(it)
            }


        }
        catch (t: Throwable){
            State.ErrorState(t)
        }
    }

    private suspend fun <T : Any> handleResponse(result: () -> Deferred<T>, converter: (T) -> BaseModel): BaseModel {
            val serviceResult = result().await()
            return converter(serviceResult)
    }
}