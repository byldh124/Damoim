package com.moondroid.damoim.domain.usecase.app;

import com.moondroid.damoim.domain.repository.AppRepository;
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
public final class VersionUseCase_Factory implements Factory<VersionUseCase> {
  private final Provider<AppRepository> appRepositoryProvider;

  public VersionUseCase_Factory(Provider<AppRepository> appRepositoryProvider) {
    this.appRepositoryProvider = appRepositoryProvider;
  }

  @Override
  public VersionUseCase get() {
    return newInstance(appRepositoryProvider.get());
  }

  public static VersionUseCase_Factory create(Provider<AppRepository> appRepositoryProvider) {
    return new VersionUseCase_Factory(appRepositoryProvider);
  }

  public static VersionUseCase newInstance(AppRepository appRepository) {
    return new VersionUseCase(appRepository);
  }
}
