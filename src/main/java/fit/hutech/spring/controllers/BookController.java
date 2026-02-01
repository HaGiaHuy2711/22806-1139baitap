package fit.hutech.spring.controllers;

import fit.hutech.spring.daos.Item;
import fit.hutech.spring.entities.Book;
import fit.hutech.spring.services.BookService;
import fit.hutech.spring.services.CartService;
import fit.hutech.spring.services.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final CartService cartService;

    // Hiển thị danh sách sách + phân trang + sắp xếp
    @GetMapping
    public String showAllBooks(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        model.addAttribute(
                "books",
                bookService.getAllBooks(pageNo, pageSize, sortBy)
        );
        model.addAttribute("currentPage", pageNo);
        model.addAttribute(
                "categories",
                categoryService.getAllCategories()
        );
        model.addAttribute(
                "totalPages",
                bookService.getAllBooks(pageNo, pageSize, sortBy).size() / pageSize
        );

        return "book/list";
    }

    // Search books
    @GetMapping("/search")
    public String searchBooks(
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model
    ) {
        model.addAttribute("books", bookService.searchBooks(keyword));
        model.addAttribute("currentPage", 0);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("totalPages", 0);
        return "book/list";
    }

    // Hiển thị form thêm sách
    @GetMapping("/add")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute(
                "categories",
                categoryService.getAllCategories()
        );
        return "book/add";
    }

    // Xử lý thêm sách
    @PostMapping("/add")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    // Hiển thị form sửa sách
        @GetMapping("/edit/{id}")
        @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(
            @PathVariable Long id,
            Model model
    ) {
        Book book = bookService.getBookById(id).orElse(null);
        model.addAttribute("book", book);
        model.addAttribute(
                "categories",
                categoryService.getAllCategories()
        );
        return "book/edit";
    }

    // Xử lý cập nhật sách
    @PostMapping("/edit")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String updateBook(@ModelAttribute Book book) {
        bookService.updateBook(book);
        return "redirect:/books";
    }

    // Xóa sách
    @GetMapping("/delete/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

    // Xem chi tiết sách
    @GetMapping("/{id}")
    public String viewBookDetail(
            @PathVariable Long id,
            Model model
    ) {
        Book book = bookService.getBookById(id).orElse(null);
        model.addAttribute("book", book);
        return "book/detail";
    }

    // ======================
    // GIỎ HÀNG – ADD TO CART
    // ======================
    @PostMapping("/add-to-cart")
    public String addToCart(
            HttpSession session,
            @RequestParam long id,
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        var cart = cartService.getCart(session);
        cart.addItems(new Item(id, name, price, quantity));
        cartService.updateCart(session, cart);
        return "redirect:/books";
    }
}

