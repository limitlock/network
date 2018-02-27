package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_ID = "192.168.1.10";
	private static final int SERVER_PORT = 6000;

	public static void main(String[] args) {

		Socket socket = new Socket();
		Scanner sc = new Scanner(System.in);

		try {
			socket.connect(new InetSocketAddress(SERVER_ID, SERVER_PORT));
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			while (true) {
				System.out.print(">>");
				String message = sc.nextLine();
				if ("exit".equals(message)) {
					break;
				}
				pw.println(message);
				String echoMessage = br.readLine();
				if (echoMessage == null) {
					System.out.println("[client] dissconnect");
					break;
				}
				System.out.println("<<" + echoMessage);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sc.close();
		}
	}

}
