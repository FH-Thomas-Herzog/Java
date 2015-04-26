package at.fh.ooe.swe4.stack;

class StackValue {
	@Override
	public String toString() {
		return "Instance of my class";
	}
}

public class Stack1 {

	private int max;
	private int top;
	private Object[] data;

	public Stack1(int max) {
		super();
		if (max <= 0) {
			throw new IllegalArgumentException("Max must be greater than 0");
		}
		this.max = max;
		data = new Object[max];
		top--;
	}

	public boolean isEmpty() {
		return top < 0;
	}

	public boolean isFull() {
		return top == (max - 1);
	}

	public void push(final Object value) throws StackException {
		if (isFull()) {
			throw new StackException("Canot add value because stck isj already full !!!");
		}
		top++;
		data[top] = value;
	}

	public Object pop() throws StackException {
		if (isEmpty()) {
			throw new StackException("Cannot perfom pop on an empty stack !!!");
		}
		final Object value = data[top];
		top--;
		return value;
	}

	public static void main(String[] args) {
		final Stack1 stack = new Stack1(1);
		try {
			stack.push(new StackValue2());
			stack.push(new StackValue2());
		} catch (StackException e) {
			e.printStackTrace();
		}
	}

}
