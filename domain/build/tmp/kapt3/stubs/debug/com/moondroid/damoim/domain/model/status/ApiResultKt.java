package com.moondroid.damoim.domain.model.status;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a:\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005H\u0086\b\u00f8\u0001\u0000\u001a:\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00070\u0005H\u0086\b\u00f8\u0001\u0000\u001a:\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00070\u0005H\u0086\b\u00f8\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\u000b"}, d2 = {"onError", "Lcom/moondroid/damoim/domain/model/status/ApiResult;", "T", "", "action", "Lkotlin/Function1;", "", "", "onFail", "", "onSuccess", "domain_debug"})
public final class ApiResultKt {
    
    @org.jetbrains.annotations.NotNull
    public static final <T extends java.lang.Object>com.moondroid.damoim.domain.model.status.ApiResult<T> onSuccess(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.domain.model.status.ApiResult<? extends T> $this$onSuccess, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> action) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final <T extends java.lang.Object>com.moondroid.damoim.domain.model.status.ApiResult<T> onFail(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.domain.model.status.ApiResult<? extends T> $this$onFail, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> action) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final <T extends java.lang.Object>com.moondroid.damoim.domain.model.status.ApiResult<T> onError(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.domain.model.status.ApiResult<? extends T> $this$onError, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> action) {
        return null;
    }
}