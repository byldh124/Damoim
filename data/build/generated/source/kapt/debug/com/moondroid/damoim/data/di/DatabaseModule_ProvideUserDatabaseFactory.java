package com.moondroid.damoim.data.di;

import android.content.Context;
import com.moondroid.damoim.data.datasource.local.LocalDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideUserDatabaseFactory implements Factory<LocalDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideUserDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public LocalDatabase get() {
    return provideUserDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideUserDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideUserDatabaseFactory(contextProvider);
  }

  public static LocalDatabase provideUserDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideUserDatabase(context));
  }
}
