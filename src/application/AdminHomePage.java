package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * AdminPage class represents the user interface for the admin user.
 * This page displays a simple welcome message for the admin.
 */



public class AdminHomePage {
    private List<String[]> adminQuestions = new ArrayList<>(); // List to store admin's questions
    private List<String[]> userQuestions = new ArrayList<>(); // List to store other user's questions
    private VBox adminQuestionsLayout = new VBox(10); // VBox to display admin's questions, with spacing between them
    private VBox userQuestionsLayout = new VBox(10); // VBox to display other user's questions

    /**
     * Displays the admin page in the provided primary stage.
     * @param primaryStage The primary stage where the scene will be displayed.
     */
    public void show(Stage primaryStage) {
        VBox layout = new VBox(20); // Increased spacing between elements
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Label to display the welcome message for the admin
        Label adminLabel = new Label("Hello, Student!");
        adminLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Button to create a new question
        Button createQuestionButton = new Button("Create Question");
        createQuestionButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // Text fields for title and description (hidden by default)
        TextField titleField = new TextField();
        titleField.setPromptText("Enter question title");
        titleField.setVisible(false);  // Hide initially

        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Enter question description");
        descriptionField.setVisible(false);  // Hide initially

        // Buttons for submitting or canceling the question
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        submitButton.setVisible(false);  // Hide initially

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        cancelButton.setVisible(false);  // Hide initially

        // Label to display error messages (hidden by default)
        Label errorLabel = new Label("Please fill in both title and description.");
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        errorLabel.setVisible(false);  // Hide initially

        // Handle creating a question
        createQuestionButton.setOnAction(e -> {
            titleField.setVisible(true);
            descriptionField.setVisible(true);
            submitButton.setVisible(true);
            cancelButton.setVisible(true);
            errorLabel.setVisible(false);  // Hide error message if creating a new question
            createQuestionButton.setDisable(true);  // Disable the create button while the form is visible
        });

        // Handle submitting the question
        submitButton.setOnAction(e -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            
            // Check if title or description is empty
            if (title.isEmpty() || description.isEmpty()) {
                errorLabel.setVisible(true);  // Show error message
            } else {
                String[] question = new String[]{title, description}; // Store title and description as an array
                adminQuestions.add(question);  // Store the question in the admin list
                displayQuestions();  // Update the display of questions

                // Reset the form
                titleField.clear();
                descriptionField.clear();
                titleField.setVisible(false);
                descriptionField.setVisible(false);
                submitButton.setVisible(false);
                cancelButton.setVisible(false);
                createQuestionButton.setDisable(false);
            }
        });

        // Handle canceling the creation of the question
        cancelButton.setOnAction(e -> {
            titleField.clear();
            descriptionField.clear();
            titleField.setVisible(false);
            descriptionField.setVisible(false);
            submitButton.setVisible(false);
            cancelButton.setVisible(false);
            createQuestionButton.setDisable(false);
            errorLabel.setVisible(false);  // Hide error message when canceling
        });

        // Add the elements to the layout
        layout.getChildren().addAll(adminLabel, createQuestionButton, titleField, descriptionField, submitButton, cancelButton, errorLabel, new Label("Admin Questions:"), adminQuestionsLayout, new Label("User Questions:"), userQuestionsLayout);

