package com.it2go.micro.carfleetservice.mapper;

import org.mapstruct.Mapper;

@Mapper
public interface SearchTemplateMapper {

  com.it2go.util.jpa.search.SearchTemplate map(com.it2go.micro.carfleetservice.generated.domain.SearchTemplate template);
}
