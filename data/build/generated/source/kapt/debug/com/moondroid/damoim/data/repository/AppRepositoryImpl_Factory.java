package com.moondroid.damoim.data.repository;

import com.moondroid.damoim.data.datasource.remote.RemoteDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppRepositoryImpl_Factory implements Factory<AppRepositoryImpl> {
  private final Provider<RemoteDataSource> remoteDataSourceProvider;

  public AppRepositoryImpl_Factory(Provider<RemoteDataSource> remoteDataSourceProvider) {
    this.remoteDataSourceProvider = remoteDataSourceProvider;
  }

  @Override
  public AppRepositoryImpl get() {
    return newInstance(remoteDataSourceProvider.get());
  }

  public static AppRepositoryImpl_Factory create(
      Provider<RemoteDataSource> remoteDataSourceProvider) {
    return new AppRepositoryImpl_Factory(remoteDataSourceProvider);
  }

  public static AppRepositoryImpl newInstance(RemoteDataSource remoteDataSource) {
    return new AppRepositoryImpl(remoteDataSource);
  }
}
