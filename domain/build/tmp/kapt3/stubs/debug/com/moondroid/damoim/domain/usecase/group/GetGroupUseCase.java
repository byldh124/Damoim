package com.moondroid.damoim.domain.usecase.group;

import com.moondroid.damoim.common.GroupType;
import com.moondroid.damoim.domain.repository.GroupRepository;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J3\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00070\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0086B\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000f"}, d2 = {"Lcom/moondroid/damoim/domain/usecase/group/GetGroupUseCase;", "", "repository", "Lcom/moondroid/damoim/domain/repository/GroupRepository;", "(Lcom/moondroid/damoim/domain/repository/GroupRepository;)V", "invoke", "Lkotlinx/coroutines/flow/Flow;", "Lcom/moondroid/damoim/domain/model/status/ApiResult;", "", "Lcom/moondroid/damoim/domain/model/GroupItem;", "id", "", "type", "Lcom/moondroid/damoim/common/GroupType;", "(Ljava/lang/String;Lcom/moondroid/damoim/common/GroupType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public final class GetGroupUseCase {
    @org.jetbrains.annotations.NotNull
    private final com.moondroid.damoim.domain.repository.GroupRepository repository = null;
    
    @javax.inject.Inject
    public GetGroupUseCase(@org.jetbrains.annotations.NotNull
    com.moondroid.damoim.domain.repository.GroupRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object invoke(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    com.moondroid.damoim.common.GroupType type, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.moondroid.damoim.domain.model.status.ApiResult<? extends java.util.List<com.moondroid.damoim.domain.model.GroupItem>>>> $completion) {
        return null;
    }
}