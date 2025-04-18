package borikkori.community.api.application.domain.post.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import borikkori.community.api.common.enums.CategoryType;
import borikkori.community.api.domain.post.entity.Category;
import borikkori.community.api.domain.post.repository.CategoryRepository;
import borikkori.community.api.domain.post.service.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryUseCaseImpl implements CategoryUseCase {
	private final CategoryRepository categoryRepository;
	private final CategoryService categoryService;

	@Override
	@Transactional(readOnly = true)
	public List<Category> getAllCategories() {
		return categoryRepository.findAllCategory();
	}

	@Override
	@Transactional(readOnly = true)
	public Category getCategoryById(Long id) {
		return categoryRepository.findCategoryById(id);
	}

	@Override
	@Transactional
	public Category createCategory(CategoryType categoryType) {
		// 클라이언트로부터 전달받은 정보를 기반으로 도메인 객체 생성
		Category category = categoryService.createCategory(categoryType);
		return categoryRepository.saveCategory(category);
	}

	@Override
	@Transactional
	public void createSubCategory(Category parentCategory, CategoryType categoryType) {
		// parentCategory가 저장되지 않은 경우 저장
		System.out.println("----11111 ---" + parentCategory.getId());
		System.out.println("----11111 ---" + parentCategory);
		if (parentCategory.getId() == null) {
			parentCategory = categoryRepository.saveCategory(parentCategory);
		}
		System.out.println("----sub ---" + categoryType.getDisplayName());
		Category subCategory = categoryService.createSubCategory(parentCategory, categoryType);
		System.out.println("----sub2 ---" + subCategory.getCategoryType().getDisplayName());
		parentCategory.addSubCategory(subCategory); // 도메인 객체 관계 설정
		categoryRepository.saveCategory(subCategory); // 하위 카테고리 저장
	}

	@Override
	@Transactional
	public Category updateCategory(Long id, CategoryType categoryType) {
		Category category = categoryRepository.findCategoryById(id);
		categoryService.updateCategory(category, categoryType);
		return categoryRepository.saveCategory(category);
	}

	@Override
	@Transactional
	public void deleteCategory(Long id) {
		if (!categoryRepository.existsById(id)) {
			throw new RuntimeException("Category not found with id: " + id);
		}
		categoryRepository.deleteCategoryById(id);
	}
}
