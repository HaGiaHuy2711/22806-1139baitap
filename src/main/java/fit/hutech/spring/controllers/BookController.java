package fit.hutech.spring.controllers;

import fit.hutech.spring.entities.Book;
import fit.hutech.spring.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // ====== HIỂN THỊ DANH SÁCH SÁCH ======
    @GetMapping
    public String showBookList(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book/list";
    }

    // ====== FORM THÊM SÁCH ======
    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/add";
    }

    // ====== XỬ LÝ THÊM SÁCH ======
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book) {
        if (bookService.getBookById(book.getId()).isEmpty()) {
            bookService.addBook(book);
        }
        return "redirect:/books";
    }
    @GetMapping("/edit/{id}")
public String editBookForm(@PathVariable Long id, Model model) {
    bookService.getBookById(id).ifPresent(book -> model.addAttribute("book", book));
    return "book/edit";
}

@PostMapping("/edit")
public String updateBook(@ModelAttribute("book") Book book) {
    bookService.updateBook(book);
    return "redirect:/books";
}
 @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        if (bookService.getBookById(id).isPresent()) {
            bookService.deleteBookById(id);
        }
        return "redirect:/books";
    }

}
