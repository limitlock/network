package time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPTimeClient {

	private static final String SERVER_IP = "192.168.1.10";
	private static final int SERVER_PORT = 5001;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		Scanner scanner = null;

		try {
			scanner = new Scanner(System.in);
			socket = new DatagramSocket();

			while (true) {
				System.out.print(">>");
				String message = scanner.nextLine();

				if ("quit".equals(message)) {
					break;
				}

				// 입력받은 문자열을 byte[]로 변환
				// getBytes : 유니코드 문자열을 UTF-8 캐릭터 바이트배열로 변환하여 반환
				byte[] sendData = message.getBytes("UTF-8");

				// 지정된 IP와 PORT를 통해 원하는 곳에 패킷을 보낸다.
				// (to the specified port number on the specified host.)
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(SERVER_IP, SERVER_PORT));

				// 전송
				socket.send(sendPacket);

				// 서버에서 오게 될 메세지 수신
				// 데이터는 receviePacket에 들어간다.
				DatagramPacket receviePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receviePacket);
				message = new String(receviePacket.getData(), 0, receviePacket.getLength(), "UTF-8");


				System.out.println("[Date] : " + message);
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}

	}

}
