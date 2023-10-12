package com.moondroid.damoim.domain.usecase.sign;

import com.moondroid.damoim.domain.repository.SignRepository;
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
public final class SaltUseCase_Factory implements Factory<SaltUseCase> {
  private final Provider<SignRepository> repositoryProvider;

  public SaltUseCase_Factory(Provider<SignRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaltUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaltUseCase_Factory create(Provider<SignRepository> repositoryProvider) {
    return new SaltUseCase_Factory(repositoryProvider);
  }

  public static SaltUseCase newInstance(SignRepository repository) {
    return new SaltUseCase(repository);
  }
}
