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
public final class UpdateTokenUseCase_Factory implements Factory<UpdateTokenUseCase> {
  private final Provider<ProfileRepository> repositoryProvider;

  public UpdateTokenUseCase_Factory(Provider<ProfileRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateTokenUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateTokenUseCase_Factory create(Provider<ProfileRepository> repositoryProvider) {
    return new UpdateTokenUseCase_Factory(repositoryProvider);
  }

  public static UpdateTokenUseCase newInstance(ProfileRepository repository) {
    return new UpdateTokenUseCase(repository);
  }
}
