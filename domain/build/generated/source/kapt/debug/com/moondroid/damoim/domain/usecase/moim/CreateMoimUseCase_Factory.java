package com.moondroid.damoim.domain.usecase.moim;

import com.moondroid.damoim.domain.repository.MoimRepository;
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
public final class CreateMoimUseCase_Factory implements Factory<CreateMoimUseCase> {
  private final Provider<MoimRepository> repositoryProvider;

  public CreateMoimUseCase_Factory(Provider<MoimRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CreateMoimUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static CreateMoimUseCase_Factory create(Provider<MoimRepository> repositoryProvider) {
    return new CreateMoimUseCase_Factory(repositoryProvider);
  }

  public static CreateMoimUseCase newInstance(MoimRepository repository) {
    return new CreateMoimUseCase(repository);
  }
}
