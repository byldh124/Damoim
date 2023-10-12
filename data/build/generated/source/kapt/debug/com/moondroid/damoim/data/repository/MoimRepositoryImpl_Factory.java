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
public final class MoimRepositoryImpl_Factory implements Factory<MoimRepositoryImpl> {
  private final Provider<RemoteDataSource> remoteDataSourceProvider;

  public MoimRepositoryImpl_Factory(Provider<RemoteDataSource> remoteDataSourceProvider) {
    this.remoteDataSourceProvider = remoteDataSourceProvider;
  }

  @Override
  public MoimRepositoryImpl get() {
    return newInstance(remoteDataSourceProvider.get());
  }

  public static MoimRepositoryImpl_Factory create(
      Provider<RemoteDataSource> remoteDataSourceProvider) {
    return new MoimRepositoryImpl_Factory(remoteDataSourceProvider);
  }

  public static MoimRepositoryImpl newInstance(RemoteDataSource remoteDataSource) {
    return new MoimRepositoryImpl(remoteDataSource);
  }
}
