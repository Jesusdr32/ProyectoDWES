package es.iesclaradelrey.da2d1e.shopeahjdr.api.controllers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.AddProductToCartDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.CartResponseDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication.getName()));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addProduct(
            Authentication authentication,
            @RequestBody AddProductToCartDto dto
    ) {
        return ResponseEntity.ok(cartService.addProduct(authentication.getName(), dto));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDto> removeProduct(
            Authentication authentication,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(cartService.removeProduct(authentication.getName(), productId));
    }

    @DeleteMapping
    public ResponseEntity<CartResponseDto> clearCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.clearCart(authentication.getName()));
    }
}