package com.moondroid.damoim.data.repository;

import com.google.gson.Gson;
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource;
import com.moondroid.damoim.data.model.request.CreateMoimRequest;
import com.moondroid.damoim.domain.model.MoimItem;
import com.moondroid.damoim.domain.model.Profile;
import com.moondroid.damoim.domain.model.status.ApiResult;
import com.moondroid.damoim.domain.repository.MoimRepository;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J]\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J+\u0010\u0014\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u00070\u00062\u0006\u0010\u0012\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J+\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00150\u00070\u00062\u0006\u0010\t\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J5\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00070\u00062\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2 = {"Lcom/moondroid/damoim/data/repository/MoimRepositoryImpl;", "Lcom/moondroid/damoim/domain/repository/MoimRepository;", "remoteDataSource", "Lcom/moondroid/damoim/data/datasource/remote/RemoteDataSource;", "(Lcom/moondroid/damoim/data/datasource/remote/RemoteDataSource;)V", "createMoim", "Lkotlinx/coroutines/flow/Flow;", "Lcom/moondroid/damoim/domain/model/status/ApiResult;", "", "title", "", "address", "date", "time", "pay", "lat", "", "lng", "joinMember", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMoimMembers", "", "Lcom/moondroid/damoim/domain/model/Profile;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMoims", "Lcom/moondroid/damoim/domain/model/MoimItem;", "joinMoim", "id", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public final class MoimRepositoryImpl implements com.moondroid.damoim.domain.repository.MoimRepository {
    @org.jetbrains.annotations.NotNull
    private final com.moondroid.damoim.data.datasource.remote.RemoteDataSource remoteDataSource = null;
    
    @javax.inject.Inject
    public MoimRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.datasource.remote.RemoteDataSource remoteDataSource) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object createMoim(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String address, @org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    java.lang.String time, @org.jetbrains.annotations.NotNull
    java.lang.String pay, double lat, double lng, @org.jetbrains.annotations.NotNull
    java.lang.String joinMember, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getMoims(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.domain.model.MoimItem>>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getMoimMembers(@org.jetbrains.annotations.NotNull
    java.lang.String joinMember, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.domain.model.Profile>>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object joinMoim(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.domain.model.MoimItem>>> $completion) {
        return null;
    }
}