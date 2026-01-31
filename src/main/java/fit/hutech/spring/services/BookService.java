package fit.hutech.spring.services;

import fit.hutech.spring.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final List<Book> books;

    // Lấy danh sách tất cả sách
    public List<Book> getAllBooks() {
        return books;
    }

    // Tìm sách theo ID
    public Optional<Book> getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    // Thêm sách mới
    public void addBook(Book book) {
        books.add(book);
    }
    public void updateBook(Book updatedBook) {
    for (int i = 0; i < books.size(); i++) {
        if (books.get(i).getId().equals(updatedBook.getId())) {
            books.set(i, updatedBook);
            return;
        }
    }
}
// XÓA SÁCH
    public void deleteBookById(Long id) {
        getBookById(id).ifPresent(books::remove);
    }

}

