package at.fh.ooe.net.socket.ssl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Random;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SSLServer {

	public static final String PASSWORD = "123456";
	public static final int PORT = 50000;

	public static void main(String args[]) {
		try {

			final SSLServerSocket sslSocket = createSSLServersocket();
			try {
				while (true) {
					Socket socket = sslSocket.accept();
					handleClient(socket);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				if ((sslSocket != null) && (!sslSocket.isClosed())) {
					sslSocket.close();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private static SSLServerSocket createSSLServersocket() throws Throwable {
		final KeyStore store = KeyStore.getInstance("JKS");
		store.load(new FileInputStream(
				"C:\\Users\\s1310307011\\SSL\\server.jks"), PASSWORD
				.toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
		kmf.init(store, PASSWORD.toCharArray());

		KeyManager[] km = kmf.getKeyManagers();

		TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");

		tmf.init(store);

		TrustManager[] tm = tmf.getTrustManagers();

		SSLContext sslCtx = SSLContext.getInstance("TLS");

		sslCtx.init(km, tm, null);

		SSLServerSocketFactory sslFactory = sslCtx.getServerSocketFactory();

		SSLServerSocket sslSocket = (SSLServerSocket) sslFactory
				.createServerSocket(PORT);

		((SSLServerSocket) sslSocket)
				.setEnabledCipherSuites(new String[] { "SSL_DH_anon_WITH_RC4_128_MD5" });

		return sslSocket;
	}

	private static void handleClient(final Socket socket) throws Throwable {
		BufferedReader biw = null;
		BufferedWriter bow = null;
		try {
			biw = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			bow = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			boolean run = Boolean.TRUE;
			int state = 0;
			Integer randomNumber = null;
			Integer value;

			while (run) {
				final String line = biw.readLine();
				System.out.println("Client request: " + line);
				// Initial request
				if (state == 0) {
					if (line.trim().toLowerCase().equals("hello")) {
						bow.write("OK");
						randomNumber = new Random(System.currentTimeMillis())
								.nextInt(1000) + 1;
						state = 1;
					} else {
						bow.write("I do not understand your request");
					}
				}
				// Number send
				else if (state == 1) {
					try {
						value = Integer.parseInt(line);
						if (randomNumber.compareTo(value) < 0) {
							bow.write("Greater");
						} else if (randomNumber.compareTo(value) > 0) {
							bow.write("Lower");
						} else {
							bow.write("You got it");
							state = 0;
						}
					} catch (NumberFormatException e) {
						bow.write("Invalid number '" + line + "'");
					}
				}
				bow.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (biw != null) {
				biw.close();
			}
			if (bow != null) {
				bow.close();
			}
		}
	}
}
