package glazer.englard.tetris;

import java.util.LinkedList;

public class PriorityQueue {

	private LinkedList<Piece> list;

	public PriorityQueue() {
		this.list = new LinkedList<Piece>();
	}
	public void reset(){
		this.list.clear();
	}

	public void enqueue(Piece data) {
		list.add(data);
	}

	public Piece dequeue() {
		Piece data = list.removeFirst();
		return data;
	}

	public Piece peek() {
		return list.getFirst();
	}
	
	
	
}

