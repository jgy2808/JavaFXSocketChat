package application.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import application.util.DBConnect;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainController implements Initializable {
	DBConnect connector = new DBConnect();
	Socket socket;

	@FXML
	private Button connectionButton;
	@FXML
	private Button sendButton;
	@FXML
	private TextField input;
	@FXML
	private TextField IPText;
	@FXML
	private TextField portText;
	@FXML
	private TextField userName;
	@FXML
	private TextArea textArea;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		connector.connect();
		String nick = connector.getNick(LoginController.email);
		userName.setText(nick);
		IPText.setText(LoginController.IP);
		portText.setText(LoginController.port);
		connector.close();
	}

	// 접속하기 클릭 시 작동하는 함수
	@FXML
	private void connection() {
		if (connectionButton.getText().equals("접속하기")) {
			int port = 0;
			try {
				port = Integer.parseInt(portText.getText());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			startClient(IPText.getText(), port);
			Platform.runLater(() -> {
				textArea.appendText("[채팅방 접속]\n");
			});
			connectionButton.setText("종료");
			input.setDisable(false);
			sendButton.setDisable(false);
			textArea.setDisable(false);
			input.requestFocus();
		} else {
			stopClient();
			Platform.runLater(() -> {
				textArea.appendText("[채팅방 퇴장]\n");
			});
			connectionButton.setText("접속하기");
			input.setDisable(true);
			sendButton.setDisable(true);
		}
	}

	// 보내기 버튼 클릭 시 작동하는 함수
	@FXML
	private void sndBtnClick() {
		send(userName.getText() + ">" + input.getText() + "\n");
		input.setText("");
		input.requestFocus();
	}
	
	// 엔터키를 눌렀을 때 채팅을 보내는 함수
	@FXML
	private void enterKeyEvent(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			send(userName.getText() + ">" + input.getText() + "\n");
			input.setText("");
			input.requestFocus();
		}
	}

	// 클라이언트 시작 함수
	private void startClient(String IP, int port) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(IP, port);
					receive();
				} catch (Exception e) {
					// TODO: handle exception
					if (!socket.isClosed()) {
						stopClient();
						System.out.println("[서버 접속 실패]");
						Platform.exit();
					}
				}
			}
		};
		thread.start();
	}

	// 클라이언트 종료 함수
	private void stopClient() {
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 채팅 받는 함수
	public void receive() {
		while (true) {
			try {
				InputStream in = socket.getInputStream();
				byte[] buffer = new byte[512];
				int length = in.read(buffer);
				if (length == -1)
					throw new IOException();
				String message = new String(buffer, 0, length, "UTF-8");
				Platform.runLater(() -> {
					textArea.appendText(message);
				});
			} catch (Exception e) {
				// TODO: handle exception
				stopClient();
				break;
			}
		}
	}

	// 채팅 보내는 함수
	public void send(String message) {
		Thread thread = new Thread() {
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buffer = message.getBytes("UTF-8");
					out.write(buffer);
					out.flush();
				} catch (Exception e) {
					// TODO: handle exception
					stopClient();
				}
			}
		};
		thread.start();
	}

}
