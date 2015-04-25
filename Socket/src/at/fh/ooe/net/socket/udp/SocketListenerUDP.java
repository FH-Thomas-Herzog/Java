package at.fh.ooe.net.socket.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SocketListenerUDP {

	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			socket = new DatagramSocket(1200);
			boolean run = true;
			byte[] buffer;
			while (run) {
				buffer = new byte[1024];
				final DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(packet);
				System.out.println(new String(buffer, packet.getOffset(),
						packet.getLength(), "UTF-8"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}
}
