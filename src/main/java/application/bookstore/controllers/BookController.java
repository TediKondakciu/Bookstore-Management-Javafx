package application.bookstore.controllers;

import application.bookstore.auxiliaries.FileHandler;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.views.BookView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookController {
    private final BookView bookView;

    public BookController(BookView bookView) {
        this.bookView = bookView;
        setSaveListener();
        setDeleteListener();
        setEditListener();
        setComboBoxListener();
        setSearchListener();
    }

    private void setSearchListener() {
        bookView.getSearchView().getClearBtn().setOnAction(e -> {
            bookView.getSearchView().getSearchField().setText("");
            bookView.getTableView().setItems(FXCollections.observableArrayList(Book.getBooks()));
        });
        bookView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = bookView.getSearchView().getSearchField().getText();
            ArrayList<Book> searchResults = Book.getSearchResults(searchText);
            bookView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    private void setComboBoxListener(){
        bookView.getAuthorsComboBox().setOnMouseClicked(e->{
            bookView.getAuthorsComboBox().getItems().setAll(Author.getAuthors());
            // set default selected the first author
            if (!Author.getAuthors().isEmpty())
                bookView.getAuthorsComboBox().setValue(Author.getAuthors().get(0));
        });

    }
    private void setSaveListener() {
        bookView.getSaveBtn().setOnAction(e -> {
            String isbn = bookView.getIsbnField().getText();
            String title = bookView.getTitleField().getText();
            float purchasedPrice = Float.parseFloat(bookView.getPurchasedPriceField().getText());
            float sellingPrice = Float.parseFloat(bookView.getSellingPriceField().getText());
            Author author = bookView.getAuthorsComboBox().getValue();
            Book book = new Book(isbn, title, purchasedPrice, sellingPrice, author);

            if (!book.exists()){
                if (book.saveInFile()) {
                    bookView.getTableView().getItems().add(book);
                    bookView.getResultLabel().setText("Book created successfully");
                    bookView.getResultLabel().setTextFill(Color.DARKGREEN);
                    resetFields();
                } else {
                    bookView.getResultLabel().setText("Book creation failed");
                    bookView.getResultLabel().setTextFill(Color.DARKRED);
                }
            } else {
                bookView.getResultLabel().setText("Book with this ISBN exists.");
                bookView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });
    }

    private void setDeleteListener(){
        bookView.getDeleteBtn().setOnAction(e->{
            List<Book> itemsToDelete = List.copyOf(bookView.getTableView().getSelectionModel().getSelectedItems());
            for (Book book: itemsToDelete) {
                if (book.deleteFromFile()) {
                    bookView.getTableView().getItems().remove(book);
                    System.out.println("Book with ISBN" + book.getIsbn() + "was successfully removed");
                    bookView.getResultLabel().setText("Book was successfully removed");
                    bookView.getResultLabel().setTextFill(Color.DARKGREEN);
                } else {
                    System.out.println("Book with ISBN:" + book.getIsbn() + "deletion failed ISBN");
                    bookView.getResultLabel().setText("Book was not deleted");
                    bookView.getResultLabel().setTextFill(Color.DARKRED);
                    break;
                }
            }
        });
    }

    private void setEditListener() {
        bookView.getIsbnCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            String oldVal=bookToEdit.getIsbn();
            bookToEdit.setIsbn(e.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateFile();
            }
            else {
                bookToEdit.setIsbn(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Edit value invalid!");
                bookView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });

        bookView.getTitleCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            String oldVal=bookToEdit.getTitle();
            bookToEdit.setTitle(e.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateFile();
            }
            else {
                System.out.println("Edit value invalid! "+e.getNewValue());
                bookToEdit.setTitle(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Edit value invalid!");
                bookView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });

        bookView.getPurchasedPriceCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            float oldVal=bookToEdit.getPurchasedPrice();
            bookToEdit.setPurchasedPrice(e.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateFile();
            }
            else {
                System.out.println("Edit value invalid! "+e.getNewValue());
                bookToEdit.setPurchasedPrice(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Edit value invalid!");
                bookView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });

        bookView.getSellingPriceCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            float oldVal=bookToEdit.getSellingPrice();
            bookToEdit.setSellingPrice(e.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateFile();
            }
            else {
                System.out.println("Edit value invalid! "+e.getNewValue());
                bookToEdit.setSellingPrice(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Edit value invalid!");
                bookView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });


    }

    private void resetFields() {
        bookView.getIsbnField().setText("");
        bookView.getTitleField().setText("");
        bookView.getPurchasedPriceField().setText("");
        bookView.getSellingPriceField().setText("");
    }
}
