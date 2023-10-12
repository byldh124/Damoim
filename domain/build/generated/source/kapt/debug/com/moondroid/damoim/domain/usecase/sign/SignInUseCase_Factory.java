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
public final class SignInUseCase_Factory implements Factory<SignInUseCase> {
  private final Provider<SignRepository> repositoryProvider;

  public SignInUseCase_Factory(Provider<SignRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SignInUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SignInUseCase_Factory create(Provider<SignRepository> repositoryProvider) {
    return new SignInUseCase_Factory(repositoryProvider);
  }

  public static SignInUseCase newInstance(SignRepository repository) {
    return new SignInUseCase(repository);
  }
}
