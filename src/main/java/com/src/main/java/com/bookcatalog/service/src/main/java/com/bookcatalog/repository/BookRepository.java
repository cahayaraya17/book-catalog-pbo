package com.bookcatalog.repository;

import com.bookcatalog.model.Book;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BookRepository {
    // Simulasi database menggunakan Map (sementara)
    private Map<UUID, Book> booksDatabase;

    public BookRepository() {
        this.booksDatabase = new HashMap<>();
        initializeSampleData();
    }

    // Inisialisasi sample data untuk testing
    private void initializeSampleData() {
        UUID userId = UUID.randomUUID();
        
        Book book1 = new Book(userId, "Harry Potter", "J.K. Rowling", "Fantasy", 
                             2001, 4.8, "Petualangan penyihir muda", "https://example.com/hp.jpg");
        
        Book book2 = new Book(userId, "The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", 
                             1954, 4.9, "Epic fantasy adventure", "https://example.com/lotr.jpg");
        
        Book book3 = new Book(userId, "To Kill a Mockingbird", "Harper Lee", "Fiction", 
                             1960, 4.7, "Kisah tentang ras dan keadilan", "https://example.com/mockingbird.jpg");

        booksDatabase.put(book1.getId(), book1);
        booksDatabase.put(book2.getId(), book2);
        booksDatabase.put(book3.getId(), book3);
    }

    // CREATE - Tambah buku baru
    public boolean addBook(Book book) {
        try {
            booksDatabase.put(book.getId(), book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // READ - Dapatkan semua buku
    public List<Book> getAllBooks() {
        return new ArrayList<>(booksDatabase.values());
    }

    // READ - Dapatkan buku by ID
    public Book getBookById(UUID id) {
        return booksDatabase.get(id);
    }

    // READ - Dapatkan buku by user ID
    public List<Book> getBooksByUserId(UUID userId) {
        return booksDatabase.values().stream()
                .filter(book -> book.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    // UPDATE - Update buku
    public boolean updateBook(Book book) {
        try {
            if (booksDatabase.containsKey(book.getId())) {
                book.setUpdatedAt(LocalDateTime.now());
                booksDatabase.put(book.getId(), book);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // UPDATE - Update cover buku
    public boolean updateBookCover(UUID bookId, String coverUrl) {
        try {
            Book book = booksDatabase.get(bookId);
            if (book != null) {
                book.setCoverUrl(coverUrl);
                book.setUpdatedAt(LocalDateTime.now());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // DELETE - Hapus buku
    public boolean deleteBook(UUID id) {
        try {
            return booksDatabase.remove(id) != null;
        } catch (Exception e) {
            return false;
        }
    }

    // SEARCH - Cari buku berdasarkan judul atau author
    public List<Book> searchBooks(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return booksDatabase.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerKeyword) ||
                               book.getAuthor().toLowerCase().contains(lowerKeyword) ||
                               book.getGenre().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    // GET - Dapatkan semua genre unik
    public List<String> getAllGenres() {
        return booksDatabase.values().stream()
                .map(Book::getGenre)
                .distinct()
                .collect(Collectors.toList());
    }

    // GET - Dapatkan statistik rating
    public double getAverageRating() {
        return booksDatabase.values().stream()
                .mapToDouble(Book::getRating)
                .average()
                .orElse(0.0);
    }

    // GET - Dapatkan buku dengan rating tertinggi
    public List<Book> getTopRatedBooks(int limit) {
        return booksDatabase.values().stream()
                .sorted((b1, b2) -> Double.compare(b2.getRating(), b1.getRating()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
