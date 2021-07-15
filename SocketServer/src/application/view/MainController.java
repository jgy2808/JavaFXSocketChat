package application.view;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class MainController {

	@FXML
	private TextArea textArea;
	@FXML
	private Button toggleButton;

	// 생성자
	public MainController() {

	}

	// 컨트롤러 클래스 초기화. FXML 파일이 로드되고 나서 자동으로 호출
	@FXML
	private void initialize() {

	}

	// 여러개의 스레드를 효율적으로 관리하기 위해 사용하는 대표적인 라이브러리
	public static ExecutorService threadPool;
	public static Vector<Client> clients = new Vector<Client>();

	ServerSocket serverSocket;

	// 시작하기 버튼 이벤트
	@FXML
	private void connectionEvent() {
		String IP = "127.0.0.1";
		int port = 9000;

		if (toggleButton.getText().equals("시작하기")) {
			startServer(IP, port);
			Platform.runLater(() -> {
				String message = String.format("[서버 시작]\n", IP, port);
				textArea.appendText(message);
				toggleButton.setText("종료하기");
			});
		} else {
			stopServer();
			Platform.runLater(() -> {
				String message = String.format("[서버 종료]\n", IP, port);
				textArea.appendText(message);
				toggleButton.setText("시작하기");
			});
		}
	}

	// 서버의 작동을 실행하는 메소드입니다.
	public void startServer(String IP, int port) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(IP, port));
		} catch (Exception e) {
			// TODO: handle exception
			if (!serverSocket.isClosed()) {
				stopServer();
			}
			return;
		}

		// 클라이언트가 접속할떄까지 계속 기다리는 스레드
		Runnable thread = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						Socket socket = serverSocket.accept();
						clients.add(new Client(socket));
						System.out.println("[클라이언트 접속] " + socket.getRemoteSocketAddress() + ": "
								+ Thread.currentThread().getName());
					} catch (Exception e) {
						// TODO: handle exception
						if (!serverSocket.isClosed()) {
							stopServer();
						}
						break;
					}
				}
			}
		};
		threadPool = Executors.newCachedThreadPool();
		threadPool.submit(thread);
	}

	// 서버의 작동을 중지시키는 메소드입니다.
	public void stopServer() {
		try {
			// 현재 작동 중인 모든 소켓 닫기
			Iterator<Client> iterator = clients.iterator();
			while (iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			// 서버 소켓 객체 닫기
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			if (threadPool != null && !threadPool.isShutdown()) {
				threadPool.shutdown();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
