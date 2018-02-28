package chat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientApp {

	private static final String SERVER_ID = "192.168.1.10";
	private static final int SERVER_PORT = 6000;

	public static void main(String[] args) throws IOException {

		String name = null;
		Scanner scanner = new Scanner(System.in);
		Socket socket = null;

		try {

			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_ID, SERVER_PORT));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			while (true) {

				System.out.println("대화명을 입력하세요.");
				System.out.print(">>> ");
				name = scanner.nextLine();
				System.out.println(name);

				String data = "join:" + name;
				pw.println(data);
				pw.flush();

				if (name.isEmpty() == false) {
					break;
				}
				System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
			}
			new ChatWindow(name, socket).show();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
