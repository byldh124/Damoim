package com.moondroid.damoim.data.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

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
public final class NetworkModule_ProvideHttpClientFactory implements Factory<OkHttpClient> {
  @Override
  public OkHttpClient get() {
    return provideHttpClient();
  }

  public static NetworkModule_ProvideHttpClientFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OkHttpClient provideHttpClient() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideHttpClient());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideHttpClientFactory INSTANCE = new NetworkModule_ProvideHttpClientFactory();
  }
}
