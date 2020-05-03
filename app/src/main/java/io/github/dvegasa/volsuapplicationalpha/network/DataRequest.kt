package io.github.dvegasa.volsuapplicationalpha.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import java.io.IOException

/**
 * Created by Ed Khalturin @DVegasa
 */

@Suppress("LeakingThis")
/**
 * Объект, для получения данных из интернета, и их кеширования. При неудачном подключении,
 * возвращается кешированный объект. Более формально: сначала возвращается [LiveData] с кешированной
 * версией, параллельно происходит подключение к интернет серверу, для обновления данных.
 * В случае успешного подключения и получения данных, кеширует данные в БД (Room). Данные в
 * ранее выданной LiveData обновятся автоматически.
 *
 * @param requestStatus     Содержит в себе статус операции. Сразу после создания запроса,
 *                          присуждается значение [RequestStatus.LOADING]. Если интернет подключение
 *                          закончилось удачно, назначается значение [RequestStatus.OK]. В случае
 *                          ошибки -- [RequestStatus.ERROR]
 *
 * @param RequestType       Тип, представляющий объект, полученный из интернет запроса. Обязан
 *                          наследовать интерфейс [WebResponse<ResultType>]
 *
 * @param ResultType        Тип, представляющий объект, конвертированный из [RequestType], чтобы
 *                          вдальнейшем оперировать этим объектои внутри проекта
 */
abstract class DataRequest
<RequestType, ResultType>
    (private val requestStatus: MutableLiveData<RequestStatus>) {

    private val liveData: LiveData<ResultType> by lazy {
        getCachedData()
    }

    init {
        requestStatus.value = RequestStatus(Status.LOADING)
        Thread(Runnable {
            if (shouldFetch()) {
                try {
                    val response = getRetrofitCall().execute()

                    if (response.isSuccessful && response.body() != null) {
                        val requestObject = response.body()!!
                        cacheResult(requestToResult(requestObject))
                        requestStatus.postValue(RequestStatus(Status.OK))

                    } else {
                        val errorStr = response.errorBody()?.string() ?: "Иная ошибка загрузки"
                        requestStatus.postValue(RequestStatus(Status.ERROR, errorStr))
                    }
                } catch (e: IOException) {
                    requestStatus.postValue(RequestStatus(Status.ERROR, "Не удалось соединиться"))
                }
            }
        }).start()
    }

    /**
     * @return Возвращает [LiveData] в котором потом придут результаты обновления данных.
     *         Изначально содержит в себе кешированную версию, полученную из
     *         [DataRequest.shouldFetch()], затем при удачном подключении к интернету, в этом
     *         объекте появятся результаты запроса. По факту, возвращает полученное значение
     *         из [DataRequest.getCachedData()]
     */
    fun asLiveData(): LiveData<ResultType> {
        return liveData
    }

    /**
     * Определяет условие, при котором должно произойти обновление данных путём скачивания
     * их из интернета.
     * @return Надо ли обновить данные (значение `false`) или использовать кешированные ранее
     *         данные (значение `true`)
     */
    abstract fun shouldFetch(): Boolean

    /**
     * Получение кешированных данных.
     * @return [LiveData] объект, полученный из Room SQLite базы данных. Именно этот объект и
     *         возвращается при вызове [DataRequest.asLiveData()]
     */
    abstract fun getCachedData(): LiveData<ResultType>

    /**
     * @return [Retrofit.Call], который будет вызван из [DataRequest] для получения данных из интернета
     */
    abstract fun getRetrofitCall(): Call<RequestType>

    /**
     * Вызывается при успешном обновлении данных из интернета, для обновления кешированной версии
     * данных
     */
    abstract fun cacheResult(data: ResultType)

    /**
     * Адаптирует объект, полученный из интернета ([requst]) в объект, который будет использоваться
     * вдальнейшем внутри приложения ([ResultType])
     */
    abstract fun requestToResult(requst: RequestType): ResultType
}