package com.moondroid.damoim.data.repository;

import com.moondroid.damoim.data.datasource.remote.RemoteDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class GroupRepositoryImpl_Factory implements Factory<GroupRepositoryImpl> {
  private final Provider<RemoteDataSource> remoteDataSourceProvider;

  public GroupRepositoryImpl_Factory(Provider<RemoteDataSource> remoteDataSourceProvider) {
    this.remoteDataSourceProvider = remoteDataSourceProvider;
  }

  @Override
  public GroupRepositoryImpl get() {
    return newInstance(remoteDataSourceProvider.get());
  }

  public static GroupRepositoryImpl_Factory create(
      Provider<RemoteDataSource> remoteDataSourceProvider) {
    return new GroupRepositoryImpl_Factory(remoteDataSourceProvider);
  }

  public static GroupRepositoryImpl newInstance(RemoteDataSource remoteDataSource) {
    return new GroupRepositoryImpl(remoteDataSource);
  }
}
