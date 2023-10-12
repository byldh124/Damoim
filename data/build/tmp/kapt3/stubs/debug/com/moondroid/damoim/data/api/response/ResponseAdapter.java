package com.moondroid.damoim.data.api.response;

import retrofit2.Call;
import retrofit2.CallAdapter;
import java.lang.reflect.Type;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u001a\u0012\u0004\u0012\u0002H\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00040\u00030\u0002B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\"\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u00032\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/moondroid/damoim/data/api/response/ResponseAdapter;", "T", "Lretrofit2/CallAdapter;", "Lretrofit2/Call;", "Lcom/moondroid/damoim/data/api/response/ApiStatus;", "successType", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Type;)V", "adapt", "call", "responseType", "data_debug"})
public final class ResponseAdapter<T extends java.lang.Object> implements retrofit2.CallAdapter<T, retrofit2.Call<com.moondroid.damoim.data.api.response.ApiStatus<? extends T>>> {
    @org.jetbrains.annotations.NotNull
    private final java.lang.reflect.Type successType = null;
    
    public ResponseAdapter(@org.jetbrains.annotations.NotNull
    java.lang.reflect.Type successType) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.reflect.Type responseType() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public retrofit2.Call<com.moondroid.damoim.data.api.response.ApiStatus<T>> adapt(@org.jetbrains.annotations.NotNull
    retrofit2.Call<T> call) {
        return null;
    }
}