package com.moondroid.damoim.data.model.response;

import com.google.gson.annotations.SerializedName;
import com.moondroid.damoim.common.RequestParam;
import com.moondroid.damoim.data.model.dto.ProfileDTO;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J/\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0019"}, d2 = {"Lcom/moondroid/damoim/data/model/response/MemberResponse;", "", "code", "", "message", "", "result", "", "Lcom/moondroid/damoim/data/model/dto/ProfileDTO;", "(ILjava/lang/String;Ljava/util/List;)V", "getCode", "()I", "getMessage", "()Ljava/lang/String;", "getResult", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "data_debug"})
public final class MemberResponse {
    @com.google.gson.annotations.SerializedName(value = "code")
    private final int code = 0;
    @com.google.gson.annotations.SerializedName(value = "message")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String message = null;
    @com.google.gson.annotations.SerializedName(value = "result")
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.moondroid.damoim.data.model.dto.ProfileDTO> result = null;
    
    public MemberResponse(int code, @org.jetbrains.annotations.Nullable
    java.lang.String message, @org.jetbrains.annotations.NotNull
    java.util.List<com.moondroid.damoim.data.model.dto.ProfileDTO> result) {
        super();
    }
    
    public final int getCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.moondroid.damoim.data.model.dto.ProfileDTO> getResult() {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.moondroid.damoim.data.model.dto.ProfileDTO> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.moondroid.damoim.data.model.response.MemberResponse copy(int code, @org.jetbrains.annotations.Nullable
    java.lang.String message, @org.jetbrains.annotations.NotNull
    java.util.List<com.moondroid.damoim.data.model.dto.ProfileDTO> result) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}