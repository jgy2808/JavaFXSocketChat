package application.view;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class LoginController {
	HashMap<String, Integer> serverConn = new HashMap<String, Integer>();

	@FXML
	private TextField emailText;

	@FXML
	private TextField pwText;

	@FXML
	private ChoiceBox<String> serverChoice;

	@FXML
	private Button loginBtn;

	@FXML
	private Button registerBtn;

	// 로그인 이벤트
	@FXML
	private void loginEvent() {

	}

	// 회원가입 이벤트
	@FXML
	private void registerEvent() {

	}

}
