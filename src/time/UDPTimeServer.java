package time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {

	private static final int PORT = 5001;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket(PORT);
			while (true) {
				// 수신을 위한 패킷 생성
				// (Constructs a DatagramPacket for receiving packets of length length.)
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);

				// 수신 대기 중 (Blocking)
				socket.receive(receivePacket);

				// 받은 패킷을 읽어 String message에 저장
				// String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");

				// 현재 날짜&시간
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String data = format.format(new Date());

				// byte 배열에 저장
				byte[] sendData = data.getBytes("UTF-8");

				// 패킷으로 포장?
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
						receivePacket.getPort());

				socket.send(sendPacket);
				
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
