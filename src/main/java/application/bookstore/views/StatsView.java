package application.bookstore.views;

import application.bookstore.controllers.AuthorController;
import application.bookstore.controllers.StatsController;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.models.BookOrder;
import application.bookstore.models.Order;
import application.bookstore.ui.CreateButton;
import application.bookstore.ui.DeleteButton;
import application.bookstore.ui.EditButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class StatsView extends View{
    private final VBox vBox = new VBox();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    String title;

    public StatsView(String title, int option) {
        this.title=title;
        if (option==1)
            generateClientBasedData();
        else if (option==2)
            generateLibrarianBasedData();
        else if (option==3)
            generateBooksBasedData();
        new StatsController(this);
    }

    private void generateClientBasedData(){
        pieChartData.clear();
        ArrayList<Order> orders = Order.getOrders();
        for(Order o : orders) {
            int match=0;
            for (PieChart.Data d: pieChartData) {
                if (o.getClientName().matches(d.getName())) {
                    d.setPieValue(d.getPieValue()+o.getTotal());
                    match=1;
                    break;
                }
            }
            if (match==0) {
                pieChartData.add(new PieChart.Data(o.getClientName(), o.getTotal()));
            }
        }
    }

    private void generateLibrarianBasedData(){
        pieChartData.clear();
        ArrayList<Order> orders = Order.getOrders();
        for(Order o : orders) {
            System.out.println(o);
            int match=0;
            for (PieChart.Data d: pieChartData) {
                if (o.getUsername().matches(d.getName())) {
                    d.setPieValue(d.getPieValue()+o.getTotal());
                    match=1;
                    break;
                }
            }
            if (match==0) {
                pieChartData.add(new PieChart.Data(o.getUsername(), o.getTotal()));
            }
        }
    }

    private void generateBooksBasedData(){
        pieChartData.clear();
        ArrayList<Order> orders = Order.getOrders();
        orders.clear(); // make sure you read from file
        orders = Order.getOrders();
        for(Order o : orders) {
            for(BookOrder b: o.getBooksOrdered()) {
                int match = 0;
                for (PieChart.Data d : pieChartData) {
                    if ((b.getBookISBN()+" "+b.getTitle()).matches(d.getName())) {
                        d.setPieValue(d.getPieValue() + b.getQuantity());
                        match = 1;
                        break;
                    }
                }
                if (match==0) {
                    pieChartData.add(new PieChart.Data((b.getBookISBN()+" "+b.getTitle()), b.getQuantity()));
                }
            }
        }
    }


    @Override
    public Parent getView() {
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        final PieChart chart = new PieChart(pieChartData);

        chart.setTitle(title);

        vBox.getChildren().addAll(chart);

        return vBox;
    }


}
