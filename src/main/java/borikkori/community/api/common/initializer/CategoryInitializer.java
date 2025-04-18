package borikkori.community.api.common.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import borikkori.community.api.application.domain.post.usecase.CategoryUseCase;
import borikkori.community.api.common.enums.CategoryType;
import borikkori.community.api.domain.post.entity.Category;
import borikkori.community.api.domain.post.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryInitializer implements CommandLineRunner {
	private final CategoryRepository categoryRepository;
	private final CategoryUseCase categoryUseCase;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if (categoryRepository.countCategory() == 0) {
			Category free = categoryUseCase.createCategory(CategoryType.FREE);
			Category info = categoryUseCase.createCategory(CategoryType.INFO);
			Category beginner = categoryUseCase.createCategory(CategoryType.BEGINNER);
			Category funny = categoryUseCase.createCategory(CategoryType.FUNNY);
			System.out.println("패런츠");
			System.out.println(free.getId());
			System.out.println(free.getCategoryType());
			System.out.println("패런츠");
			categoryUseCase.createSubCategory(free, CategoryType.NOTICE);
			categoryUseCase.createSubCategory(info, CategoryType.NOTICE);
			categoryUseCase.createSubCategory(beginner, CategoryType.NOTICE);
			categoryUseCase.createSubCategory(funny, CategoryType.NOTICE);
		}
	}
}
