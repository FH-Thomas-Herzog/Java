package swe4.threading;

public class Main {
	static public void main(String args[]) {
		Buffer b = new Buffer(4);
		Producer p1 = new Producer("Producer 1", b);
		Producer p2 = new Producer("Producer 2", b);
		Consumer c = new Consumer("Consumer", b);
		p1.start();
		c.start();
		p2.start();

	}
}
