package com.grybakar.onlinemovieshopapp.mapper;

import com.grybakar.onlinemovieshopapp.dto.ActorDto;
import com.grybakar.onlinemovieshopapp.model.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ActorMapper {

  ActorDto toDto(Actor actor);



}
