package application.view;

import java.util.HashMap;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	HashMap<String, Integer> serverConn = new HashMap<String, Integer>();
	DBConnect connector = new DBConnect();
	Main scene = new Main();
	
	@FXML
	private TextField emailText;

	@FXML
	private PasswordField pwText;

	@FXML
	private ChoiceBox<String> serverChoice;

	@FXML
	private Button loginBtn;

	@FXML
	private Button registerBtn;
	

	// 로그인 이벤트
	@FXML
	private void loginEvent() {
		int result;
		Stage stage = (Stage)loginBtn.getScene().getWindow();
		Alert alert;
		connector.connect();
		String email = emailText.getText();
		String passwd = pwText.getText();
		result = connector.login(email, passwd);
		switch(result) {
			case 1:
				scene.mainScene(stage);
				break;
			case 2:
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("로그인 실패");
				alert.setHeaderText("로그인 실패");
				alert.setContentText("비밀번호가 틀렸습니다.");
				alert.showAndWait();
				break;
			case 3:
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("로그인 실패");
				alert.setHeaderText("로그인 실패");
				alert.setContentText("없는 이메일입니다.");
				alert.showAndWait();
				break;
		}
	}

	// 회원가입 이벤트
	@FXML
	private void registerEvent() {
		Stage stage = (Stage)registerBtn.getScene().getWindow();
		scene.registerScene(stage);
	}

}
