package borikkori.community.api.adapter.out.persistence.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import borikkori.community.api.adapter.out.persistence.post.entity.CategoryEntity;
import borikkori.community.api.common.enums.CategoryType;

public interface SpringDataCategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
	Optional<CategoryEntity> findByCategoryType(CategoryType categoryType);

	Optional<CategoryEntity> findByParentCategory_IdAndCategoryType(Long parentId, CategoryType categoryType);
}
