package com.moondroid.damoim.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.moondroid.damoim.network.ApiInterface
import com.moondroid.damoim.network.Repository
import com.moondroid.damoim.network.RepositoryImpl
//import com.moondroid.damoim.network.RepositoryImpl
import com.moondroid.damoim.network.URLManager.BASE_URL
import com.moondroid.damoim.utils.DMLog
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.internal.EMPTY_RESPONSE
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

val appModules = module {
    single {
        createWebService<ApiInterface>(okHttpClient = createHttpClient(), baseUrl = BASE_URL)
    }

    factory<Repository> { RepositoryImpl(api = get()) }
}


/*
 * 커스텀한 OKHttpClient 객체를 interceptor와 함께 반환한다.
 * Retrofit 서비스를 구축하기 위해 사용한다.
 */
fun createHttpClient(): OkHttpClient {
//    val client = SSLConnect.getUnsafeOkHttpClient()
    val client = OkHttpClient.Builder()
    client.connectTimeout(60, TimeUnit.SECONDS)
    client.readTimeout(60, TimeUnit.SECONDS)
    client.writeTimeout(60, TimeUnit.SECONDS)
    // 참고: addInterceptor 사용하기 위해서 gradle 파일에 compileOptions, kotlinOptions 추가해 주어야 함
    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method, original.body).build()
        DMLog.d(request.toString())
        return@addInterceptor it.proceed(request)
    }.build()
}


/* Retrofit 서비스 구축하기 위한 메소드 */
inline fun <reified T> createWebService(okHttpClient: OkHttpClient, baseUrl: String): T {
    DMLog.d("createWebService()")
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}

/**
 * 비어있는(length=0)인 Response를 받았을 경우 처리
 */
class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
        return Converter<ResponseBody, Any> {
            if (it.contentLength() == 0L) return@Converter EMPTY_RESPONSE
            delegate.convert(it)
        }
    }

}


/**
 * 백그라운드에서 HTTP 통신하는 경우 ServiceBulider를 사용함
 */
object ServiceBuilder {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(createHttpClient())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
