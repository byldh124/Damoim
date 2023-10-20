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
public final class CreateGroupUseCase_Factory implements Factory<CreateGroupUseCase> {
  private final Provider<GroupRepository> repositoryProvider;

  public CreateGroupUseCase_Factory(Provider<GroupRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CreateGroupUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static CreateGroupUseCase_Factory create(Provider<GroupRepository> repositoryProvider) {
    return new CreateGroupUseCase_Factory(repositoryProvider);
  }

  public static CreateGroupUseCase newInstance(GroupRepository repository) {
    return new CreateGroupUseCase(repository);
  }
}