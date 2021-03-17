package com.it2go.micro.carfleetservice.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.carfleetservice.generated.domain.Group;
import com.it2go.micro.carfleetservice.generated.domain.Rule;
import com.it2go.micro.carfleetservice.generated.domain.Rule.TypeEnum;
import com.it2go.util.jpa.search.SearchTemplate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 04.03.2021.
 */
@DisplayName("SearchTemplateMapper Tests -")
class SearchTemplateMapperTest {

  @Test
  void map() {
    SearchTemplateMapper searchTemplateMapper = new SearchTemplateMapperImpl();

    com.it2go.micro.carfleetservice.generated.domain.SearchTemplate originTemplate =
        new com.it2go.micro.carfleetservice.generated.domain.SearchTemplate();
    Group group = new Group();
    Rule rule = new Rule();
    rule.setField("testCol");
    rule.setType(TypeEnum.STRING);

    group.setRules(List.of(rule));

    originTemplate.filters(group);

    SearchTemplate searchTemplate = searchTemplateMapper.map(originTemplate);

    assertNotNull(searchTemplate);
    assertEquals(searchTemplate.getFilters().getRules().size(), 1);
  }
}
