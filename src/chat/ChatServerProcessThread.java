package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ChatServerProcessThread extends Thread {
	private Socket socket;
	private String nickname;

	BufferedReader br = null;
	PrintWriter pw = null;

	List<Writer> listWriters;

	public ChatServerProcessThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		// 서버에 접근하는 클라이언트 객체 ( ip주소, Port번호 등등)
		InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		int remoteHostPort = remoteSocketAddress.getPort();
		String remoteHostAddress = remoteSocketAddress.getAddress().getHostAddress();
		System.out.println(" connected from  " + remoteHostAddress + ":" + remoteHostPort);

		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			while (true) {
				String request = br.readLine();
				if (request == null) {
					System.out.println("disconnected by client");
					doQuit(pw);
					break;
				}

				String[] tokens = request.split(":");
				System.out.println(tokens[0]);
				if ("join".equals(tokens[0])) {
					doJoin(tokens[1], pw);
				} else if ("message".equals(tokens[0])) {
					doMessage(tokens[1]);
				} else if ("quit".equals(tokens[0])) {
					doQuit(pw);
				} else {
					System.out.println("에러:알수 없는 요청(" + tokens[0] + ")");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doJoin(String nickname, Writer input_pw) {
		this.nickname = nickname;
		addWriter(input_pw);
		String data = nickname + "님이 참여하였습니다.";
		broadcast(data);

	}

	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void broadcast(String data) {
		synchronized (listWriters) {
			for (Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}

	private void doQuit(Writer writer) {
		removeWriter(writer);
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);

	}

	private void removeWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.remove(writer);
		}

	}

	private void doMessage(String message) {
		String data = nickname + ":" + message;
		broadcast(data);
	}

}
