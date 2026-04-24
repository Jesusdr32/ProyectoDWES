package es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    // Consulta derivada
    List<Product> findByCategoriesId(Long categoryId, Sort sort);
    // Para las validaciones del nombre del producto
    boolean existsByNameIgnoreCase(String name);
}
