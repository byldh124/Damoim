package com.moondroid.damoim.common;

import android.content.Context;
import android.content.SharedPreferences;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0017B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\nH\u0002J\u001a\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u0004H\u0002J\u000e\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\nJ\u0018\u0010\u0012\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\nH\u0002J\u0018\u0010\u0014\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J\u000e\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/moondroid/damoim/common/Preferences;", "", "()V", "PREF_NAME", "", "preferences", "Landroid/content/SharedPreferences;", "clear", "", "getBoolean", "", "key", "defVal", "getString", "init", "context", "Landroid/content/Context;", "isAutoSign", "putBoolean", "value", "putString", "setAutoSign", "auto", "PrefsKey", "common_debug"})
public final class Preferences {
    private static android.content.SharedPreferences preferences;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREF_NAME = "sehan_pref";
    @org.jetbrains.annotations.NotNull
    public static final com.moondroid.damoim.common.Preferences INSTANCE = null;
    
    private Preferences() {
        super();
    }
    
    public final void init(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
    
    private final java.lang.String getString(java.lang.String key, java.lang.String defVal) {
        return null;
    }
    
    private final void putString(java.lang.String key, java.lang.String value) {
    }
    
    private final boolean getBoolean(java.lang.String key, boolean defVal) {
        return false;
    }
    
    private final void putBoolean(java.lang.String key, boolean value) {
    }
    
    public final void setAutoSign(boolean auto) {
    }
    
    public final boolean isAutoSign() {
        return false;
    }
    
    public final void clear() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/moondroid/damoim/common/Preferences$PrefsKey;", "", "()V", "AUTO_SIGN", "", "common_debug"})
    public static final class PrefsKey {
        @org.jetbrains.annotations.NotNull
        public static final java.lang.String AUTO_SIGN = "AUTO_SIGN";
        @org.jetbrains.annotations.NotNull
        public static final com.moondroid.damoim.common.Preferences.PrefsKey INSTANCE = null;
        
        private PrefsKey() {
            super();
        }
    }
}