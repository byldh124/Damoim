package com.moondroid.damoim.data.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import retrofit2.converter.gson.GsonConverterFactory;

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
public final class NetworkModule_ProvideConverterFactoryFactory implements Factory<GsonConverterFactory> {
  @Override
  public GsonConverterFactory get() {
    return provideConverterFactory();
  }

  public static NetworkModule_ProvideConverterFactoryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GsonConverterFactory provideConverterFactory() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideConverterFactory());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideConverterFactoryFactory INSTANCE = new NetworkModule_ProvideConverterFactoryFactory();
  }
}
