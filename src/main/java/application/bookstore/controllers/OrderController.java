package application.bookstore.controllers;

import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.models.BookOrder;
import application.bookstore.models.Order;
import application.bookstore.views.BookView;
import application.bookstore.views.NewOrderView;
import javafx.scene.paint.Color;

import java.util.List;

public class OrderController {
    private final NewOrderView orderView;

    public OrderController(NewOrderView bookView) {
        this.orderView = bookView;
        Order.getOrders();// get data from file
        setEditListener();
        setChooseBookListener();
        setRemoveBookListener();
        setCreateListener();
    }

    private void setChooseBookListener(){
        orderView.getBooks_tableView().setOnMousePressed(e->{
                if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                    BookOrder b = new BookOrder(1, orderView.getBooks_tableView().getSelectionModel().getSelectedItem());
                    orderView.getOrder().getBooksOrdered().add(b);
                    orderView.getBooks_tableView().getItems().remove(orderView.getBooks_tableView().getSelectionModel().getSelectedItem());
                    orderView.getTableView().getItems().add(b);
                    orderView.getTotalValueLabel().setText(((Float)orderView.getOrder().getTotal()).toString());
                }
        });
    }

    private void setRemoveBookListener(){
        orderView.getTableView().setOnMousePressed(e->{
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                BookOrder b = orderView.getTableView().getSelectionModel().getSelectedItem();
                orderView.getOrder().getBooksOrdered().remove(b);
                orderView.getBooks_tableView().getItems().add(b.getBook());
                orderView.getTableView().getItems().remove(b);
                orderView.getTotalValueLabel().setText(((Float)orderView.getOrder().getTotal()).toString());
            }
        });
    }

    private void setEditListener() {
        orderView.getNoCol().setOnEditCommit(e -> {
            BookOrder orderToEdit = e.getRowValue();
            int oldVal=orderToEdit.getQuantity();
            orderToEdit.setQuantity(e.getNewValue());
            if (orderToEdit.getQuantity()>0){
                System.out.println(orderView.getOrder().getBooksOrdered());
                orderView.getTotalValueLabel().setText(((Float)orderView.getOrder().getTotal()).toString());
            }else {
                System.out.println("Edit value invalid! "+e.getNewValue());
                orderToEdit.setQuantity(oldVal);
                orderView.getTableView().getItems().set(orderView.getTableView().getItems().indexOf(orderToEdit), orderToEdit);
                orderView.getResultLabel().setText("Edit value invalid!");
                orderView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });

    }

    private void setCreateListener() {
        orderView.getSaveBtn().setOnMousePressed(e -> {
            orderView.getOrder().completeOrder(orderView.getNameField().getText());
            if (orderView.getOrder().saveInFile()) {
                System.out.println(orderView.getOrder());
                orderView.getOrder().print();
                orderView.getResultLabel().setText("Order created successfully");
                orderView.getResultLabel().setTextFill(Color.DARKGREEN);
                resetFields();
//                orderView.getMainView().getTabPane().getTabs().remove(orderView.getTab());
            } else {
                orderView.getResultLabel().setText("Client name cannot be empty!");
                orderView.getResultLabel().setTextFill(Color.DARKRED);
            }

        });
    }
    private void resetFields() {
        orderView.getNameField().setText("");
    }
}
