package application.bookstore.controllers;
import application.bookstore.models.User;
import application.bookstore.views.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainController {
    private final MainView mainView;
    private final Stage mainStage;

    public MainController(MainView mainView, Stage mainStage) {
        this.mainView = mainView;
        this.mainStage = mainStage;
        setListener();

    }
    private Tab openTab(Tab tab){
        for(Tab t:mainView.getTabPane().getTabs()){
            if(t.getText().equals(tab.getText())){
                return t;
            }
        }
        mainView.getTabPane().getTabs().add(tab);
        return tab;
    }

    private void setListener() {

        mainView.getViewAuthors().setOnAction((e)-> {
            Tab authorTab = new Tab("Authors");
            authorTab.setContent(new AuthorView().getView());
            Tab tab=openTab(authorTab);
        });

        mainView.getViewBooks().setOnAction((e)-> {
            Tab booksTab = new Tab("Books");
            booksTab.setContent(new BookView().getView());
            Tab tab=openTab(booksTab);
        });
        mainView.getNewBill().setOnAction(e->{
            Tab order = new Tab("New Order");
            order.setContent(new NewOrderView(mainView, order).getView());
            Tab tab=openTab(order);
        });
        mainView.getClientBasedMenu().setOnAction(e->{
            Tab stats = new Tab("Client Based Statistics");
            stats.setContent(new StatsView("Client Based Statistics", 1).getView());
            Tab tab=openTab(stats);
        });

        mainView.getLibrarianBasedMenu().setOnAction(e->{
            Tab stats = new Tab("User Based Statistics");
            stats.setContent(new StatsView("User Profit Earned Statistics", 2).getView());
            Tab tab=openTab(stats);
        });

        mainView.getBookBasedMenu().setOnAction(e->{
            Tab stats = new Tab("Book Based Statistics");
            stats.setContent(new StatsView("Number of books sold Statistics", 3).getView());
            Tab tab=openTab(stats);
        });

        mainView.getManageUsers().setOnAction(e->{
            Tab users = new Tab("Users");
            users.setContent(new UsersView().getView());
            Tab tab=openTab(users);
        });

        mainView.getChangePasswordMenuLabel().setOnMouseClicked((e)->{


            TextField oldPassField = new TextField();
            TextField newPassField = new TextField();
            TextField newPassField1 = new TextField();
            Label oldPassLabel = new Label("Current Password: ", oldPassField);
            oldPassLabel.setContentDisplay(ContentDisplay.RIGHT);
            Label newPassLabel = new Label("New Password: ", newPassField);
            newPassLabel.setContentDisplay(ContentDisplay.RIGHT);
            Label newPassLabel1 = new Label("New Password: ", newPassField1);
            newPassLabel1.setContentDisplay(ContentDisplay.RIGHT);

            HBox h = new HBox();
            h.setAlignment(Pos.CENTER);
            Button okButton = new Button("Ok");
            Button cancelButton = new Button("Cancel");
            h.getChildren().addAll(okButton, cancelButton);

            Label messgaeLabel = new Label("");

            VBox v = new VBox();
            v.setAlignment(Pos.CENTER);
            v.getChildren().addAll(oldPassLabel, newPassLabel, newPassLabel1, messgaeLabel, h);
            v.setSpacing(5);

            Scene secondScene = new Scene(v, 350, 350);

            Stage newWindow = new Stage();
            newWindow.setTitle("Change Password");
            newWindow.setScene(secondScene);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(mainStage);

            cancelButton.setOnMousePressed(ev->newWindow.close());
            okButton.setOnMousePressed(ev->{
                if (oldPassField.getText().equals(mainView.getCurrentUser().getPassword())){
                    if (newPassField.getText().matches(newPassField1.getText())){
                        User u = new User(mainView.getCurrentUser().getUsername(), newPassField.getText(), mainView.getCurrentUser().getRole());
                        if (u.isValid()){
                            mainView.getCurrentUser().deleteFromFile();
                            mainView.setCurrentUser(u);
                            u.saveInFile();
                            newWindow.close();
                        }
                        else{
                            messgaeLabel.setText("New Password Invalid!");
                            messgaeLabel.setTextFill(Color.DARKRED);
                        }
                    }
                    else{
                        messgaeLabel.setText("New Passwords do not match!");
                        messgaeLabel.setTextFill(Color.DARKRED);
                    }
                }
                else{
                    messgaeLabel.setText("Current Password is Incorrect!");
                    messgaeLabel.setTextFill(Color.DARKRED);
                }
            });

            newWindow.show();
        });

        mainView.getLogoutMenuLabel().setOnMouseClicked((e)->{
            LoginView loginView = new LoginView();
            new LoginController(loginView, new MainView(mainStage), mainStage);
            Scene scene = new Scene(loginView.getView(), 720, 440);
            mainStage.setScene(scene);
        });
    }

}
