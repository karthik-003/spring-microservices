package com.karvis.review.mapper;


import com.karvis.api.review.Review;
import com.karvis.review.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReviewMapper {


    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "version",ignore=true)
    })
    ReviewEntity apiToEntity(Review api);

    @Mappings({
            @Mapping(target = "serviceAddress",ignore = true)
    })
    Review entityToApi(ReviewEntity entity);
}
