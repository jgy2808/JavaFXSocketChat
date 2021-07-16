package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("채팅 클라이언트");
			primaryStage.setResizable(false);
			AnchorPane root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 메인 화면 출력 메서드
	public void mainScene(Stage stage) {
		AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
			Scene scene = new Scene(root, 400, 400);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 회원가입 화면 출력 메서드
	public void registerScene(Stage stage) {
		AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("view/Register.fxml"));
			Scene scene = new Scene(root, 400, 400);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	// 로그인 화면 출력 메서드
	public void loginScene(Stage stage) {
		AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
			Scene scene = new Scene(root, 400, 400);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}