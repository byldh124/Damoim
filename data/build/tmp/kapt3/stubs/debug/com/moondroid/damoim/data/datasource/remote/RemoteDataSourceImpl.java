package com.moondroid.damoim.data.datasource.remote;

import com.moondroid.damoim.common.GroupType;
import com.moondroid.damoim.common.ResponseCode;
import com.moondroid.damoim.data.api.ApiInterface;
import com.moondroid.damoim.data.api.response.ApiStatus;
import com.moondroid.damoim.data.model.dto.GroupItemDTO;
import com.moondroid.damoim.data.model.dto.MoimItemDTO;
import com.moondroid.damoim.data.model.dto.ProfileDTO;
import com.moondroid.damoim.data.model.entity.ProfileEntity;
import com.moondroid.damoim.data.model.request.CreateMoimRequest;
import com.moondroid.damoim.data.model.request.SaltRequest;
import com.moondroid.damoim.data.model.request.SignInRequest;
import com.moondroid.damoim.data.model.request.SignUpRequest;
import com.moondroid.damoim.data.model.request.SocialSignRequest;
import com.moondroid.damoim.data.model.request.UpdateTokenRequest;
import com.moondroid.damoim.domain.model.status.ApiResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J/\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ5\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00120\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\u001f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0017\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\'\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00062\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ-\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0 0\u00062\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010!\u001a\u00020\"H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#J%\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0 0\u00062\u0006\u0010\u001d\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&J%\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0 0\u00062\u0006\u0010(\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&J%\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020*0 0\u00062\u0006\u0010\u001d\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&J\u001f\u0010+\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\u0006\u0010\u0017\u001a\u00020,H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010-J\'\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ/\u0010/\u001a\b\u0012\u0004\u0012\u00020*0\u00062\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u00100\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00101J/\u00102\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u00103\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00101J/\u00104\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u00105\u001a\u00020\u001bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00106J\u001f\u00107\u001a\b\u0012\u0004\u0012\u0002080\u00062\u0006\u0010\u0017\u001a\u000209H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010:J\u001f\u0010;\u001a\b\u0012\u0004\u0012\u0002080\u00062\u0006\u0010\u0017\u001a\u00020<H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010=J\u001f\u0010>\u001a\b\u0012\u0004\u0012\u0002080\u00062\u0006\u0010\u0017\u001a\u00020?H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010@J?\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00120\u00112\b\u0010B\u001a\u0004\u0018\u00010\u00142\b\u0010C\u001a\u0004\u0018\u00010\u0014H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010DJ\'\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010F\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ5\u0010G\u001a\b\u0012\u0004\u0012\u0002080\u00062\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00120\u00112\b\u0010B\u001a\u0004\u0018\u00010\u0014H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\'\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010I\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ\f\u0010J\u001a\u00020\u001b*\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006K"}, d2 = {"Lcom/moondroid/damoim/data/datasource/remote/RemoteDataSourceImpl;", "Lcom/moondroid/damoim/data/datasource/remote/RemoteDataSource;", "api", "Lcom/moondroid/damoim/data/api/ApiInterface;", "(Lcom/moondroid/damoim/data/api/ApiInterface;)V", "checkAppVersion", "Lcom/moondroid/damoim/domain/model/status/ApiResult;", "", "packageName", "", "versionCode", "", "versionName", "(Ljava/lang/String;ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createGroup", "Lcom/moondroid/damoim/data/model/dto/GroupItemDTO;", "body", "", "Lokhttp3/RequestBody;", "file", "Lokhttp3/MultipartBody$Part;", "(Ljava/util/Map;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createMoim", "request", "Lcom/moondroid/damoim/data/model/request/CreateMoimRequest;", "(Lcom/moondroid/damoim/data/model/request/CreateMoimRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFavor", "", "id", "title", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGroupList", "", "type", "Lcom/moondroid/damoim/common/GroupType;", "(Ljava/lang/String;Lcom/moondroid/damoim/common/GroupType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMembers", "Lcom/moondroid/damoim/data/model/dto/ProfileDTO;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMoimMember", "joinMembers", "getMoims", "Lcom/moondroid/damoim/data/model/dto/MoimItemDTO;", "getSalt", "Lcom/moondroid/damoim/data/model/request/SaltRequest;", "(Lcom/moondroid/damoim/data/model/request/SaltRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinGroup", "joinMoim", "date", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveRecent", "lastTime", "setFavor", "active", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signIn", "Lcom/moondroid/damoim/data/model/entity/ProfileEntity;", "Lcom/moondroid/damoim/data/model/request/SignInRequest;", "(Lcom/moondroid/damoim/data/model/request/SignInRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signUp", "Lcom/moondroid/damoim/data/model/request/SignUpRequest;", "(Lcom/moondroid/damoim/data/model/request/SignUpRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "socialSign", "Lcom/moondroid/damoim/data/model/request/SocialSignRequest;", "(Lcom/moondroid/damoim/data/model/request/SocialSignRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateGroup", "thumb", "image", "(Ljava/util/Map;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateInterest", "interest", "updateProfile", "updateToken", "token", "success", "data_debug"})
public final class RemoteDataSourceImpl implements com.moondroid.damoim.data.datasource.remote.RemoteDataSource {
    @org.jetbrains.annotations.NotNull
    private final com.moondroid.damoim.data.api.ApiInterface api = null;
    
    @javax.inject.Inject
    public RemoteDataSourceImpl(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.api.ApiInterface api) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object checkAppVersion(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, int versionCode, @org.jetbrains.annotations.NotNull
    java.lang.String versionName, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object signUp(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SignUpRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.entity.ProfileEntity>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object signIn(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SignInRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.entity.ProfileEntity>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getSalt(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SaltRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<java.lang.String>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object socialSign(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SocialSignRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.entity.ProfileEntity>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateToken(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateInterest(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String interest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateProfile(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends okhttp3.RequestBody> body, @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part thumb, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.entity.ProfileEntity>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object createGroup(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends okhttp3.RequestBody> body, @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part file, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.dto.GroupItemDTO>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateGroup(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends okhttp3.RequestBody> body, @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part thumb, @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part image, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.dto.GroupItemDTO>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getMembers(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.data.model.dto.ProfileDTO>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getMoims(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.data.model.dto.MoimItemDTO>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object saveRecent(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String lastTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object joinGroup(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getFavor(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<java.lang.Boolean>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object setFavor(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, boolean active, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object createMoim(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.CreateMoimRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getGroupList(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.common.GroupType type, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.data.model.dto.GroupItemDTO>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getMoimMember(@org.jetbrains.annotations.NotNull
    java.lang.String joinMembers, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.data.model.dto.ProfileDTO>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object joinMoim(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.dto.MoimItemDTO>> $completion) {
        return null;
    }
    
    private final boolean success(int $this$success) {
        return false;
    }
}