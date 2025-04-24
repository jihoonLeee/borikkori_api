package borikkori.community.api.domain.post.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import borikkori.community.api.common.enums.CategoryType;
import borikkori.community.api.domain.post.entity.Category;
import borikkori.community.api.domain.post.service.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	@Override
	public Category createCategory(CategoryType categoryType) {
		LocalDateTime now = LocalDateTime.now();
		return new Category(
			null,
			categoryType,
			categoryType.getDescription(),
			true,
			categoryType.getDisplayOrder(),
			now,
			now,
			null,
			new HashSet<>()
		);
	}

	@Override
	public Category createSubCategory(Category parentCategory, CategoryType categoryType) {
		LocalDateTime now = LocalDateTime.now();
		return new Category(
			null,
			categoryType,
			categoryType.getDescription(),
			true,
			categoryType.getDisplayOrder(),
			now,
			now,
			parentCategory,
			new HashSet<>()
		);
	}

	@Override
	public void updateCategory(Category category, CategoryType categoryType) {
		category.updateCategory(categoryType, categoryType.getDescription());
	}
}
