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

	// ������
	public MainController() {

	}

	// ��Ʈ�ѷ� Ŭ���� �ʱ�ȭ. FXML ������ �ε�ǰ� ���� �ڵ����� ȣ��
	@FXML
	private void initialize() {

	}

	// �������� �����带 ȿ�������� �����ϱ� ���� ����ϴ� ��ǥ���� ���̺귯��
	public static ExecutorService threadPool;
	public static Vector<Client> clients = new Vector<Client>();

	ServerSocket serverSocket;

	// �����ϱ� ��ư �̺�Ʈ
	@FXML
	private void connectionEvent() {
		String IP = "127.0.0.1";
		int port = 9000;

		if (toggleButton.getText().equals("�����ϱ�")) {
			startServer(IP, port);
			Platform.runLater(() -> {
				String message = String.format("[���� ����]\n", IP, port);
				textArea.appendText(message);
				toggleButton.setText("�����ϱ�");
			});
		} else {
			stopServer();
			Platform.runLater(() -> {
				String message = String.format("[���� ����]\n", IP, port);
				textArea.appendText(message);
				toggleButton.setText("�����ϱ�");
			});
		}
	}

	// ������ �۵��� �����ϴ� �޼ҵ��Դϴ�.
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

		// Ŭ���̾�Ʈ�� �����ҋ����� ��� ��ٸ��� ������
		Runnable thread = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						Socket socket = serverSocket.accept();
						clients.add(new Client(socket));
						System.out.println("[Ŭ���̾�Ʈ ����] " + socket.getRemoteSocketAddress() + ": "
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

	// ������ �۵��� ������Ű�� �޼ҵ��Դϴ�.
	public void stopServer() {
		try {
			// ���� �۵� ���� ��� ���� �ݱ�
			Iterator<Client> iterator = clients.iterator();
			while (iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			// ���� ���� ��ü �ݱ�
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
