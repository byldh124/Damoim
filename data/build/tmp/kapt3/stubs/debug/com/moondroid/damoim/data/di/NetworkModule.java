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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c7\u0002\u0018\u00002\u00020\u0001:\u0001\u000eB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\t\u001a\u00020\nH\u0007J\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\bH\u0007\u00a8\u0006\u000f"}, d2 = {"Lcom/moondroid/damoim/data/di/NetworkModule;", "", "()V", "provideApiService", "Lcom/moondroid/damoim/data/api/ApiInterface;", "retrofit", "Lretrofit2/Retrofit;", "provideConverterFactory", "Lretrofit2/converter/gson/GsonConverterFactory;", "provideHttpClient", "Lokhttp3/OkHttpClient;", "provideRetrofitInstance", "okHttpClient", "gsonConverterFactory", "NullOnEmptyConverterFactory", "data_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class NetworkModule {
    @org.jetbrains.annotations.NotNull
    public static final com.moondroid.damoim.data.di.NetworkModule INSTANCE = null;
    
    private NetworkModule() {
        super();
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final okhttp3.OkHttpClient provideHttpClient() {
        return null;
    }
    
    @javax.inject.Singleton
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final retrofit2.Retrofit provideRetrofitInstance(@org.jetbrains.annotations.NotNull
    okhttp3.OkHttpClient okHttpClient, @org.jetbrains.annotations.NotNull
    retrofit2.converter.gson.GsonConverterFactory gsonConverterFactory) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final retrofit2.converter.gson.GsonConverterFactory provideConverterFactory() {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.moondroid.damoim.data.api.ApiInterface provideApiService(@org.jetbrains.annotations.NotNull
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    /**
     * 비어있는(length=0)인 Response를 받았을 경우 처리
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J7\u0010\u0003\u001a\f\u0012\u0004\u0012\u00020\u0005\u0012\u0002\b\u00030\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lcom/moondroid/damoim/data/di/NetworkModule$NullOnEmptyConverterFactory;", "Lretrofit2/Converter$Factory;", "()V", "responseBodyConverter", "Lretrofit2/Converter;", "Lokhttp3/ResponseBody;", "type", "Ljava/lang/reflect/Type;", "annotations", "", "", "retrofit", "Lretrofit2/Retrofit;", "(Ljava/lang/reflect/Type;[Ljava/lang/annotation/Annotation;Lretrofit2/Retrofit;)Lretrofit2/Converter;", "data_debug"})
    public static final class NullOnEmptyConverterFactory extends retrofit2.Converter.Factory {
        
        public NullOnEmptyConverterFactory() {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public retrofit2.Converter<okhttp3.ResponseBody, ?> responseBodyConverter(@org.jetbrains.annotations.NotNull
        java.lang.reflect.Type type, @org.jetbrains.annotations.NotNull
        java.lang.annotation.Annotation[] annotations, @org.jetbrains.annotations.NotNull
        retrofit2.Retrofit retrofit) {
            return null;
        }
    }
}