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
public final class GetGroupUseCase_Factory implements Factory<GetGroupUseCase> {
  private final Provider<GroupRepository> repositoryProvider;

  public GetGroupUseCase_Factory(Provider<GroupRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetGroupUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetGroupUseCase_Factory create(Provider<GroupRepository> repositoryProvider) {
    return new GetGroupUseCase_Factory(repositoryProvider);
  }

  public static GetGroupUseCase newInstance(GroupRepository repository) {
    return new GetGroupUseCase(repository);
  }
}
