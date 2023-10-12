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
public final class ProfileUseCase_Factory implements Factory<ProfileUseCase> {
  private final Provider<ProfileRepository> repositoryProvider;

  public ProfileUseCase_Factory(Provider<ProfileRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ProfileUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ProfileUseCase_Factory create(Provider<ProfileRepository> repositoryProvider) {
    return new ProfileUseCase_Factory(repositoryProvider);
  }

  public static ProfileUseCase newInstance(ProfileRepository repository) {
    return new ProfileUseCase(repository);
  }
}
