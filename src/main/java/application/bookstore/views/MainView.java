package application.bookstore.views;

import application.bookstore.controllers.MainController;
import application.bookstore.models.Role;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainView extends View {
    private final MenuBar menuBar = new MenuBar();

    ImageView imageView1 = new ImageView(String.valueOf(MainView.class.getResource("/images/prove.png")));
    private final Menu booksMenu= new Menu("Books", imageView1);
    private final MenuItem viewBooks = new MenuItem("View Books");
    private final MenuItem viewAuthors = new MenuItem("View Authors");


    private final Menu salesMenu= new Menu("Sales");
    private final MenuItem newBill = new MenuItem("New Bill");


    private final Label changePasswordMenuLabel = new Label("Change Password");
    private final Menu changePasswordMenu= new Menu("", changePasswordMenuLabel);

    private final Label logoutMenuLabel = new Label("Logout");
    private final Menu logoutMenu = new Menu("", logoutMenuLabel);

    private final Menu adminMenu = new Menu("Admin");
    private final MenuItem manageUsers = new MenuItem("Manage Users");
    private final MenuItem manageBooks = new MenuItem("Manage Books");
    private final MenuItem manageAuthors = new MenuItem("Manage Authors");


    private final MenuItem clientBasedMenu = new MenuItem("Client Based Analysis");
    private final MenuItem bookBasedMenu = new MenuItem("Book Based Analysis");
    private final MenuItem librarianBasedMenu = new MenuItem("User Based Analysis");

    public MainView(Stage mainStage){
        new MainController(this, mainStage);
    }


    private final TabPane tabPane = new TabPane();

    @Override
    public Parent getView() {
        menuBar.setStyle(
                "-fx-background-color: #9A9A9A;" +
                        "-fx-selection-bar: lightgray;"+
                "-fx-text-background-color: black;" +
                        "-fx-focused-text-base-color: yellow;" +
                        "-fx-alignment: center;"
        );
        BorderPane borderPane = new BorderPane();

        booksMenu.getItems().addAll(viewBooks, viewAuthors);
        salesMenu.getItems().add(newBill);
        menuBar.getMenus().addAll(booksMenu, salesMenu, changePasswordMenu, logoutMenu);


        Tab defaultTab = new Tab("Books");
        defaultTab.setContent(new BookView().getView());

        Role currentRole = (getCurrentUser() != null ? getCurrentUser().getRole() : null);
        if (currentRole != null) {
            if (currentRole == Role.ADMIN) {
                menuBar.getMenus().add(adminMenu);
                adminMenu.getItems().addAll(manageUsers);
            }
            if (currentRole == Role.MANAGER || currentRole == Role.ADMIN) {
                salesMenu.getItems().addAll(clientBasedMenu, bookBasedMenu, librarianBasedMenu);
            }
            tabPane.getTabs().add(defaultTab);
        }

        VBox topPane = new VBox();
        topPane.getChildren().addAll(menuBar, tabPane);
        borderPane.setTop(topPane);

        borderPane.setBottom(new StackPane(new Text("Welcome " + getCurrentUser().getUsername())));
        return borderPane;
    }


    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Menu getBooksMenu() {
        return booksMenu;
    }

    public MenuItem getViewBooks() {
        return viewBooks;
    }

    public MenuItem getViewAuthors() {
        return viewAuthors;
    }

    public Menu getSalesMenu() {
        return salesMenu;
    }

    public MenuItem getNewBill() {
        return newBill;
    }

    public Menu getChangePasswordMenu() {
        return changePasswordMenu;
    }

    public Menu getLogoutMenu() {
        return logoutMenu;
    }

    public Menu getAdminMenu() {
        return adminMenu;
    }

    public MenuItem getManageUsers() {
        return manageUsers;
    }

    public MenuItem getManageBooks() {
        return manageBooks;
    }

    public MenuItem getManageAuthors() {
        return manageAuthors;
    }

    public MenuItem getClientBasedMenu() {
        return clientBasedMenu;
    }

    public MenuItem getBookBasedMenu() {
        return bookBasedMenu;
    }

    public MenuItem getLibrarianBasedMenu() {
        return librarianBasedMenu;
    }

    public Label getChangePasswordMenuLabel() {
        return changePasswordMenuLabel;
    }

    public Label getLogoutMenuLabel() {
        return logoutMenuLabel;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

}
