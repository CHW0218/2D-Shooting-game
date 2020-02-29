// James Cai
// ICS4U1-01
// December 9, 2019
// Mr. Radulovic
//ADT Assignment
/*  This class is used to create ArrayList objects of Unit and implement arraylist's function by using array. 
 * The default size is set to 100000 because the number of unit on screen in this game won't be too much.*/

public class ArrayList {

	private Unit [] list;// Array used to implement the ArrayList
	int length;//Variable that used to track the length of the arraylist.
	public ArrayList()
	{
		list = new Unit[100000];
		length = 0;
	}

	// Adds a Unit to the end of the arraylist.
	public void add(Unit n) {
		list[length] = n;
		length++;//make the length +1
		
	}

	// insert a Unit to the given index.
	public void insert(Unit n, int i) {
		for(int m = length-1; m>= i ; m--) {
			list[m+1] = list[m];//Make every after given index node move 1 index more to let a new node insert to the arraylist.
		}list[i] = n;
		length++;//make the length +1
	}

	// Remove Unit on the arraylist, if target Unit is given.
	public void remove(Unit n) {
		@SuppressWarnings("null")
		int target = (Integer) null;/*Variable that used to mark the index of the Unit we wants to remove. 
									  initialize it by null to prevent it remove any Unit of the given Unit is not found.*/
		for(int m = 0; m < length ; m++) {//go through the arraylist to find the given Unit.
			if(list[m] == n) {//if Unit found, remove it.
				target = m;
				list[m]=null;
				break;
			}
		}
		//Make every Unit after removed Unit move 1 index forward to fill the gap.
		for(int m = target; m< length ; m++) {
			list[m] = list[m+1];
		}length--;//make the length -1
	}
	
	// Remove Unit of given index on the arraylist then return it, if index of target Unit is given.
	public Unit remove(int i) {	
		Unit target = list[i];//store the removed Unit.
		list[i]=null;
		for(int m = i; m< length ; m++) {//Make every Unit after removed Unit move 1 index forward to fill the gap.
			list[m] = list[m+1];
		}length--;
		return target;
	}

	//Return the Unit on the given index
	public Unit get(int i) {
		return list[i];
	}

	//Return the first Unit on the arraylist
	public Unit getFirstNode() {
		return list[0];
	}

	//Return the last Unit on the arraylist
	public Unit getLastNode() {
		return list[length-1];
	}
	public boolean isEmpty() {
		if(length != 0) {
			return false;
		}else {
			return true;
		}
		
	}
	//Return the size of the arraylist
	public int size() {
		return length;
	}
	
	// Return a string that contain all the value of Unit in the ArrayList.
	public String toString() {
		String str = "";
		for(int i = 0; i< length; i++) {
			str += list[i].getType()+" ";
		}
		return str;
	}
}
