package com.moondroid.damoim.data.repository;

import com.moondroid.damoim.data.model.dao.ProfileDao;
import com.moondroid.damoim.data.model.request.SaltRequest;
import com.moondroid.damoim.data.model.request.SignInRequest;
import com.moondroid.damoim.data.model.request.SignUpRequest;
import com.moondroid.damoim.data.model.request.SocialSignRequest;
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource;
import com.moondroid.damoim.domain.model.Profile;
import com.moondroid.damoim.domain.model.status.ApiResult;
import com.moondroid.damoim.domain.repository.SignRepository;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J%\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u000b\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ-\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010Je\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J%\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\b2\u0006\u0010\u000b\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001b"}, d2 = {"Lcom/moondroid/damoim/data/repository/SignRepositoryImpl;", "Lcom/moondroid/damoim/domain/repository/SignRepository;", "remoteDataSource", "Lcom/moondroid/damoim/data/datasource/remote/RemoteDataSource;", "localDataSource", "Lcom/moondroid/damoim/data/model/dao/ProfileDao;", "(Lcom/moondroid/damoim/data/datasource/remote/RemoteDataSource;Lcom/moondroid/damoim/data/model/dao/ProfileDao;)V", "getSalt", "Lkotlinx/coroutines/flow/Flow;", "Lcom/moondroid/damoim/domain/model/status/ApiResult;", "", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signIn", "Lcom/moondroid/damoim/domain/model/Profile;", "hashPw", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signUp", "salt", "name", "birth", "gender", "location", "interest", "thumb", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "socialSign", "data_debug"})
public final class SignRepositoryImpl implements com.moondroid.damoim.domain.repository.SignRepository {
    @org.jetbrains.annotations.NotNull
    private final com.moondroid.damoim.data.datasource.remote.RemoteDataSource remoteDataSource = null;
    @org.jetbrains.annotations.NotNull
    private final com.moondroid.damoim.data.model.dao.ProfileDao localDataSource = null;
    
    @javax.inject.Inject
    public SignRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.datasource.remote.RemoteDataSource remoteDataSource, @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.dao.ProfileDao localDataSource) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object signUp(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String hashPw, @org.jetbrains.annotations.NotNull
    java.lang.String salt, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String birth, @org.jetbrains.annotations.NotNull
    java.lang.String gender, @org.jetbrains.annotations.NotNull
    java.lang.String location, @org.jetbrains.annotations.NotNull
    java.lang.String interest, @org.jetbrains.annotations.NotNull
    java.lang.String thumb, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.domain.model.Profile>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object signIn(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String hashPw, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.domain.model.Profile>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object socialSign(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.domain.model.Profile>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getSalt(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<java.lang.String>>> $completion) {
        return null;
    }
}