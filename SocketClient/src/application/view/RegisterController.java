package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
	private DBConnect connector = new DBConnect();
	Main scene = new Main();
	
	@FXML
	private TextField emailText;

	@FXML
	private PasswordField pwText;
	
	@FXML
	private TextField nickText;

	@FXML
	private CheckBox confirmCheck;

	@FXML
	private Button applyBtn;

	@FXML
	private Button cancelBtn;

	// 등록 이벤트
	@FXML
	private void applyEvent() {
		Stage stage = (Stage)applyBtn.getScene().getWindow();
		connector.connect();
		String email = emailText.getText();
		String passwd = pwText.getText();
		String nickname = nickText.getText();
		Alert alert;
		if(confirmCheck.isSelected() == true) {
			connector.register(email, nickname, passwd);
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입");
			alert.setHeaderText("회원가입 완료");
			alert.setContentText("회원가입을 완료했습니다.");
			alert.showAndWait();
			scene.loginScene(stage);
		}
		else {
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("회원가입 동의");
			alert.setHeaderText("회원가입 동의 버튼 체크");
			alert.setContentText("회원가입 동의를 체크해야 회원가입이 완료됩니다.");
			alert.showAndWait();
		}
		connector.close();
	}

	// 취소 이벤트
	@FXML
	private void cancelEvent() {
		Stage stage = (Stage)cancelBtn.getScene().getWindow();
		scene.loginScene(stage);
	}
}
