package borikkori.community.api.application.domain.post.usecase;

import java.util.List;

import borikkori.community.api.common.enums.CategoryType;
import borikkori.community.api.domain.post.entity.Category;

public interface CategoryUseCase {
	List<Category> getAllCategories();

	Category getCategoryById(Long id);

	Category createCategory(CategoryType categoryType);

	void createSubCategory(Category parentCategory, CategoryType categoryType);

	Category updateCategory(Long id, CategoryType categoryType);

	void deleteCategory(Long id);
}
