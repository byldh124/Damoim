package com.moondroid.damoim.data.di;

import android.util.Log;
import com.google.gson.GsonBuilder;
import com.moondroid.damoim.data.BuildConfig;
import com.moondroid.damoim.data.api.ApiInterface;
import com.moondroid.damoim.data.api.response.ResponseAdapterFactory;
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource;
import com.moondroid.damoim.data.datasource.remote.RemoteDataSourceImpl;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.lang.reflect.Type;
import javax.inject.Singleton;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006\u0007"}, d2 = {"Lcom/moondroid/damoim/data/di/NetworkBindModule;", "", "()V", "bindRemoteDataSource", "Lcom/moondroid/damoim/data/datasource/remote/RemoteDataSource;", "remoteDataSourceImpl", "Lcom/moondroid/damoim/data/datasource/remote/RemoteDataSourceImpl;", "data_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class NetworkBindModule {
    
    public NetworkBindModule() {
        super();
    }
    
    @dagger.Binds
    @org.jetbrains.annotations.NotNull
    public abstract com.moondroid.damoim.data.datasource.remote.RemoteDataSource bindRemoteDataSource(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.datasource.remote.RemoteDataSourceImpl remoteDataSourceImpl);
}