package backend;

public class Queue {
    private int[] queArray;
    private int front;
    private int rear;

    public Queue () {
        queArray = new int[ConstParameters.SIZE];
        front = 0;
        rear = -1;
    }

    public void insert(int element) {
        if (rear == ConstParameters.SIZE - 1)
            rear = -1;

        queArray[++rear] = element;
    }

    public int remove() {
        int temp = queArray[front++];

        if (front == ConstParameters.SIZE)
            front = 0;

        return temp;
    }

    public boolean isEmpty() {
        return ( (rear + 1 == front) || (front + ConstParameters.SIZE - 1 == rear) );
    }
}
