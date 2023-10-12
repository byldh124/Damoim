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
public final class SaveRecentUseCase_Factory implements Factory<SaveRecentUseCase> {
  private final Provider<GroupRepository> repositoryProvider;

  public SaveRecentUseCase_Factory(Provider<GroupRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveRecentUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveRecentUseCase_Factory create(Provider<GroupRepository> repositoryProvider) {
    return new SaveRecentUseCase_Factory(repositoryProvider);
  }

  public static SaveRecentUseCase newInstance(GroupRepository repository) {
    return new SaveRecentUseCase(repository);
  }
}
