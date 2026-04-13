package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.AddProductToCartDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.CartResponseDto;

public interface CartService {
    CartResponseDto addProduct(String username, AddProductToCartDto dto);
    CartResponseDto getCart(String username);
    CartResponseDto removeProduct(String username, Long productId);
    CartResponseDto clearCart(String username);
}
