package application;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	// �������� �����带 ȿ�������� �����ϱ� ���� ����ϴ� ��ǥ���� ���̺귯��
	public static ExecutorService threadPool;
	public static Vector<Client> clients = new Vector<Client>();

	ServerSocket serverSocket;

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

	// UI�� �����ϰ�, ���������� ���α׷��� ���۽�Ű�� �޼ҵ��Դϴ�.
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("ä�� ����");
			primaryStage.setResizable(false);
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
