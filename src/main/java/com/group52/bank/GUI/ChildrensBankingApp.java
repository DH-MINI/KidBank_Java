package com.group52.bank.GUI;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.task.TaskSystem;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;


import java.util.Objects;

public class ChildrensBankingApp extends Application {
    private static final String PARENT_CSV = "src/main/resources/datacsv/parents.csv";
    private static final String CHILD_CSV = "src/main/resources/datacsv/children.csv";
    private static final String TRANSACTION_HISTORY_CSV = "src/main/resources/datacsv/transactionHistory.csv";
    private static final String TASK_CSV = "src/main/resources/datacsv/taskHistory.csv";
    AuthenticationSystem authSystem;
    TransactionSystem transSystem;
    TaskSystem taskSystem;
    LoginWindow loginWindow;
    public void start(Stage primaryStage) {

//    public ChildrensBankingApp() throws Exception {
//        super("Children's Banking App");

            Text welcomeText = new Text("Welcome to Online Bank!");
            welcomeText.setFont(Font.font("Arial", 50));

            // 按钮
            Button exploreButton = new Button("Explore Now");
            exploreButton.setStyle("-fx-background-color: purple; -fx-text-fill: white;");

            // 左侧布局和样式
            VBox leftBox = new VBox(welcomeText, exploreButton);
            leftBox.setAlignment(Pos.CENTER);
            leftBox.setSpacing(50);
            leftBox.setPadding(new Insets(0, 0, 50, 0));

            // 右侧图片
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/main.png")));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(600); // 设置图片宽度
            imageView.setFitHeight(600); // 设置图片高度
            imageView.setPreserveRatio(false); // 禁用保持比例
            BorderPane.setMargin(imageView, new Insets(0, 150, 0, 0)); // 从上到下的顺序为上、右、下、左

            // 总体布局
            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(leftBox);
            borderPane.setRight(imageView);
            Color color = Color.web("#FFB347"); // 使用 web 颜色
            // 创建 BackgroundFill 对象
            BackgroundFill backgroundFill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
            // 创建 Background 对象
            Background background = new Background(backgroundFill);
            // 将 Background 对象应用于 BorderPane
            borderPane.setBackground(background);
            // 创建场景
            Scene scene = new Scene(borderPane, 1200, 600); // 注意这里的总宽度增加了，以适应右侧的图片

            // 设置舞台
            primaryStage.setTitle("Online Bank");
            primaryStage.setScene(scene);
            primaryStage.show();
            // Initialize authentication, transaction, and task systems (use your existing code)
            authSystem = new AuthenticationSystem(PARENT_CSV, CHILD_CSV);
            transSystem = new TransactionSystem(TRANSACTION_HISTORY_CSV, CHILD_CSV);
            taskSystem = new TaskSystem(TASK_CSV, CHILD_CSV);

            // Create the login window
            loginWindow = new LoginWindow(this);
            loginWindow.setVisible(true);

        exploreButton.setOnAction(e -> {

        });
//
    }

    public static void main(String[] args) throws Exception {
//        new ChildrensBankingApp();
        launch(args);
    }
    }

