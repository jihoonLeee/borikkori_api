package borikkori.community.api.adapter.out.persistence.post.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import borikkori.community.api.adapter.out.persistence.post.entity.CategoryEntity;
import borikkori.community.api.adapter.out.persistence.post.mapper.CategoryMapper;
import borikkori.community.api.adapter.out.persistence.post.repository.SpringDataCategoryJpaRepository;
import borikkori.community.api.common.enums.CategoryType;
import borikkori.community.api.domain.post.entity.Category;
import borikkori.community.api.domain.post.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {
	private final SpringDataCategoryJpaRepository categoryJpaRepository;
	private final CategoryMapper categoryMapper;

	@Override
	public Category saveCategory(Category category) {
		System.out.println("Saving category: " + category);
		System.out.println("Category ID: " + category.getId());
		System.out.println("Category Type: " + category.getCategoryType());
		System.out.println(
			"Parent Category: " + (category.getParentCategory() != null ? category.getParentCategory().getId() :
				"null"));
		CategoryEntity categoryEntity = categoryMapper.toEntity(category);
		System.out.println("Mapped Entity: " + categoryEntity);
		System.out.println("Entity Category Type: " + categoryEntity.getCategoryType());
		CategoryEntity savedCategory = categoryJpaRepository.save(categoryEntity);
		return categoryMapper.toDomain(savedCategory);
	}

	@Override
	public Category findCategoryById(Long id) {
		CategoryEntity categoryEntity = categoryJpaRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
		return categoryMapper.toDomain(categoryEntity);
	}

	@Override
	public Category findCategoryByName(CategoryType categoryType) {
		CategoryEntity categoryEntity = categoryJpaRepository.findByCategoryType(categoryType)
			.orElseThrow(() -> new RuntimeException("Category not found with categoryType: " + categoryType));
		return categoryMapper.toDomain(categoryEntity);
	}

	@Override
	public Category findByParentIdAndType(Long parentCategoryId, CategoryType categoryType) {
		CategoryEntity entity = categoryJpaRepository
			.findByParentCategory_IdAndCategoryType(parentCategoryId, categoryType)
			.orElseThrow(() -> new RuntimeException(
				"Sub-category not found for parentId=" + parentCategoryId + " and type=" + categoryType));
		return categoryMapper.toDomain(entity);
	}

	@Override
	public List<Category> findAllCategory() {
		return categoryJpaRepository.findAll().stream()
			.map(categoryMapper::toDomain)
			.collect(Collectors.toList());
	}

	@Override
	public List<Category> findCategoryList(Long categoryId) {
		List<CategoryEntity> categoryEntities = categoryJpaRepository.findAll();
		return categoryEntities.stream()
			.map(categoryMapper::toDomain)
			.collect(Collectors.toList());
	}

	@Override
	public boolean existsById(Long id) {
		return categoryJpaRepository.existsById(id);
	}

	@Override
	public void deleteCategoryById(Long id) {
		categoryJpaRepository.deleteById(id);
	}

	@Override
	public long countCategory() {
		return categoryJpaRepository.count();
	}

}
