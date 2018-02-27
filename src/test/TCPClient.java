package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	private static final String SERVER_IP = "192.168.1.10";
	private static final int SERVER_PORT = 5001;

	public static void main(String[] args) {
		Socket socket = null;

		try {
			// 1. 소켓 생성
			socket = new Socket();

			// 2.서버연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT), 5001);

			// 3.I/O Stream
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			// 4.쓰기/읽기
			String data = "hello";
			os.write(data.getBytes("utf-8"));

			byte[] buffer = new byte[256];
			int readByteCount = is.read(buffer);

			if (readByteCount == -1) {
				System.out.println("[client] diconnected by server");
				return;
			}

			data = new String(buffer, 0, readByteCount, "utf-8");
			System.out.println("[client] received:" + data);

		} catch (ConnectException e) {
			System.out.println("[client] Not Connected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
