package com.moondroid.damoim.domain.usecase.group;

import com.moondroid.damoim.domain.repository.GroupRepository;
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
public final class GetFavorUseCase_Factory implements Factory<GetFavorUseCase> {
  private final Provider<GroupRepository> repositoryProvider;

  public GetFavorUseCase_Factory(Provider<GroupRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetFavorUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetFavorUseCase_Factory create(Provider<GroupRepository> repositoryProvider) {
    return new GetFavorUseCase_Factory(repositoryProvider);
  }

  public static GetFavorUseCase newInstance(GroupRepository repository) {
    return new GetFavorUseCase(repository);
  }
}
