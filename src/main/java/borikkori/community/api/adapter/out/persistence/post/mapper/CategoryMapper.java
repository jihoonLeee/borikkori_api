package borikkori.community.api.adapter.out.persistence.post.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import borikkori.community.api.adapter.out.persistence.post.entity.CategoryEntity;
import borikkori.community.api.domain.post.entity.Category;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface CategoryMapper {

	@Mapping(target = "id", source = "id")
	Category toDomain(CategoryEntity entity);

	@Mapping(target = "subCategories", ignore = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "categoryType", source = "categoryType")
	CategoryEntity toEntity(Category domain);
}
