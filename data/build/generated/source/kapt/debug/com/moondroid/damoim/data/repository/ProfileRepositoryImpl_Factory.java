package com.moondroid.damoim.data.repository;

import com.moondroid.damoim.data.datasource.remote.RemoteDataSource;
import com.moondroid.damoim.data.model.dao.ProfileDao;
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
public final class ProfileRepositoryImpl_Factory implements Factory<ProfileRepositoryImpl> {
  private final Provider<RemoteDataSource> remoteDataSourceProvider;

  private final Provider<ProfileDao> localDataSourceProvider;

  public ProfileRepositoryImpl_Factory(Provider<RemoteDataSource> remoteDataSourceProvider,
      Provider<ProfileDao> localDataSourceProvider) {
    this.remoteDataSourceProvider = remoteDataSourceProvider;
    this.localDataSourceProvider = localDataSourceProvider;
  }

  @Override
  public ProfileRepositoryImpl get() {
    return newInstance(remoteDataSourceProvider.get(), localDataSourceProvider.get());
  }

  public static ProfileRepositoryImpl_Factory create(
      Provider<RemoteDataSource> remoteDataSourceProvider,
      Provider<ProfileDao> localDataSourceProvider) {
    return new ProfileRepositoryImpl_Factory(remoteDataSourceProvider, localDataSourceProvider);
  }

  public static ProfileRepositoryImpl newInstance(RemoteDataSource remoteDataSource,
      ProfileDao localDataSource) {
    return new ProfileRepositoryImpl(remoteDataSource, localDataSource);
  }
}
