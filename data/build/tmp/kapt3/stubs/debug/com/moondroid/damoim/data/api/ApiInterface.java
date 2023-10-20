package com.moondroid.damoim.data.api;

import com.moondroid.damoim.common.RequestParam;
import com.moondroid.damoim.data.api.response.ApiStatus;
import com.moondroid.damoim.data.model.request.CreateMoimRequest;
import com.moondroid.damoim.data.model.request.SaltRequest;
import com.moondroid.damoim.data.model.request.SignInRequest;
import com.moondroid.damoim.data.model.request.SignUpRequest;
import com.moondroid.damoim.data.model.request.SocialSignRequest;
import com.moondroid.damoim.data.model.request.UpdateTokenRequest;
import com.moondroid.damoim.data.model.response.FavorResponse;
import com.moondroid.damoim.data.model.response.GroupListResponse;
import com.moondroid.damoim.data.model.response.GroupResponse;
import com.moondroid.damoim.data.model.response.MemberResponse;
import com.moondroid.damoim.data.model.response.MoimListResponse;
import com.moondroid.damoim.data.model.response.MoimResponse;
import com.moondroid.damoim.data.model.response.ProfileResponse;
import com.moondroid.damoim.data.model.response.SimpleResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J+\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ5\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\n\u001a\u00020\u00062\b\b\u0001\u0010\u000b\u001a\u00020\f2\b\b\u0001\u0010\r\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ9\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\u0014\b\u0001\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00130\u00122\n\b\u0001\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J!\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0011\u001a\u00020\u0018H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ+\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u001f\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ!\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J!\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u00032\b\b\u0001\u0010\u001f\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J!\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u00032\b\b\u0001\u0010\u001f\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J!\u0010&\u001a\b\u0012\u0004\u0012\u00020#0\u00032\b\b\u0001\u0010\'\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J!\u0010(\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010)\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J!\u0010*\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J!\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0011\u001a\u00020,H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010-J+\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u001f\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ5\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u001f\u001a\u00020\u00062\b\b\u0001\u00101\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00102J+\u00103\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u00104\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ5\u00105\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u001f\u001a\u00020\u00062\b\b\u0001\u00106\u001a\u000207H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00108J5\u00109\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u001f\u001a\u00020\u00062\b\b\u0001\u0010:\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00102J!\u0010;\u001a\b\u0012\u0004\u0012\u00020<0\u00032\b\b\u0001\u0010\u0011\u001a\u00020=H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010>J!\u0010?\u001a\b\u0012\u0004\u0012\u00020<0\u00032\b\b\u0001\u0010\u0011\u001a\u00020@H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010AJ!\u0010B\u001a\b\u0012\u0004\u0012\u00020<0\u00032\b\b\u0001\u0010\u0011\u001a\u00020CH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010DJE\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\u0014\b\u0001\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00130\u00122\n\b\u0001\u0010F\u001a\u0004\u0018\u00010\u00152\n\b\u0001\u0010G\u001a\u0004\u0018\u00010\u0015H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010HJ+\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010J\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ9\u0010K\u001a\b\u0012\u0004\u0012\u00020<0\u00032\u0014\b\u0001\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00130\u00122\n\b\u0001\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J!\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010M\u001a\u00020NH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010O\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006P"}, d2 = {"Lcom/moondroid/damoim/data/api/ApiInterface;", "", "blockUser", "Lcom/moondroid/damoim/data/api/response/ApiStatus;", "Lcom/moondroid/damoim/data/model/response/SimpleResponse;", "id", "", "blockId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkAppVersion", "packageName", "versionCode", "", "versionName", "(Ljava/lang/String;ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createGroup", "Lcom/moondroid/damoim/data/model/response/GroupResponse;", "body", "", "Lokhttp3/RequestBody;", "file", "Lokhttp3/MultipartBody$Part;", "(Ljava/util/Map;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createMoim", "Lcom/moondroid/damoim/data/model/request/CreateMoimRequest;", "(Lcom/moondroid/damoim/data/model/request/CreateMoimRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllGroups", "Lcom/moondroid/damoim/data/model/response/GroupListResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFavor", "Lcom/moondroid/damoim/data/model/response/FavorResponse;", "title", "getFavoriteGroups", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMembers", "Lcom/moondroid/damoim/data/model/response/MemberResponse;", "getMoim", "Lcom/moondroid/damoim/data/model/response/MoimListResponse;", "getMoimMember", "joinMember", "getMyGroups", "userId", "getRecentGroups", "getSalt", "Lcom/moondroid/damoim/data/model/request/SaltRequest;", "(Lcom/moondroid/damoim/data/model/request/SaltRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "join", "joinInMoim", "Lcom/moondroid/damoim/data/model/response/MoimResponse;", "date", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reportUser", "msg", "saveFavor", "active", "", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveRecent", "lastTime", "signIn", "Lcom/moondroid/damoim/data/model/response/ProfileResponse;", "Lcom/moondroid/damoim/data/model/request/SignInRequest;", "(Lcom/moondroid/damoim/data/model/request/SignInRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInSocial", "Lcom/moondroid/damoim/data/model/request/SocialSignRequest;", "(Lcom/moondroid/damoim/data/model/request/SocialSignRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signUp", "Lcom/moondroid/damoim/data/model/request/SignUpRequest;", "(Lcom/moondroid/damoim/data/model/request/SignUpRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateGroup", "thumb", "image", "(Ljava/util/Map;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateInterest", "interest", "updateProfile", "updateToken", "updateTokenRequest", "Lcom/moondroid/damoim/data/model/request/UpdateTokenRequest;", "(Lcom/moondroid/damoim/data/model/request/UpdateTokenRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public abstract interface ApiInterface {
    
    /**
     * 버전 체크
     */
    @retrofit2.http.GET(value = "app/checkVersion.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object checkAppVersion(@retrofit2.http.Query(value = "packageName")
    @org.jetbrains.annotations.NotNull
    java.lang.String packageName, @retrofit2.http.Query(value = "versionCode")
    int versionCode, @retrofit2.http.Query(value = "versionName")
    @org.jetbrains.annotations.NotNull
    java.lang.String versionName, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.SimpleResponse>> $completion);
    
    @retrofit2.http.POST(value = "sign/signIn.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object signIn(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SignInRequest body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.ProfileResponse>> $completion);
    
    @retrofit2.http.POST(value = "sign/salt.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSalt(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SaltRequest body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.SimpleResponse>> $completion);
    
    @retrofit2.http.POST(value = "sign/signUp.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object signUp(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SignUpRequest body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.ProfileResponse>> $completion);
    
    @retrofit2.http.POST(value = "sign/signInKakao.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object signInSocial(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.SocialSignRequest body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.ProfileResponse>> $completion);
    
    @retrofit2.http.POST(value = "user/updateToken.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateToken(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.UpdateTokenRequest updateTokenRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.SimpleResponse>> $completion);
    
    @retrofit2.http.Multipart
    @retrofit2.http.POST(value = "user/updateProfile.php")
    @kotlin.jvm.JvmSuppressWildcards
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateProfile(@retrofit2.http.PartMap
    @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, okhttp3.RequestBody> body, @retrofit2.http.Part
    @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part file, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.ProfileResponse>> $completion);
    
    @retrofit2.http.GET(value = "user/updateInterest.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateInterest(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String id, @retrofit2.http.Query(value = "interest")
    @org.jetbrains.annotations.NotNull
    java.lang.String interest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.SimpleResponse>> $completion);
    
    @retrofit2.http.GET(value = "group/group.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getAllGroups(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.GroupListResponse>> $completion);
    
    @retrofit2.http.GET(value = "group/myGroup.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMyGroups(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.GroupListResponse>> $completion);
    
    @retrofit2.http.GET(value = "group/favorite.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getFavoriteGroups(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.GroupListResponse>> $completion);
    
    @retrofit2.http.GET(value = "group/recent.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getRecentGroups(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.GroupListResponse>> $completion);
    
    @retrofit2.http.GET(value = "group/member.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMembers(@retrofit2.http.Query(value = "title")
    @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.MemberResponse>> $completion);
    
    @retrofit2.http.GET(value = "moim/moim.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMoim(@retrofit2.http.Query(value = "title")
    @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.MoimListResponse>> $completion);
    
    @retrofit2.http.POST(value = "moim/create.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createMoim(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.model.request.CreateMoimRequest body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.SimpleResponse>> $completion);
    
    @retrofit2.http.Multipart
    @retrofit2.http.POST(value = "group/create.php")
    @kotlin.jvm.JvmSuppressWildcards
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createGroup(@retrofit2.http.PartMap
    @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, okhttp3.RequestBody> body, @retrofit2.http.Part
    @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part file, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.GroupResponse>> $completion);
    
    @retrofit2.http.Multipart
    @retrofit2.http.POST(value = "group/update.php")
    @kotlin.jvm.JvmSuppressWildcards
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateGroup(@retrofit2.http.PartMap
    @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, okhttp3.RequestBody> body, @retrofit2.http.Part
    @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part thumb, @retrofit2.http.Part
    @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part image, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.GroupResponse>> $completion);
    
    @retrofit2.http.GET(value = "user/updateRecent.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveRecent(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String id, @retrofit2.http.Query(value = "title")
    @org.jetbrains.annotations.NotNull
    java.lang.String title, @retrofit2.http.Query(value = "lastTime")
    @org.jetbrains.annotations.NotNull
    java.lang.String lastTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.SimpleResponse>> $completion);
    
    @retrofit2.http.GET(value = "user/updateFavor.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveFavor(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String id, @retrofit2.http.Query(value = "title")
    @org.jetbrains.annotations.NotNull
    java.lang.String title, @retrofit2.http.Query(value = "active")
    boolean active, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.SimpleResponse>> $completion);
    
    @retrofit2.http.GET(value = "user/join.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object join(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String id, @retrofit2.http.Query(value = "title")
    @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.SimpleResponse>> $completion);
    
    @retrofit2.http.GET(value = "user/favor.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getFavor(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String id, @retrofit2.http.Query(value = "title")
    @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.FavorResponse>> $completion);
    
    @retrofit2.http.GET(value = "moim/member.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMoimMember(@retrofit2.http.Query(value = "joinMember")
    @org.jetbrains.annotations.NotNull
    java.lang.String joinMember, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.MemberResponse>> $completion);
    
    @retrofit2.http.GET(value = "moim/join.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object joinInMoim(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String id, @retrofit2.http.Query(value = "title")
    @org.jetbrains.annotations.NotNull
    java.lang.String title, @retrofit2.http.Query(value = "date")
    @org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.MoimResponse>> $completion);
    
    @retrofit2.http.GET(value = "app/block.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object blockUser(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String id, @retrofit2.http.Query(value = "blockId")
    @org.jetbrains.annotations.NotNull
    java.lang.String blockId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.SimpleResponse>> $completion);
    
    @retrofit2.http.GET(value = "app/report.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object reportUser(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String id, @retrofit2.http.Query(value = "message")
    @org.jetbrains.annotations.NotNull
    java.lang.String msg, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.moondroid.damoim.data.api.response.ApiStatus<com.moondroid.damoim.data.model.response.SimpleResponse>> $completion);
}