package backend;

public class Stack {

    private int[] stack;
    private int top;

    public Stack() {
        stack = new int[ConstParameters.SIZE];
        top = -1;
    }

    public void push(int element) {
        stack[++top] = element;
    }

    public int pop() {
        return stack[top--];
    }

    public int whatOnTop() {
        return stack[top];
    }

    public boolean isEmpty() {
        return (top == -1);
    }

}
