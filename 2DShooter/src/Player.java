//Name: James Cai
//Class: ICS4U - 01
//Date:2020/01/24
//Instructor: Mr.Raduovic
//Assignment name: Culminating Activity - 2D shooter

//import needed class.
import javafx.scene.shape.Circle;

//Class that represent player in the game. All the method were inheritance from unit.
public class Player extends Unit {

	public Player(double speed, int health, Circle shape, int shooting_speed,int damage) {
		super(speed, health, shape,0,shooting_speed,damage);//initialize the super class, type for player is 0.
	}


}
