package com.moondroid.damoim.data.mapper

import com.moondroid.damoim.data.model.dto.BaseResponseDTO
import com.moondroid.damoim.data.model.dto.ProfileDTO
import com.moondroid.damoim.data.model.entity.ProfileEntity
import com.moondroid.damoim.domain.model.BaseResponse
import com.moondroid.damoim.domain.model.Profile

object DataMapper {
    fun BaseResponseDTO.toBaseResponse(): BaseResponse = BaseResponse(code, message)

    fun ProfileDTO.toProfileEntity(): ProfileEntity =
        ProfileEntity(id, name, birth, gender, location, interest, thumb, message)

    fun ProfileEntity.toProfile(): Profile = Profile(id, name, birth, gender, location, interest, thumb, message)
}