package com.moondroid.damoim.data.datasource.remote;

import com.moondroid.damoim.data.api.ApiInterface;
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
public final class RemoteDataSourceImpl_Factory implements Factory<RemoteDataSourceImpl> {
  private final Provider<ApiInterface> apiProvider;

  public RemoteDataSourceImpl_Factory(Provider<ApiInterface> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public RemoteDataSourceImpl get() {
    return newInstance(apiProvider.get());
  }

  public static RemoteDataSourceImpl_Factory create(Provider<ApiInterface> apiProvider) {
    return new RemoteDataSourceImpl_Factory(apiProvider);
  }

  public static RemoteDataSourceImpl newInstance(ApiInterface api) {
    return new RemoteDataSourceImpl(api);
  }
}
