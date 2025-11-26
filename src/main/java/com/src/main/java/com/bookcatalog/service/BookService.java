package com.bookcatalog.service;

import com.bookcatalog.model.Book;
import com.bookcatalog.repository.BookRepository;

import java.util.List;
import java.util.UUID;

public class BookService {
    private BookRepository bookRepository;

    public BookService() {
        this.bookRepository = new BookRepository();
    }

    // CREATE - Tambah buku baru
    public boolean addBook(Book book) {
        return bookRepository.addBook(book);
    }

    // READ - Dapatkan semua buku
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    // READ - Dapatkan buku by ID
    public Book getBookById(UUID id) {
        return bookRepository.getBookById(id);
    }

    // READ - Dapatkan buku by user ID
    public List<Book> getBooksByUserId(UUID userId) {
        return bookRepository.getBooksByUserId(userId);
    }

    // UPDATE - Update buku
    public boolean updateBook(Book book) {
        return bookRepository.updateBook(book);
    }

    // UPDATE - Update cover buku
    public boolean updateBookCover(UUID bookId, String coverUrl) {
        return bookRepository.updateBookCover(bookId, coverUrl);
    }

    // DELETE - Hapus buku
    public boolean deleteBook(UUID id) {
        return bookRepository.deleteBook(id);
    }

    // SEARCH - Cari buku berdasarkan judul atau author
    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword);
    }

    // GET - Dapatkan semua genre unik
    public List<String> getAllGenres() {
        return bookRepository.getAllGenres();
    }

    // GET - Dapatkan statistik rating
    public double getAverageRating() {
        return bookRepository.getAverageRating();
    }
}
