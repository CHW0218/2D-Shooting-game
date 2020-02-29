// James Cai
// ICS4U1-01
// December 9, 2019
// Mr. Radulovic
//ADT Assignment
/*  This Class is used to create queue object and build queue's function by using array.In this assignment this class is used for bonus ball order. 
 * The maximum number of node this queue can hold will be 20 because in this assignment the bonus ball order will be a relatively small in length. Thus there 
 * is no need to have a very large list, in the game only 4 spot will be used, length of 20 is to prevent order overflow and the game crash. */

public class Queue {
	private Bonus_ball[] list;// array the used to implement the Queue
	int length;//Variable that used to track the length of the queue.
	public Queue()
	{
		list = new Bonus_ball[20];//Maximum size of the array.
		length = 0;
	}
	
	// adds a bonus ball to the end of the queue
	public void enqueue(Bonus_ball node) {
		list[length] = node;
		length++;//make the length +1
		
	}

	// removes the first bonus ball from the queue and return the bonus ball we removed.
	public Bonus_ball dequeue() {
		Bonus_ball remove = list[0];
		list[0] = null;
		for(int m = 0; m< length ; m++) {//Make every bonus ball move 1 index forward to fill the gap.
			list[m] = list[m+1];
		}length--;//make the length -1
		return remove;
	}

	// returns the first bonus ball of the queue without remove it.
	public Bonus_ball peek() {
		return list[0];
	}

	// returns the size of the queue
	public int size() {
		return length;
	}

	// returns true if the queue is empty, false if not
	public boolean isEmpty() {
		if(length == 0) {
			return true;
		}else {
		return false;
		}
	}
	
	// Return a string that contain all the effect of bonus ball in the queue.
	public String toString() {
		String str = "";
		for(int i = 0; i< length; i++) {
			str += list[i].getEffect()+" ";
		}
		return str;
	}
}
