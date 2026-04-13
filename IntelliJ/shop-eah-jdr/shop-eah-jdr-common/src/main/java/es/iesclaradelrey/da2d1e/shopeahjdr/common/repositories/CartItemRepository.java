package es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUsernameAndProductId(String username, Long productId);

    void deleteByUsernameAndProductId(String username, Long productId);

    void deleteByUsername(String username);

    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.user.username = :username")
    long countDistinctProducts(String username);

    @Query("SELECT COALESCE(SUM(ci.units),0) FROM CartItem ci WHERE ci.user.username = :username")
    long totalUnits(String username);

    @Query("""
        SELECT COALESCE(SUM(ci.units * (ci.product.price * (1 - ci.product.discount / 100.0))),0) 
        FROM CartItem ci
        WHERE ci.user.username = :username
    """)
    double totalPrice(String username);
}
