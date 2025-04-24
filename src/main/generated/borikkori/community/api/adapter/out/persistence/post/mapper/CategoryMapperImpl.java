package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.CategoryEntity;
import borikkori.community.api.domain.post.entity.Category;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T22:44:06+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toDomain(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( entity.getId() );
        category.categoryType( entity.getCategoryType() );
        category.description( entity.getDescription() );
        category.active( entity.isActive() );
        category.displayOrder( entity.getDisplayOrder() );
        category.regDate( entity.getRegDate() );
        category.updDate( entity.getUpdDate() );
        category.parentCategory( toDomain( entity.getParentCategory() ) );
        category.subCategories( categoryEntitySetToCategorySet( entity.getSubCategories() ) );

        return category.build();
    }

    @Override
    public CategoryEntity toEntity(Category domain) {
        if ( domain == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        categoryEntity.id( domain.getId() );
        categoryEntity.categoryType( domain.getCategoryType() );
        categoryEntity.active( domain.isActive() );
        categoryEntity.displayOrder( domain.getDisplayOrder() );
        categoryEntity.description( domain.getDescription() );
        categoryEntity.parentCategory( toEntity( domain.getParentCategory() ) );
        categoryEntity.regDate( domain.getRegDate() );
        categoryEntity.updDate( domain.getUpdDate() );

        return categoryEntity.build();
    }

    protected Set<Category> categoryEntitySetToCategorySet(Set<CategoryEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<Category> set1 = new LinkedHashSet<Category>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CategoryEntity categoryEntity : set ) {
            set1.add( toDomain( categoryEntity ) );
        }

        return set1;
    }
}
