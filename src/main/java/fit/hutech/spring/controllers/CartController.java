package fit.hutech.spring.controllers;

import fit.hutech.spring.services.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // =====================
    // HIỂN THỊ GIỎ HÀNG
    // =====================
    @GetMapping
    public String showCart(
            HttpSession session,
            @NotNull Model model
    ) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice", cartService.getSumPrice(session));
        model.addAttribute("totalQuantity", cartService.getSumQuantity(session));
        return "book/cart";
    }

    // =====================
    // XÓA 1 SẢN PHẨM KHỎI GIỎ
    // =====================
    @GetMapping("/remove/{id}")
    public String removeFromCart(
            HttpSession session,
            @PathVariable Long id
    ) {
        var cart = cartService.getCart(session);
        cart.removeItems(id);
        cartService.updateCart(session, cart);
        return "redirect:/cart";
    }

    // =====================
    // CẬP NHẬT SỐ LƯỢNG (AJAX)
    // =====================
    @GetMapping("/updateCart/{id}/{quantity}")
    public String updateCart(
            HttpSession session,
            @PathVariable Long id,
            @PathVariable int quantity
    ) {
        var cart = cartService.getCart(session);
        cart.updateItems(id, quantity);
        cartService.updateCart(session, cart);
        return "book/cart";
    }

    // =====================
    // XÓA TOÀN BỘ GIỎ HÀNG
    // =====================
    @GetMapping("/clear")
    public String clearCart(HttpSession session) {
        cartService.removeCart(session);
        return "redirect:/cart";
    }
}
