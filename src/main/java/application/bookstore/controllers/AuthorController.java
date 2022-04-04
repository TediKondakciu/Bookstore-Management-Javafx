package application.bookstore.controllers;

import application.bookstore.auxiliaries.FileHandler;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.views.AuthorView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorController {
    private AuthorView authorView;
    public AuthorController(AuthorView authorView) {
        this.authorView = authorView;
        setSaveListener();
        setDeleteListener();
        setEditListener();
        setSearchListener();
    }

    private void setEditListener() {
        authorView.getFirstNameCol().setOnEditCommit(e -> {
            Author authorToEdit = e.getRowValue();
            String oldVal=authorToEdit.getFirstName();
            authorToEdit.setFirstName(e.getNewValue());
            if (authorToEdit.isValid()){
                authorToEdit.updateFile();
            }
            else {
                System.out.println("Edit value invalid! "+e.getNewValue());
                authorToEdit.setFirstName(oldVal);
                authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);
                authorView.getResultLabel().setText("Edit value invalid!");
                authorView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });

        authorView.getLastNameCol().setOnEditCommit(e -> {
            Author authorToEdit = e.getRowValue();
            String oldVal=authorToEdit.getLastName();
            authorToEdit.setLastName(e.getNewValue());
            if (authorToEdit.isValid()){
                authorToEdit.updateFile();
            }
            else {
                System.out.println("Edit value invalid! "+e.getNewValue());
                authorToEdit.setLastName(oldVal);
                authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);
                authorView.getResultLabel().setText("Edit value invalid!");
                authorView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });
    }

    private void setSearchListener() {
        authorView.getSearchView().getClearBtn().setOnAction(e -> {
            authorView.getSearchView().getSearchField().setText("");
            authorView.getTableView().setItems(FXCollections.observableArrayList(Author.getAuthors()));
        });
        authorView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = authorView.getSearchView().getSearchField().getText();
            ArrayList<Author> searchResults = Author.getSearchResults(searchText);
            authorView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    private void setDeleteListener() {
        authorView.getDeleteBtn().setOnAction(e->{
            List<Author> itemsToDelete = List.copyOf(authorView.getTableView().getSelectionModel().getSelectedItems());
            for (Author a: itemsToDelete) {
                if (a.deleteFromFile()) {
                    authorView.getTableView().getItems().remove(a);
                    System.out.println("Author removed successfully FullName: "+a.getFullName());
                    authorView.getResultLabel().setText("Book removed successfully");
                    authorView.getResultLabel().setTextFill(Color.DARKGREEN);
                } else {
                    System.out.println("Book deletion failed FullName: "+a.getFullName());
                    authorView.getResultLabel().setText("Book deletion failed");
                    authorView.getResultLabel().setTextFill(Color.DARKRED);
                    break;
                }
            }
        });
    }

    private void setSaveListener() {
        authorView.getSaveBtn().setOnAction(e -> {
            String firstName = authorView.getFirstNameField().getText();
            String lastName = authorView.getLastNameField().getText();
            Author author = new Author(firstName, lastName);
            if (!author.exists()) {
                if (author.saveInFile()) {
                    authorView.getTableView().getItems().add(author);
                    authorView.getResultLabel().setText("Author created successfully!");
                    authorView.getResultLabel().setTextFill(Color.DARKGREEN);
                    authorView.getFirstNameField().setText("");
                    authorView.getLastNameField().setText("");
                } else {
                    authorView.getResultLabel().setText("Author creation failed!");
                    authorView.getResultLabel().setTextFill(Color.DARKRED);
                }
            }
            else {
                authorView.getResultLabel().setText("Author with this Full Name exists.");
                authorView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });
    }

}
