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
public final class UpdateProfileUseCase_Factory implements Factory<UpdateProfileUseCase> {
  private final Provider<ProfileRepository> repositoryProvider;

  public UpdateProfileUseCase_Factory(Provider<ProfileRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateProfileUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateProfileUseCase_Factory create(
      Provider<ProfileRepository> repositoryProvider) {
    return new UpdateProfileUseCase_Factory(repositoryProvider);
  }

  public static UpdateProfileUseCase newInstance(ProfileRepository repository) {
    return new UpdateProfileUseCase(repository);
  }
}
