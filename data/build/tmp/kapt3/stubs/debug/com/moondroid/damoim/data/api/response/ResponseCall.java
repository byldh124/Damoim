package com.moondroid.damoim.data.api.response;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0014\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u0002H\u0016J\u001c\u0010\t\u001a\u00020\u00072\u0012\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u000bH\u0016J\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/moondroid/damoim/data/api/response/ResponseCall;", "T", "Lretrofit2/Call;", "Lcom/moondroid/damoim/data/api/response/ApiStatus;", "callDelegate", "(Lretrofit2/Call;)V", "cancel", "", "clone", "enqueue", "callback", "Lretrofit2/Callback;", "execute", "Lretrofit2/Response;", "isCanceled", "", "isExecuted", "request", "Lokhttp3/Request;", "timeout", "Lokio/Timeout;", "data_debug"})
public final class ResponseCall<T extends java.lang.Object> implements retrofit2.Call<com.moondroid.damoim.data.api.response.ApiStatus<? extends T>> {
    @org.jetbrains.annotations.NotNull
    private final retrofit2.Call<T> callDelegate = null;
    
    public ResponseCall(@org.jetbrains.annotations.NotNull
    retrofit2.Call<T> callDelegate) {
        super();
    }
    
    @java.lang.Override
    public void enqueue(@org.jetbrains.annotations.NotNull
    retrofit2.Callback<com.moondroid.damoim.data.api.response.ApiStatus<T>> callback) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public retrofit2.Call<com.moondroid.damoim.data.api.response.ApiStatus<T>> clone() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public retrofit2.Response<com.moondroid.damoim.data.api.response.ApiStatus<T>> execute() {
        return null;
    }
    
    @java.lang.Override
    public boolean isExecuted() {
        return false;
    }
    
    @java.lang.Override
    public void cancel() {
    }
    
    @java.lang.Override
    public boolean isCanceled() {
        return false;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public okhttp3.Request request() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public okio.Timeout timeout() {
        return null;
    }
}