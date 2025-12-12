package com.lms.backend.library.mapper;

import com.lms.backend.library.dto.HoldDto;
import com.lms.backend.library.entity.Hold;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HoldMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    Hold toEntity(HoldDto dto);

    HoldDto toDto(Hold entity);
}