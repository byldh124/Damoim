package com.moondroid.damoim.domain.repository;

import com.moondroid.damoim.domain.model.Profile;
import com.moondroid.damoim.domain.model.status.ApiResult;
import kotlinx.coroutines.flow.Flow;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u001d\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00040\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J%\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00040\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJW\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00040\u00032\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\f2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J-\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00040\u00032\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001a\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001b"}, d2 = {"Lcom/moondroid/damoim/domain/repository/ProfileRepository;", "", "deleteProfile", "Lkotlinx/coroutines/flow/Flow;", "Lcom/moondroid/damoim/domain/model/status/ApiResult;", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProfile", "Lcom/moondroid/damoim/domain/model/Profile;", "updateInterest", "", "interest", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProfile", "id", "name", "birth", "gender", "location", "message", "thumb", "Ljava/io/File;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateToken", "token", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public abstract interface ProfileRepository {
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getProfile(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.domain.model.Profile>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateToken(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateInterest(@org.jetbrains.annotations.NotNull
    java.lang.String interest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteProfile(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<java.lang.Boolean>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateProfile(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String birth, @org.jetbrains.annotations.NotNull
    java.lang.String gender, @org.jetbrains.annotations.NotNull
    java.lang.String location, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.io.File thumb, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.domain.model.Profile>>> $completion);
}