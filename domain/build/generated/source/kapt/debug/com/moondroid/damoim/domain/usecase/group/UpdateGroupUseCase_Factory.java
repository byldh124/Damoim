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
public final class UpdateGroupUseCase_Factory implements Factory<UpdateGroupUseCase> {
  private final Provider<GroupRepository> repositoryProvider;

  public UpdateGroupUseCase_Factory(Provider<GroupRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateGroupUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateGroupUseCase_Factory create(Provider<GroupRepository> repositoryProvider) {
    return new UpdateGroupUseCase_Factory(repositoryProvider);
  }

  public static UpdateGroupUseCase newInstance(GroupRepository repository) {
    return new UpdateGroupUseCase(repository);
  }
}
