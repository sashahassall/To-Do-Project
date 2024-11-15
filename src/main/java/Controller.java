import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class Controller {

    @FXML
    private VBox tasksVBox;

    @FXML
    private TextField taskInput;

    @FXML
    private Button plusButton;

    @FXML
    private Label totalCount;

    @FXML
    private ScrollPane scrollPane;


    @FXML
    private Label completedCount;

    private int total = 0;
    private int completed = 0;

    @FXML
    public void initialize() {

        plusButton.setOnAction(event -> addTask());
        taskInput.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                addTask();
            }
        });

        tasksVBox.setSpacing(20);
        tasksVBox.setPrefHeight(500);
        tasksVBox.setMaxHeight(500);
        updateCount();
    }

    private void updateCount() {
        totalCount.setText(String.valueOf(total));
        completedCount.setText(String.valueOf(completed));
    }

    public void addTask() {
        String taskText = taskInput.getText().trim();
        if (taskText.isEmpty()) {
            return;
        }

        total++;
        updateCount();
        taskInput.clear();


        AnchorPane taskBubble = new AnchorPane();
        taskBubble.setPrefSize(915, 68);
        taskBubble.getStyleClass().add("task-pane");

        HBox taskHBox = new HBox();
        taskHBox.setSpacing(30);
        taskHBox.setAlignment(javafx.geometry.Pos.CENTER);

        Label taskLabel = new Label(taskText);
        taskLabel.getStyleClass().add("words");
        taskLabel.setWrapText(true);
        taskLabel.setMaxWidth(800);

        taskBubble.minHeightProperty().bind(taskLabel.heightProperty().add(20));

        Button btnDelete = new Button();
        btnDelete.setStyle("-fx-opacity: 1; -fx-background-color: transparent; -fx-border-color: transparent;");
        btnDelete.setOnAction(event -> deleteTask(taskBubble));

        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/Delete.png")));
        deleteIcon.setFitWidth(35);
        deleteIcon.setPreserveRatio(true);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1);
        deleteIcon.setEffect(colorAdjust);

        btnDelete.setGraphic(deleteIcon);

        ImageView taskIcon = new ImageView(new Image(getClass().getResourceAsStream("/circle.png")));
        taskIcon.setFitWidth(40);
        taskIcon.setPreserveRatio(true);
        taskIcon.setEffect(colorAdjust);

        CheckBox checkBox = new CheckBox();
        checkBox.setOpacity(0);
        checkBox.setScaleX(2);
        checkBox.setScaleY(2);

        ImageView checkmarkIcon = new ImageView(new Image(getClass().getResourceAsStream("/check.png")));
        checkmarkIcon.setFitWidth(35);
        checkmarkIcon.setPreserveRatio(true);
        checkmarkIcon.setVisible(false);
        checkmarkIcon.setTranslateX(5);

        FadeTransition checkmarkFadeIn = new FadeTransition(Duration.millis(100), checkmarkIcon);
        checkmarkFadeIn.setFromValue(0.0);
        checkmarkFadeIn.setToValue(1.0);

        FadeTransition checkmarkFadeOut = new FadeTransition(Duration.millis(100), checkmarkIcon);
        checkmarkFadeOut.setFromValue(1.0);
        checkmarkFadeOut.setToValue(0.0);

        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                completed++;
                checkmarkIcon.setVisible(true);
                checkmarkFadeIn.play();
            } else {
                completed--;
                checkmarkFadeOut.play();
            }
            updateCount();
        });

        StackPane iconStackPane = new StackPane();
        iconStackPane.getChildren().addAll(taskIcon, checkBox, checkmarkIcon);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        taskHBox.getChildren().addAll(iconStackPane, taskLabel, spacer, btnDelete);
        taskBubble.getChildren().add(taskHBox);

        AnchorPane.setTopAnchor(taskHBox, 7.0);
        AnchorPane.setRightAnchor(taskHBox, 10.0);
        AnchorPane.setLeftAnchor(taskHBox, 10.0);

        tasksVBox.getChildren().add(0, taskBubble);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), taskBubble);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();


    }

    private void deleteTask(AnchorPane taskBubble) {
        CheckBox checkBox = (CheckBox) ((StackPane) ((HBox) taskBubble.getChildren().get(0)).getChildren().get(0)).getChildren().get(1);
        if (checkBox.isSelected()) {
            completed--;
        }
        total--;
        updateCount();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), taskBubble);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> tasksVBox.getChildren().remove(taskBubble));
        fadeOut.play();
    }
}
