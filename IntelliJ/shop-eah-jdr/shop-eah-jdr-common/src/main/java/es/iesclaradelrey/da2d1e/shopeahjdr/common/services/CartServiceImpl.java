package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.AddProductToCartDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.CartItemDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.CartResponseDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.CartItem;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.exceptions.InsufficientStockException;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.exceptions.InvalidUnitsException;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.exceptions.ProductNotFoundException;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.AppUserRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.CartItemRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final AppUserRepository userRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository,
                           ProductRepository productRepository,
                           AppUserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartResponseDto addProduct(String username, AddProductToCartDto dto) {
        if (dto.getUnits() <= 0)
            throw new InvalidUnitsException();

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        AppUser user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow();

        CartItem item = cartItemRepository
                .findByUsernameAndProductId(username, product.getId())
                .orElse(null);

        int existingUnits = (item != null) ? item.getUnits() : 0;
        int totalUnits = existingUnits + dto.getUnits();

        if (product.getStock() < totalUnits)
            throw new InsufficientStockException();

        if (item == null) {
            item = CartItem.builder()
                    .user(user)
                    .product(product)
                    .units(dto.getUnits())
                    .updatedAt(LocalDateTime.now())
                    .build();
        } else {
            item.setUnits(totalUnits);
            item.setUpdatedAt(LocalDateTime.now());
        }

        cartItemRepository.save(item);

        return getCart(username);
    }

    @Override
    public CartResponseDto getCart(String username) {
        List<CartItem> items = cartItemRepository.findAll().stream()
                .filter(i -> i.getUser().getUsername().equals(username))
                .toList();

        List<CartItemDto> products = items.stream()
                .map(i -> {
                    double price = i.getProduct().getPrice();
                    double discount = i.getProduct().getDiscount();
                    double finalPrice = price - (price * discount / 100.0);

                    return CartItemDto.builder()
                            .productName(i.getProduct().getName())
                            .unitPrice(price)
                            .discount((int) discount)
                            .discountedPrice(finalPrice)
                            .units(i.getUnits())
                            .totalPrice(finalPrice * i.getUnits())
                            .build();
                }).toList();

        return CartResponseDto.builder()
                .products(products)
                .distinctProducts(cartItemRepository.countDistinctProducts(username))
                .totalUnits(cartItemRepository.totalUnits(username))
                .totalPrice(cartItemRepository.totalPrice(username))
                .build();
    }

    @Override
    public CartResponseDto removeProduct(String username, Long productId) {
        productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        CartItem item = cartItemRepository
                .findByUsernameAndProductId(username, productId)
                .orElseThrow(ProductNotFoundException::new);

        cartItemRepository.delete(item);

        return getCart(username);
    }

    @Override
    public CartResponseDto clearCart(String username) {
        cartItemRepository.deleteByUsername(username);

        return getCart(username);
    }
}
