package com.moondroid.damoim.data.mapper;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.moondroid.damoim.domain.model.Profile;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0007H\u0007J\u001a\u0010\n\u001a\u0004\u0018\u00010\u00072\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004H\u0007J\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0005H\u0007\u00a8\u0006\r"}, d2 = {"Lcom/moondroid/damoim/data/mapper/DataTypeConverter;", "", "()V", "jsonToList", "", "Lcom/moondroid/damoim/domain/model/Profile;", "value", "", "jsonToProfile", "json", "listToJson", "profileToJsonString", "profile", "data_debug"})
@androidx.room.ProvidedTypeConverter
public final class DataTypeConverter {
    
    public DataTypeConverter() {
        super();
    }
    
    @androidx.room.TypeConverter
    @org.jetbrains.annotations.NotNull
    public final java.lang.String profileToJsonString(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.domain.model.Profile profile) {
        return null;
    }
    
    @androidx.room.TypeConverter
    @org.jetbrains.annotations.NotNull
    public final com.moondroid.damoim.domain.model.Profile jsonToProfile(@org.jetbrains.annotations.NotNull
    java.lang.String json) {
        return null;
    }
    
    @androidx.room.TypeConverter
    @org.jetbrains.annotations.Nullable
    public final java.lang.String listToJson(@org.jetbrains.annotations.Nullable
    java.util.List<com.moondroid.damoim.domain.model.Profile> value) {
        return null;
    }
    
    @androidx.room.TypeConverter
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.moondroid.damoim.domain.model.Profile> jsonToList(@org.jetbrains.annotations.NotNull
    java.lang.String value) {
        return null;
    }
}