//Name: James Cai
//Class: ICS4U - 01
//Date:2020/01/24
//Instructor: Mr.Raduovic
//Assignment name: Culminating Activity - 2D shooter
// Interface containing a method used to create circle of the unit in the game.

//import needed class.
import javafx.scene.shape.Circle;

public interface Units {
	
	//return x center of the circle
	public double getX();
	
	//return y center of the circle
	public double getY();
	
	//set x center of the circle by given double.
	public void setX(double x);
	
	//set y center of the circle by given double.
	public void setY(double y);
	
	//return shape of this unit.
	public Circle getShape();
	
	//set shape of this unit by given circle.
	public void setShape(Circle shape);
	
}
