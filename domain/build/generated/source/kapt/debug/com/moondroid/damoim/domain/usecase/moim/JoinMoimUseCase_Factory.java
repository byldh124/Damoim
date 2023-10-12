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
public final class JoinMoimUseCase_Factory implements Factory<JoinMoimUseCase> {
  private final Provider<MoimRepository> repositoryProvider;

  public JoinMoimUseCase_Factory(Provider<MoimRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public JoinMoimUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static JoinMoimUseCase_Factory create(Provider<MoimRepository> repositoryProvider) {
    return new JoinMoimUseCase_Factory(repositoryProvider);
  }

  public static JoinMoimUseCase newInstance(MoimRepository repository) {
    return new JoinMoimUseCase(repository);
  }
}
