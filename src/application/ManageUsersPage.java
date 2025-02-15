package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import databasePart1.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The ManageUsersPage class allows the admin to view and delete users.
 */
public class ManageUsersPage {
    private final DatabaseHelper databaseHelper;
    private final ObservableList<User> users = FXCollections.observableArrayList();

    public ManageUsersPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void show(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");

        TableView<User> tableView = new TableView<>();

        // Table columns
        TableColumn<User, String> userNameColumn = new TableColumn<>("User Name");
        userNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    deleteUser(user);
                });
            }

            
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        tableView.getColumns().addAll(userNameColumn, roleColumn, actionColumn);
        tableView.setItems(users);
        loadUsers();

        layout.getChildren().addAll(new Label("Manage Users"), tableView);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Manage Users");
    }

    private void loadUsers() {
        users.clear();
        try (Connection conn = databaseHelper.getConnectionInstance();
             PreparedStatement stmt = conn.prepareStatement("SELECT userName, role FROM Users");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(rs.getString("userName"), "", rs.getString("role"))); // Password is not needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(User user) {
        try (Connection conn = databaseHelper.getConnectionInstance();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE userName = ?")) {
            stmt.setString(1, user.getUserName());
            stmt.executeUpdate();
            users.remove(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
