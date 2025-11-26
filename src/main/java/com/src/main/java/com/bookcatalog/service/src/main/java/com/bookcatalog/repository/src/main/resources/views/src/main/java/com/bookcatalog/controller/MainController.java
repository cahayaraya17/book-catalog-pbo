package com.bookcatalog.controller;

import com.bookcatalog.model.Book;
import com.bookcatalog.service.BookService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class MainController {

    @FXML
    private TextField searchField;
    
    @FXML
    private ComboBox<String> genreFilter;
    
    @FXML
    private ComboBox<String> ratingFilter;
    
    @FXML
    private Label totalBooksLabel;
    
    @FXML
    private Label averageRatingLabel;
    
    @FXML
    private GridPane booksGrid;

    private BookService bookService;

    public MainController() {
        this.bookService = new BookService();
    }

    @FXML
    public void initialize() {
        System.out.println("MainController initialized!");
        loadBooks();
        setupFilters();
        updateStatistics();
    }

    private void loadBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            displayBooks(books);
        } catch (Exception e) {
            showError("Error loading books: " + e.getMessage());
        }
    }

    private void displayBooks(List<Book> books) {
        booksGrid.getChildren().clear();
        
        int row = 0;
        int col = 0;
        int maxCols = 3;

        for (Book book : books) {
            VBox bookCard = createBookCard(book);
            booksGrid.add(bookCard, col, row);
            
            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }

        updateStatistics();
    }

    private VBox createBookCard(Book book) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        card.setPrefWidth(200);
        card.setPrefHeight(250);

        // Title
        Label titleLabel = new Label(book.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        titleLabel.setWrapText(true);

        // Author
        Label authorLabel = new Label("by " + book.getAuthor());
        authorLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        // Genre
        Label genreLabel = new Label(book.getGenre());
        genreLabel.setStyle("-fx-text-fill: #3498db; -fx-font-size: 11px; -fx-font-weight: bold;");

        // Rating
        Label ratingLabel = new Label("⭐ " + book.getRating());
        ratingLabel.setStyle("-fx-font-size: 12px;");

        // Year
        Label yearLabel = new Label("Year: " + book.getYear());
        yearLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 11px;");

        // Action Buttons
        HBox buttonBox = new HBox(5);
        Button editBtn = new Button("Edit");
        editBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-size: 10px;");
        editBtn.setOnAction(e -> handleEditBook(book));

        Button deleteBtn = new Button("Delete");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 10px;");
        deleteBtn.setOnAction(e -> handleDeleteBook(book));

        buttonBox.getChildren().addAll(editBtn, deleteBtn);

        card.getChildren().addAll(titleLabel, authorLabel, genreLabel, ratingLabel, yearLabel, buttonBox);
        
        // Add click event to show details
        card.setOnMouseClicked(e -> showBookDetails(book));

        return card;
    }

    private void setupFilters() {
        // Setup genre filter
        List<String> genres = bookService.getAllGenres();
        genreFilter.getItems().addAll(genres);
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        if (keyword == null || keyword.trim().isEmpty()) {
            loadBooks();
        } else {
            List<Book> results = bookService.searchBooks(keyword);
            displayBooks(results);
        }
    }

    @FXML
    private void handleClearSearch() {
        searchField.clear();
        loadBooks();
    }

    @FXML
    private void handleGenreFilter() {
        // Implementation for genre filtering
        System.out.println("Genre filter selected: " + genreFilter.getValue());
    }

    @FXML
    private void handleRatingFilter() {
        // Implementation for rating filtering
        System.out.println("Rating filter selected: " + ratingFilter.getValue());
    }

    @FXML
    private void handleAddBook() {
        showInfo("Add Book feature will be implemented next!");
        // TODO: Open add book dialog
    }

    private void handleEditBook(Book book) {
        showInfo("Editing book: " + book.getTitle());
        // TODO: Open edit book dialog
    }

    private void handleDeleteBook(Book book) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Book");
        alert.setHeaderText("Delete " + book.getTitle() + "?");
        alert.setContentText("Are you sure you want to delete this book?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = bookService.deleteBook(book.getId());
                if (success) {
                    showInfo("Book deleted successfully!");
                    loadBooks();
                } else {
                    showError("Failed to delete book!");
                }
            }
        });
    }

    private void showBookDetails(Book book) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Book Details");
        alert.setHeaderText(book.getTitle());
        alert.setContentText(
            "Author: " + book.getAuthor() + "\n" +
            "Genre: " + book.getGenre() + "\n" +
            "Year: " + book.getYear() + "\n" +
            "Rating: " + book.getRating() + "\n" +
            "Description: " + book.getDescription()
        );
        alert.showAndWait();
    }

    private void updateStatistics() {
        List<Book> books = bookService.getAllBooks();
        totalBooksLabel.setText("Total Books: " + books.size());
        
        double avgRating = bookService.getAverageRating();
        averageRatingLabel.setText(String.format("Average Rating: %.1f", avgRating));
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
