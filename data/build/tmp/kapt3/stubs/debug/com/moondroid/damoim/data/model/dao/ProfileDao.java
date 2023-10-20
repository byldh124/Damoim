package com.moondroid.damoim.data.model.dao;

import androidx.room.*;
import com.moondroid.damoim.data.model.entity.ProfileEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2 = {"Lcom/moondroid/damoim/data/model/dao/ProfileDao;", "", "deleteProfileAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProfile", "Lcom/moondroid/damoim/data/model/entity/ProfileEntity;", "getProfileList", "", "insertProfile", "profile", "(Lcom/moondroid/damoim/data/model/entity/ProfileEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
@androidx.room.Dao
public abstract interface ProfileDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertProfile(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.entity.ProfileEntity profile, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM profile")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getProfileList(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.moondroid.damoim.data.model.entity.ProfileEntity>> $completion);
    
    @androidx.room.Query(value = "DELETE FROM profile")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteProfileAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getProfile(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.model.entity.ProfileEntity> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        
        @org.jetbrains.annotations.Nullable
        public static java.lang.Object getProfile(@org.jetbrains.annotations.NotNull
        com.moondroid.damoim.data.model.dao.ProfileDao $this, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.model.entity.ProfileEntity> $completion) {
            return null;
        }
    }
}