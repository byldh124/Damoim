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
public final class GetMoimsUseCase_Factory implements Factory<GetMoimsUseCase> {
  private final Provider<MoimRepository> repositoryProvider;

  public GetMoimsUseCase_Factory(Provider<MoimRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetMoimsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetMoimsUseCase_Factory create(Provider<MoimRepository> repositoryProvider) {
    return new GetMoimsUseCase_Factory(repositoryProvider);
  }

  public static GetMoimsUseCase newInstance(MoimRepository repository) {
    return new GetMoimsUseCase(repository);
  }
}
