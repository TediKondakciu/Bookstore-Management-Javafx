package application.bookstore.views;

import application.bookstore.ui.DeleteButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginView extends View {
    private final BorderPane borderPane = new BorderPane();

    private final TextField usernameField = new TextField();

    private final PasswordField passwordField = new PasswordField();
    private final Button loginBtn = new Button("Login");
    private final Label errorLabel = new Label("");
    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginBtn() {
        return loginBtn;
    }
    public Label getErrorLabel() {
        return errorLabel;
    }
    public LoginView() {
        setView();
    }

    /*loginBtn.setStyle(
            "-fx-background-color: yellow;"
            );*/
    private void setView() {
        Label usernameLabel = new Label("Username", usernameField);
        Label passwordLabel = new Label("Password", passwordField);
        VBox vBox = new VBox();
        Label loginLabel = new Label("LOGIN");
        loginLabel.setStyle("-fx-text-fill:BLACK; -fx-font-size: 40;");
        vBox.getChildren().addAll(loginLabel, usernameLabel, passwordLabel, loginBtn, errorLabel);

        usernameField.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER)){
                passwordField.requestFocus();
            }
        });
        StackPane stackPane=new StackPane();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(20);
        ImageView imageView = new ImageView(String.valueOf(LoginView.class.getResource("/images/Books.png")));
        imageView.setFitHeight(350);
        imageView.setFitWidth(550);
        stackPane.getChildren().addAll(imageView, vBox);
        borderPane.setCenter(stackPane);
    }

    @Override
    public Parent getView() {
        return borderPane;
    }
}
