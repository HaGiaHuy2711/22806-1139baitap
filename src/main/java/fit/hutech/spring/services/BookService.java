package fit.hutech.spring.services;

import fit.hutech.spring.entities.Book;
import fit.hutech.spring.repositories.IBookRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(
        isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class}
)
public class BookService {

    private final IBookRepository bookRepository;

    // Lấy danh sách sách có phân trang & sắp xếp
    public List<Book> getAllBooks(
            Integer pageNo,
            Integer pageSize,
            String sortBy
    ) {
        return bookRepository.findAllBooks(pageNo, pageSize, sortBy);
    }

    // Lấy sách theo ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Thêm sách mới
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    // Cập nhật sách
    public void updateBook(@NotNull Book book) {
        Book existingBook = bookRepository
                .findById(book.getId())
                .orElse(null);

        Objects.requireNonNull(existingBook).setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        existingBook.setCategory(book.getCategory());

        bookRepository.save(existingBook);
    }

    // Xóa sách theo ID
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
