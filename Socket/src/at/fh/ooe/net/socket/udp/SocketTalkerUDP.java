package at.fh.ooe.net.socket.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketTalkerUDP {

	public static void main(String args[]) {
		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			boolean run = true;
			while (run) {
				final String line = bufferRead.readLine();
				if (line.trim().toLowerCase().equals("end")) {
					run = false;
				} else {
					final DatagramSocket socket = new DatagramSocket();
					socket.send(new DatagramPacket(line.getBytes(), line
							.getBytes().length, InetAddress
							.getByName("255.255.255.255"), 1200));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
