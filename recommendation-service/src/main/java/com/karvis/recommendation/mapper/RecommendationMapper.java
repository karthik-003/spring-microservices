package com.karvis.recommendation.mapper;

import com.karvis.api.recommendation.Recommendation;
import com.karvis.recommendation.entity.RecommendationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {
    @Mappings({
            @Mapping(target = "serviceAddress",ignore = true)
    })
    Recommendation entityToApi(RecommendationEntity entity);
    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "version",ignore = true)
    })
    RecommendationEntity apiToEntity(Recommendation api);
}
