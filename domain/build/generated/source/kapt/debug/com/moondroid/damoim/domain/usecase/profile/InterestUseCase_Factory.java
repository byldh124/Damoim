package com.moondroid.damoim.domain.usecase.profile;

import com.moondroid.damoim.domain.repository.ProfileRepository;
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
public final class InterestUseCase_Factory implements Factory<InterestUseCase> {
  private final Provider<ProfileRepository> repositoryProvider;

  public InterestUseCase_Factory(Provider<ProfileRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public InterestUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static InterestUseCase_Factory create(Provider<ProfileRepository> repositoryProvider) {
    return new InterestUseCase_Factory(repositoryProvider);
  }

  public static InterestUseCase newInstance(ProfileRepository repository) {
    return new InterestUseCase(repository);
  }
}
