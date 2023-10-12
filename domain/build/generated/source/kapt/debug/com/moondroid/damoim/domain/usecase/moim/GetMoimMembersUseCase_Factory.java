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
public final class GetMoimMembersUseCase_Factory implements Factory<GetMoimMembersUseCase> {
  private final Provider<MoimRepository> repositoryProvider;

  public GetMoimMembersUseCase_Factory(Provider<MoimRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetMoimMembersUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetMoimMembersUseCase_Factory create(Provider<MoimRepository> repositoryProvider) {
    return new GetMoimMembersUseCase_Factory(repositoryProvider);
  }

  public static GetMoimMembersUseCase newInstance(MoimRepository repository) {
    return new GetMoimMembersUseCase(repository);
  }
}
