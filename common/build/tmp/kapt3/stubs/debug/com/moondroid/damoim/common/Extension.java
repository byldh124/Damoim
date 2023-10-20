package com.moondroid.damoim.common;

import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.Keep;
import com.moondroid.damoim.common.crashlytics.FBCrash;
import java.io.Serializable;
import java.security.MessageDigest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004J\u0012\u0010\n\u001a\u00020\u000b*\u00020\u00012\u0006\u0010\f\u001a\u00020\u0004J\u0012\u0010\r\u001a\u00020\u000b*\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000fJ(\u0010\u0010\u001a\u0004\u0018\u0001H\u0011\"\n\b\u0000\u0010\u0011\u0018\u0001*\u00020\u0012*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0004H\u0087\b\u00a2\u0006\u0002\u0010\u0015J(\u0010\u0016\u001a\u0004\u0018\u0001H\u0011\"\n\b\u0000\u0010\u0011\u0018\u0001*\u00020\u0017*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0004H\u0087\b\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006\u0019"}, d2 = {"Lcom/moondroid/damoim/common/Extension;", "", "()V", "byteToString", "", "byteArray", "", "hashingPw", "password", "salt", "debug", "", "msg", "logException", "e", "", "parcelable", "T", "Landroid/os/Parcelable;", "Landroid/content/Intent;", "key", "(Landroid/content/Intent;Ljava/lang/String;)Landroid/os/Parcelable;", "serializable", "Ljava/io/Serializable;", "(Landroid/content/Intent;Ljava/lang/String;)Ljava/io/Serializable;", "common_debug"})
public final class Extension {
    @org.jetbrains.annotations.NotNull
    public static final com.moondroid.damoim.common.Extension INSTANCE = null;
    
    private Extension() {
        super();
    }
    
    public final void debug(@org.jetbrains.annotations.NotNull
    java.lang.Object $this$debug, @org.jetbrains.annotations.NotNull
    java.lang.String msg) {
    }
    
    public final void logException(@org.jetbrains.annotations.NotNull
    java.lang.Object $this$logException, @org.jetbrains.annotations.NotNull
    java.lang.Throwable e) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String hashingPw(@org.jetbrains.annotations.NotNull
    java.lang.String password, @org.jetbrains.annotations.NotNull
    java.lang.String salt) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String byteToString(@org.jetbrains.annotations.NotNull
    byte[] byteArray) {
        return null;
    }
}