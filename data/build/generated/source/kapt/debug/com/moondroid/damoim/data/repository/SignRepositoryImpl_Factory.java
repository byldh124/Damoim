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
public final class SignRepositoryImpl_Factory implements Factory<SignRepositoryImpl> {
  private final Provider<RemoteDataSource> remoteDataSourceProvider;

  private final Provider<ProfileDao> localDataSourceProvider;

  public SignRepositoryImpl_Factory(Provider<RemoteDataSource> remoteDataSourceProvider,
      Provider<ProfileDao> localDataSourceProvider) {
    this.remoteDataSourceProvider = remoteDataSourceProvider;
    this.localDataSourceProvider = localDataSourceProvider;
  }

  @Override
  public SignRepositoryImpl get() {
    return newInstance(remoteDataSourceProvider.get(), localDataSourceProvider.get());
  }

  public static SignRepositoryImpl_Factory create(
      Provider<RemoteDataSource> remoteDataSourceProvider,
      Provider<ProfileDao> localDataSourceProvider) {
    return new SignRepositoryImpl_Factory(remoteDataSourceProvider, localDataSourceProvider);
  }

  public static SignRepositoryImpl newInstance(RemoteDataSource remoteDataSource,
      ProfileDao localDataSource) {
    return new SignRepositoryImpl(remoteDataSource, localDataSource);
  }
}
