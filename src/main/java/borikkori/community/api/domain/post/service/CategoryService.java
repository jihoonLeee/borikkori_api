package borikkori.community.api.domain.post.service;

import borikkori.community.api.common.enums.CategoryType;
import borikkori.community.api.domain.post.entity.Category;

public interface CategoryService {
	/**
	 * 신규 카테고리 생성
	 */
	Category createCategory(CategoryType categoryType);

	/**
	 * 서브 카테고리 생성
	 */
	Category createSubCategory(Category parentCategory, CategoryType categoryType);

	/**
	 * 기존 카테고리 수정
	 */
	void updateCategory(Category category, CategoryType categoryType);

}
