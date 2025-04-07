package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.CategoryEntity;
import borikkori.community.api.common.enums.CategoryType;
import borikkori.community.api.domain.post.entity.Category;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-05T00:13:11+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toDomain(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Category category = new Category();

        category.setParentCategory( toDomain( entity.getParentCategory() ) );
        category.setSubCategories( categoryEntitySetToCategorySet( entity.getSubCategories() ) );

        return category;
    }

    @Override
    public CategoryEntity toEntity(Category domain) {
        if ( domain == null ) {
            return null;
        }

        Long id = null;
        CategoryType categoryType = null;
        boolean active = false;
        int displayOrder = 0;
        String description = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        id = domain.getId();
        categoryType = domain.getCategoryType();
        active = domain.isActive();
        displayOrder = domain.getDisplayOrder();
        description = domain.getDescription();
        regDate = domain.getRegDate();
        updDate = domain.getUpdDate();

        CategoryEntity parentCategory = null;
        Set<CategoryEntity> subCategories = null;

        CategoryEntity categoryEntity = new CategoryEntity( id, categoryType, active, displayOrder, description, parentCategory, subCategories, regDate, updDate );

        return categoryEntity;
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
