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
public final class SetFavorUseCase_Factory implements Factory<SetFavorUseCase> {
  private final Provider<GroupRepository> repositoryProvider;

  public SetFavorUseCase_Factory(Provider<GroupRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SetFavorUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SetFavorUseCase_Factory create(Provider<GroupRepository> repositoryProvider) {
    return new SetFavorUseCase_Factory(repositoryProvider);
  }

  public static SetFavorUseCase newInstance(GroupRepository repository) {
    return new SetFavorUseCase(repository);
  }
}
