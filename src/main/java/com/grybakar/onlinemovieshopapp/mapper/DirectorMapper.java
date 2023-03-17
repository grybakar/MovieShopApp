package com.grybakar.onlinemovieshopapp.mapper;

import com.grybakar.onlinemovieshopapp.dto.DirectorDto;
import com.grybakar.onlinemovieshopapp.model.Director;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DirectorMapper {


  DirectorDto toDto(Director director);


}
