package thread;

public class MultiThreadEx {

	public static void main(String[] args) {

		// for (int i = 0; i < 10; i++) {
		// System.out.print(i);
		// }
		
		// for (char c = 'a'; c <= 'z'; c++) {
		// System.out.print(c);
		// }

		Thread thread01 = new AlphabetThread();
		Thread thread02 = new Thread(new DigitThread());
		
		thread01.start();
		thread02.start();

	}

}