        // Set the scene to the primary stage
        Scene adminScene = new Scene(layout, 800, 600); // Increased height for questions display
        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Student Page");
    }

    /**
     * Updates the display of both admin and user questions.
     */
    private void displayQuestions() {
        adminQuestionsLayout.getChildren().clear(); // Clear existing admin questions
        userQuestionsLayout.getChildren().clear(); // Clear existing user questions

        // Display Admin Questions
        for (int i = 0; i < adminQuestions.size(); i++) {
            String[] question = adminQuestions.get(i);

            // Create a Label for the title submitted by the admin
            Label questionTitleLabel = new Label(question[0]); // Display the title submitted by the user
            questionTitleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 5px;");

            // Create a Label for the question description
            Label questionDescriptionLabel = new Label(question[1]);
            questionDescriptionLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");

            // Create a separator between the title and description
            Separator separator = new Separator();
            separator.setStyle("-fx-padding: 5px;");

            // Create a Delete button for each admin question
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-font-size: 12px; -fx-padding: 5px;");
            
            // Use a final variable to handle the lambda expression
            final int index = i;  // Create a final copy of the index for use in the lambda
            deleteButton.setOnAction(e -> {
                adminQuestions.remove(index);  // Remove the question at the current index
                displayQuestions();  // Update the display of questions
            });

            // Use an HBox to align the question content and delete button horizontally
            HBox questionBox = new HBox(10);  // Use HBox for horizontal layout
            questionBox.setStyle("-fx-alignment: center-left;");  // Align title and description on the left

            // Add the question title, description, and separator to the HBox
            VBox textBox = new VBox(5);  // VBox to hold the title, description, and separator
            textBox.getChildren().addAll(questionTitleLabel, questionDescriptionLabel, separator);

            // Align the delete button to the right side of the HBox
            HBox deleteButtonBox = new HBox(deleteButton);
            deleteButtonBox.setStyle("-fx-alignment: center-right;");  // Align button to the right

            // Add the question text (title, description) and the delete button to the HBox
            questionBox.getChildren().addAll(textBox, deleteButtonBox);
            adminQuestionsLayout.getChildren().add(questionBox);  // Add the HBox with the question and delete button
        }

        // Display User Questions
        for (int i = 0; i < userQuestions.size(); i++) {
            String[] question = userQuestions.get(i);

            // Create a Label for the title submitted by the user
            Label questionTitleLabel = new Label(question[0]); // Display the title
            questionTitleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 5px;");

            // Create a Label for the question description
            Label questionDescriptionLabel = new Label(question[1]);
            questionDescriptionLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");

            // Create a separator between the title and description
            Separator separator = new Separator();
            separator.setStyle("-fx-padding: 5px;");

            // Create an "Answer" button for each user question
            Button answerButton = new Button("Answer");
            answerButton.setStyle("-fx-font-size: 12px; -fx-padding: 5px;");
            
            TextArea answerField = new TextArea();
            answerField.setPromptText("Type your answer here...");
            answerField.setVisible(false);  // Initially hidden

            Button submitAnswerButton = new Button("Submit Answer");
            submitAnswerButton.setStyle("-fx-font-size: 12px; -fx-padding: 5px;");
            submitAnswerButton.setVisible(false);  // Initially hidden

            answerButton.setOnAction(e -> {
                answerField.setVisible(true); // Show the answer field
                submitAnswerButton.setVisible(true); // Show the submit button
            });

            submitAnswerButton.setOnAction(e -> {
                String answer = answerField.getText();
                if (!answer.isEmpty()) {
                    // You could store the answer somewhere, for now, just display it
                    Label answerLabel = new Label("Answer: " + answer);
                    answerLabel.setStyle("-fx-font-size: 12px; -fx-font-style: italic;");
                    questionDescriptionLabel.setText(question[1] + "\n" + answerLabel.getText()); // Append the answer below description
                    answerField.clear();
                    answerField.setVisible(false);
                    submitAnswerButton.setVisible(false);
                }
            });

            // Use an HBox to align the question content and answer button horizontally
            HBox questionBox = new HBox(10);
            questionBox.setStyle("-fx-alignment: center-left;"); 

            // Add the question title, description, separator, and answer button to the HBox
            VBox textBox = new VBox(5);
            textBox.getChildren().addAll(questionTitleLabel, questionDescriptionLabel, separator);

            // Add the answer button and answer field to the HBox
            HBox answerBox = new HBox(10);
            answerBox.getChildren().addAll(answerButton, answerField, submitAnswerButton);

            // Add the question content and answer option to the layout
            questionBox.getChildren().addAll(textBox, answerBox);
            userQuestionsLayout.getChildren().add(questionBox); 
        }
    }
}

