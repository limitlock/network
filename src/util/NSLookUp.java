package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookUp {

	public static void main(String[] args) {

		try {
			while (true) {
				Scanner sc = new Scanner(System.in);
				String input_hostname = sc.nextLine();

				if (input_hostname.equals("exit")) {
					break;
				}

				InetAddress[] hostAddress = InetAddress.getAllByName(input_hostname);

				for (int i = 0; i < hostAddress.length; i++) {
					System.out.println(hostAddress[i]);
				}

			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
