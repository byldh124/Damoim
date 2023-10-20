package com.moondroid.damoim.data.datasource.remote;

import com.moondroid.damoim.common.GroupType;
import com.moondroid.damoim.data.model.dto.GroupItemDTO;
import com.moondroid.damoim.data.model.dto.MoimItemDTO;
import com.moondroid.damoim.data.model.dto.ProfileDTO;
import com.moondroid.damoim.data.model.entity.ProfileEntity;
import com.moondroid.damoim.data.model.request.CreateMoimRequest;
import com.moondroid.damoim.data.model.request.SaltRequest;
import com.moondroid.damoim.data.model.request.SignInRequest;
import com.moondroid.damoim.data.model.request.SignUpRequest;
import com.moondroid.damoim.data.model.request.SocialSignRequest;
import com.moondroid.damoim.domain.model.status.ApiResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001J/\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ5\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000f0\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u001f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0014\u001a\u00020\u0015H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\'\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ-\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u001d0\u00032\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u001fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J%\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u001d0\u00032\u0006\u0010\u001a\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#J%\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u001d0\u00032\u0006\u0010%\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#J%\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u001d0\u00032\u0006\u0010\u001a\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#J\u001f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u0006\u0010\u0014\u001a\u00020)H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*J\'\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ/\u0010,\u001a\b\u0012\u0004\u0012\u00020\'0\u00032\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.J/\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.J/\u00101\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u00102\u001a\u00020\u0018H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00103J\u001f\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00032\u0006\u0010\u0014\u001a\u000206H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00107J\u001f\u00108\u001a\b\u0012\u0004\u0012\u0002050\u00032\u0006\u0010\u0014\u001a\u000209H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010:J\u001f\u0010;\u001a\b\u0012\u0004\u0012\u0002050\u00032\u0006\u0010\u0014\u001a\u00020<H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010=J?\u0010>\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000f0\u000e2\b\u0010?\u001a\u0004\u0018\u00010\u00112\b\u0010@\u001a\u0004\u0018\u00010\u0011H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010AJ\'\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010C\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ5\u0010D\u001a\b\u0012\u0004\u0012\u0002050\u00032\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000f0\u000e2\b\u0010?\u001a\u0004\u0018\u00010\u0011H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\'\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010F\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006G"}, d2 = {"Lcom/moondroid/damoim/data/datasource/remote/RemoteDataSource;", "", "checkAppVersion", "Lcom/moondroid/damoim/domain/model/status/ApiResult;", "", "packageName", "", "versionCode", "", "versionName", "(Ljava/lang/String;ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createGroup", "Lcom/moondroid/damoim/data/model/dto/GroupItemDTO;", "body", "", "Lokhttp3/RequestBody;", "file", "Lokhttp3/MultipartBody$Part;", "(Ljava/util/Map;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createMoim", "request", "Lcom/moondroid/damoim/data/model/request/CreateMoimRequest;", "(Lcom/moondroid/damoim/data/model/request/CreateMoimRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFavor", "", "id", "title", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGroupList", "", "type", "Lcom/moondroid/damoim/common/GroupType;", "(Ljava/lang/String;Lcom/moondroid/damoim/common/GroupType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMembers", "Lcom/moondroid/damoim/data/model/dto/ProfileDTO;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMoimMember", "joinMembers", "getMoims", "Lcom/moondroid/damoim/data/model/dto/MoimItemDTO;", "getSalt", "Lcom/moondroid/damoim/data/model/request/SaltRequest;", "(Lcom/moondroid/damoim/data/model/request/SaltRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinGroup", "joinMoim", "date", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveRecent", "lastTime", "setFavor", "active", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signIn", "Lcom/moondroid/damoim/data/model/entity/ProfileEntity;", "Lcom/moondroid/damoim/data/model/request/SignInRequest;", "(Lcom/moondroid/damoim/data/model/request/SignInRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signUp", "Lcom/moondroid/damoim/data/model/request/SignUpRequest;", "(Lcom/moondroid/damoim/data/model/request/SignUpRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "socialSign", "Lcom/moondroid/damoim/data/model/request/SocialSignRequest;", "(Lcom/moondroid/damoim/data/model/request/SocialSignRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateGroup", "thumb", "image", "(Ljava/util/Map;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateInterest", "interest", "updateProfile", "updateToken", "token", "data_debug"})
public abstract interface RemoteDataSource {
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object checkAppVersion(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, int versionCode, @org.jetbrains.annotations.NotNull
    java.lang.String versionName, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSalt(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SaltRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<java.lang.String>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object signUp(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SignUpRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.entity.ProfileEntity>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object signIn(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SignInRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.entity.ProfileEntity>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object socialSign(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SocialSignRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.entity.ProfileEntity>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateToken(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateInterest(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String interest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateProfile(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends okhttp3.RequestBody> body, @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part thumb, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.entity.ProfileEntity>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getGroupList(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.common.GroupType type, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.data.model.dto.GroupItemDTO>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createGroup(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends okhttp3.RequestBody> body, @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part file, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.dto.GroupItemDTO>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateGroup(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends okhttp3.RequestBody> body, @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part thumb, @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part image, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.dto.GroupItemDTO>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMembers(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.data.model.dto.ProfileDTO>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveRecent(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String lastTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object joinGroup(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getFavor(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<java.lang.Boolean>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setFavor(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, boolean active, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createMoim(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.CreateMoimRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<kotlin.Unit>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMoims(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.data.model.dto.MoimItemDTO>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMoimMember(@org.jetbrains.annotations.NotNull
    java.lang.String joinMembers, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.data.model.dto.ProfileDTO>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object joinMoim(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.domain.model.status.ApiResult<com.moondroid.damoim.data.model.dto.MoimItemDTO>> $completion);
}