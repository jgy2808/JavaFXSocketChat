package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	// ���� ���̾ƿ��� �ʱ�ȭ�Ѵ�.

	private Stage primaryStage;
	private BorderPane rootLayout;

	public void initRootLayout() {
		try {
			// fxml ���Ͽ��� ���� ���̾ƿ��� �����´�.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Main.fxml"));
			rootLayout = (BorderPane) loader.load();

			// ���� ���̾ƿ��� �����ϴ� scene�� �����ش�.
			Scene scene = new Scene(rootLayout);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// UI�� �����ϰ�, ���������� ���α׷��� ���۽�Ű�� �޼ҵ��Դϴ�.
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("ä�ü���");
			initRootLayout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
