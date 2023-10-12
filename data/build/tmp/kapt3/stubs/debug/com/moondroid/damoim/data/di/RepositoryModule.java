package com.moondroid.damoim.data.di;

import com.moondroid.damoim.data.repository.ProfileRepositoryImpl;
import com.moondroid.damoim.domain.repository.AppRepository;
import com.moondroid.damoim.domain.repository.GroupRepository;
import com.moondroid.damoim.domain.repository.ProfileRepository;
import com.moondroid.damoim.domain.repository.SignRepository;
import com.moondroid.damoim.data.repository.AppRepositoryImpl;
import com.moondroid.damoim.data.repository.GroupRepositoryImpl;
import com.moondroid.damoim.data.repository.MoimRepositoryImpl;
import com.moondroid.damoim.data.repository.SignRepositoryImpl;
import com.moondroid.damoim.domain.repository.MoimRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@dagger.Module
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\bH\'J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u000bH\'J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u000eH\'J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u0011H\'\u00a8\u0006\u0012"}, d2 = {"Lcom/moondroid/damoim/data/di/RepositoryModule;", "", "provideAppRepository", "Lcom/moondroid/damoim/domain/repository/AppRepository;", "repository", "Lcom/moondroid/damoim/data/repository/AppRepositoryImpl;", "provideGroupRepository", "Lcom/moondroid/damoim/domain/repository/GroupRepository;", "Lcom/moondroid/damoim/data/repository/GroupRepositoryImpl;", "provideMoimRepository", "Lcom/moondroid/damoim/domain/repository/MoimRepository;", "Lcom/moondroid/damoim/data/repository/MoimRepositoryImpl;", "provideProfileRepository", "Lcom/moondroid/damoim/domain/repository/ProfileRepository;", "Lcom/moondroid/damoim/data/repository/ProfileRepositoryImpl;", "provideSignRepository", "Lcom/moondroid/damoim/domain/repository/SignRepository;", "Lcom/moondroid/damoim/data/repository/SignRepositoryImpl;", "data_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract interface RepositoryModule {
    
    @dagger.Binds
    @org.jetbrains.annotations.NotNull
    public abstract com.moondroid.damoim.domain.repository.GroupRepository provideGroupRepository(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.repository.GroupRepositoryImpl repository);
    
    @dagger.Binds
    @org.jetbrains.annotations.NotNull
    public abstract com.moondroid.damoim.domain.repository.ProfileRepository provideProfileRepository(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.repository.ProfileRepositoryImpl repository);
    
    @dagger.Binds
    @org.jetbrains.annotations.NotNull
    public abstract com.moondroid.damoim.domain.repository.SignRepository provideSignRepository(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.repository.SignRepositoryImpl repository);
    
    @dagger.Binds
    @org.jetbrains.annotations.NotNull
    public abstract com.moondroid.damoim.domain.repository.AppRepository provideAppRepository(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.repository.AppRepositoryImpl repository);
    
    @dagger.Binds
    @org.jetbrains.annotations.NotNull
    public abstract com.moondroid.damoim.domain.repository.MoimRepository provideMoimRepository(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.data.repository.MoimRepositoryImpl repository);
}