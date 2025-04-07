package borikkori.community.api.adapter.out.persistence.post.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import borikkori.community.api.adapter.out.persistence.post.entity.CategoryEntity;
import borikkori.community.api.domain.post.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	// 엔티티 -> 도메인
	Category toDomain(CategoryEntity entity);

	// 도메인 -> 엔티티: 순환 참조로 인한 재귀를 방지하기 위해 무시함.
	@Mapping(target = "parentCategory", ignore = true)
	@Mapping(target = "subCategories", ignore = true)
	@Mapping(target = "id", source = "id")
	CategoryEntity toEntity(Category domain);
}
