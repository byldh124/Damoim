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
public final class JoinGroupUseCase_Factory implements Factory<JoinGroupUseCase> {
  private final Provider<GroupRepository> repositoryProvider;

  public JoinGroupUseCase_Factory(Provider<GroupRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public JoinGroupUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static JoinGroupUseCase_Factory create(Provider<GroupRepository> repositoryProvider) {
    return new JoinGroupUseCase_Factory(repositoryProvider);
  }

  public static JoinGroupUseCase newInstance(GroupRepository repository) {
    return new JoinGroupUseCase(repository);
  }
}
