package borikkori.community.api.domain.post.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import borikkori.community.api.common.enums.CategoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class Category {
	private Long id;
	private CategoryType categoryType;
	private String description;
	private boolean active;
	private int displayOrder;
	private LocalDateTime regDate;
	private LocalDateTime updDate;

	// 부모 카테고리 (없으면 최상위)
	@Setter
	private Category parentCategory;
	// 자식(서브) 카테고리 목록
	@Setter
	private Set<Category> subCategories;

	public Category(Long id, CategoryType categoryType, String description, boolean active,
		int displayOrder, LocalDateTime regDate, LocalDateTime updDate,
		Category parentCategory, Set<Category> subCategories) {
		this.id = id;
		this.categoryType = categoryType;
		this.description = description;
		this.active = active;
		this.displayOrder = displayOrder;
		this.regDate = regDate;
		this.updDate = updDate;
		this.parentCategory = parentCategory;
		this.subCategories = (subCategories != null) ? subCategories : new HashSet<>();
	}

	public void updateCategory(CategoryType categoryType, String description) {
		this.categoryType = categoryType;
		this.description = description;
		updateModificationDate();
	}

	public void activate() {
		if (!this.active) {
			this.active = true;
			updateModificationDate();
		}
	}

	public void deactivate() {
		if (this.active) {
			this.active = false;
			updateModificationDate();
		}
	}

	public void updateDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
		updateModificationDate();
	}

	private void updateModificationDate() {
		this.updDate = LocalDateTime.now();
	}

	/**
	 * 서브 카테고리를 추가합니다.
	 * 서브 카테고리를 추가하면 해당 카테고리의 부모로 자신을 설정합니다.
	 */
	public void addSubCategory(Category subCategory) {
		if (this.subCategories == null) {
			this.subCategories = new HashSet<>();
		}
		this.subCategories.add(subCategory);
		subCategory.setParentCategory(this);
		updateModificationDate();
	}

	/**
	 * 서브 카테고리를 제거합니다.
	 */
	public void removeSubCategory(Category subCategory) {
		if (this.subCategories != null && this.subCategories.remove(subCategory)) {
			subCategory.setParentCategory(null);
			updateModificationDate();
		}
	}

	/**
	 * 이 카테고리가 서브 카테고리가 없는 리프(leaf) 카테고리인지 확인합니다.
	 */
	public boolean isLeafCategory() {
		return this.subCategories == null || this.subCategories.isEmpty();
	}

}
