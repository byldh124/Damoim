package com.moondroid.damoim.domain.repository;

import com.moondroid.damoim.common.GroupType;
import com.moondroid.damoim.domain.model.GroupItem;
import com.moondroid.damoim.domain.model.MoimItem;
import com.moondroid.damoim.domain.model.Profile;
import com.moondroid.damoim.domain.model.status.ApiResult;
import kotlinx.coroutines.flow.Flow;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\r\bf\u0018\u00002\u00020\u0001JM\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ-\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J3\u0010\u0012\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00130\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0015H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J+\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00130\u00040\u00032\u0006\u0010\b\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J-\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J5\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ5\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u0010H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!Ja\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010#\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u00072\b\u0010%\u001a\u0004\u0018\u00010\r2\b\u0010&\u001a\u0004\u0018\u00010\rH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\'\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006("}, d2 = {"Lcom/moondroid/damoim/domain/repository/GroupRepository;", "", "createGroup", "Lkotlinx/coroutines/flow/Flow;", "Lcom/moondroid/damoim/domain/model/status/ApiResult;", "Lcom/moondroid/damoim/domain/model/GroupItem;", "id", "", "title", "location", "purpose", "interest", "file", "Ljava/io/File;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFavor", "", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGroupList", "", "type", "Lcom/moondroid/damoim/common/GroupType;", "(Ljava/lang/String;Lcom/moondroid/damoim/common/GroupType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMembers", "Lcom/moondroid/damoim/domain/model/Profile;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "join", "", "saveRecent", "lastTime", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setFavor", "active", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateGroup", "originTitle", "information", "thumb", "image", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/io/File;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public abstract interface GroupRepository {
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getGroupList(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.common.GroupType type, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.domain.model.GroupItem>>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createGroup(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String location, @org.jetbrains.annotations.NotNull
    java.lang.String purpose, @org.jetbrains.annotations.NotNull
    java.lang.String interest, @org.jetbrains.annotations.NotNull
    java.io.File file, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.domain.model.GroupItem>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateGroup(@org.jetbrains.annotations.NotNull
    java.lang.String originTitle, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String location, @org.jetbrains.annotations.NotNull
    java.lang.String purpose, @org.jetbrains.annotations.NotNull
    java.lang.String interest, @org.jetbrains.annotations.NotNull
    java.lang.String information, @org.jetbrains.annotations.Nullable
    java.io.File thumb, @org.jetbrains.annotations.Nullable
    java.io.File image, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.domain.model.GroupItem>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMembers(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.domain.model.Profile>>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveRecent(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String lastTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object join(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getFavor(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<java.lang.Boolean>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setFavor(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, boolean active, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>>> $completion);
}