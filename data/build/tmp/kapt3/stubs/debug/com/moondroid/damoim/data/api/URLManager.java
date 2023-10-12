package com.moondroid.damoim.data.api;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001a\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/moondroid/damoim/data/api/URLManager;", "", "()V", "BASE_URL", "", "BLOCK", "CREATE_GROUP", "CREATE_MOIM", "ChECK_APP_VERSION", "GET_FAVOR", "GET_FAVORITE", "GET_GROUP", "GET_MEMBER", "GET_MOIM", "GET_MOIM_MEMBER", "GET_MY_GROUP", "GET_RECENT", "JOIN", "JOIN_INTO_MOIM", "REPORT", "SALT", "SAVE_FAVOR", "SAVE_RECENT", "SIGN_IN", "SIGN_IN_SOCIAL", "SIGN_UP", "UPDATE_GROUP", "UPDATE_INTEREST", "UPDATE_PROFILE", "UPDATE_TOKEN", "data_debug"})
public final class URLManager {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String BASE_URL = "http://moondroid.dothome.co.kr/damoim/";
    
    /**
     * 앱 기능 관련
     */
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ChECK_APP_VERSION = "app/checkVersion.php";
    
    /**
     * 그룹 정보 관련
     */
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GET_GROUP = "group/group.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GET_MEMBER = "group/member.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GET_MY_GROUP = "group/myGroup.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GET_FAVORITE = "group/favorite.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GET_RECENT = "group/recent.php";
    
    /**
     * 모임 관련
     */
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GET_MOIM = "moim/moim.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CREATE_MOIM = "moim/create.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GET_MOIM_MEMBER = "moim/member.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String JOIN_INTO_MOIM = "moim/join.php";
    
    /**
     * 유저-그룹 관련
     */
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String SAVE_RECENT = "user/updateRecent.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String SAVE_FAVOR = "user/updateFavor.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String JOIN = "user/join.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GET_FAVOR = "user/favor.php";
    
    /**
     * 그룹 생성 수정
     */
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CREATE_GROUP = "group/create.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String UPDATE_GROUP = "group/update.php";
    
    /**
     * 회원가입 로그인 관련
     */
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String SIGN_IN = "sign/signIn.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String SIGN_UP = "sign/signUp.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String SIGN_IN_SOCIAL = "sign/signInKakao.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String SALT = "sign/salt.php";
    
    /**
     * 유저 정보 관련
     */
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String UPDATE_TOKEN = "user/updateToken.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String UPDATE_PROFILE = "user/updateProfile.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String UPDATE_INTEREST = "user/updateInterest.php";
    
    /**
     * 차단, 신고
     */
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String BLOCK = "app/block.php";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String REPORT = "app/report.php";
    @org.jetbrains.annotations.NotNull
    public static final com.moondroid.damoim.data.api.URLManager INSTANCE = null;
    
    private URLManager() {
        super();
    }
}