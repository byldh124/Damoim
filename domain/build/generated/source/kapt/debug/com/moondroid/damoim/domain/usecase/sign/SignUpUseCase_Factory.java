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
public final class SignUpUseCase_Factory implements Factory<SignUpUseCase> {
  private final Provider<SignRepository> repositoryProvider;

  public SignUpUseCase_Factory(Provider<SignRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SignUpUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SignUpUseCase_Factory create(Provider<SignRepository> repositoryProvider) {
    return new SignUpUseCase_Factory(repositoryProvider);
  }

  public static SignUpUseCase newInstance(SignRepository repository) {
    return new SignUpUseCase(repository);
  }
}
