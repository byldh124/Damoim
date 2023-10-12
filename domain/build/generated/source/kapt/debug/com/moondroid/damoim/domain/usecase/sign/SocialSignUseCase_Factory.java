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
public final class SocialSignUseCase_Factory implements Factory<SocialSignUseCase> {
  private final Provider<SignRepository> repositoryProvider;

  public SocialSignUseCase_Factory(Provider<SignRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SocialSignUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SocialSignUseCase_Factory create(Provider<SignRepository> repositoryProvider) {
    return new SocialSignUseCase_Factory(repositoryProvider);
  }

  public static SocialSignUseCase newInstance(SignRepository repository) {
    return new SocialSignUseCase(repository);
  }
}
