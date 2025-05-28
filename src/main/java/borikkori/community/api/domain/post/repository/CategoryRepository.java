package borikkori.community.api.domain.post.repository;

import java.util.List;

import borikkori.community.api.common.enums.CategoryType;
import borikkori.community.api.domain.post.entity.Category;

public interface CategoryRepository {
	Category saveCategory(Category category);

	Category findCategoryById(Long id);

	Category findCategoryByName(CategoryType categoryType);

	Category findByTypeAndSubType(CategoryType type, CategoryType subType);

	List<Category> findAllCategory();

	List<Category> findCategoryList(Long categoryId);

	boolean existsById(Long id);

	void deleteCategoryById(Long id);

	long countCategory();
}
