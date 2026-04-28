package es.iesclaradelrey.da2d1e.shopeahjdr.common.specifications;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecifications {

    private ProductSpecifications() {}

    public static Specification<Product> nameOrDescriptionContains(String text) {
        return (root, query, cb) -> {
            if (text == null || text.isBlank()) return null;

            String pattern = "%" + text.toLowerCase() + "%";
            query.distinct(true);

            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    public static Specification<Product> hasMaxPrice(Double maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> hasBrand(Long brandId) {
        return (root, query, cb) ->
        brandId == null ? null : cb.equal(root.get("brand").get("id"), brandId);
    }

    public static Specification<Product> hasBrandLeftJoin(Long brandId) {
        return (root, query, cb) -> {
            if (brandId == null) return null;
            Join<Object, Object> brandJoin = root.join("brand", JoinType.LEFT);
            return cb.equal(brandJoin.get("id"), brandId);
        };
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) ->
                categoryId == null ? null :
                        cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasCategoryLeftJoin(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return null;
            Join<Object, Object> categoryJoin = root.join("category", JoinType.LEFT);
            return cb.equal(categoryJoin.get("id"), categoryId);
        };
    }

    public static Specification<Product> fromCriteria(
            String text,
            Double maxPrice,
            Long brandId,
            Long categoryId
    ) {
        return Specification
                .where(nameOrDescriptionContains(text)
                        .and(hasMaxPrice(maxPrice))
                        .and(hasBrand(brandId))
                        .and(hasCategory(categoryId)));
    }
}
