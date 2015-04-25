package at.fh.ooe.net.socket.ssl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SSLClient {

	public static void main(String args[]) {
		BufferedReader biw = null;
		BufferedWriter bow = null;
		try {
			final Socket socket = createSSLServersocket();

			biw = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			bow = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));

			final String line = bufferRead.readLine();
			bow.write(line);
			bow.flush();

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private static Socket createSSLServersocket() throws Throwable {
		final KeyStore store = KeyStore.getInstance("JKS");
		store.load(new FileInputStream(
				"C:\\Users\\s1310307011\\SSL\\server.jks"), SSLServer.PASSWORD
				.toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
		kmf.init(store, SSLServer.PASSWORD.toCharArray());

		KeyManager[] km = kmf.getKeyManagers();

		TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");

		tmf.init(store);

		TrustManager[] tm = tmf.getTrustManagers();

		SSLContext sslCtx = SSLContext.getInstance("TLS");

		sslCtx.init(km, tm, null);

		SSLServerSocketFactory sslFactory = sslCtx.getServerSocketFactory();

		// Standard SocketFactory anfordern
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
				.getDefault();

		Socket sslsocket = sslsocketfactory.createSocket("localhost",
				SSLServer.PORT);
		((SSLSocket) sslsocket)
				.setEnabledCipherSuites(new String[] { "SSL_DH_anon_WITH_RC4_128_MD5" });

		return sslsocket;
	}
}
