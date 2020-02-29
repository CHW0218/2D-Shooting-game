//Name: James Cai
//Class: ICS4U - 01
//Date:2020/01/24
//Instructor: Mr.Raduovic
//Assignment name: Culminating Activity - 2D shooter

//import needed class.
import javafx.scene.shape.Circle;

//Class that represent bullet in the game. Most of the method were inheritance from unit.
public class Bullet_circle extends Unit {
	private double x_dir,y_dir; //x,y coordinate of the direction bullet going.
	private double x_distance,y_distance;//distance from circle center to the pointing point.
	Circle shape;
	public Bullet_circle(double x_dir,double y_dir,Circle shape) {
		super(0, 0, shape,-1,0,0);
		this.x_dir = x_dir;
		this.y_dir = y_dir;
		this.shape = shape;
		
		//Calculate the x ,y distance.
		x_distance = x_dir - shape.getCenterX();
		y_distance = y_dir - shape.getCenterY();
	}
	
	//return distance of x axis from point where bullet being created to the pointing point. 
	public double getXdistance(){
		return x_distance;
	}
	
	//return distance of y axis from point where bullet being created to the pointing point. 
	public double getYdistance(){
		return y_distance;
	}
	
	//return x coordinate of the direction bullet going.
	public double getXdir(){
		return x_dir;
	}
	//set x coordinate of the direction bullet going by given double.
	 public void setXdir(double x_dir)
		{
		 this.x_dir = x_dir;
		}
	 
	//set y coordinate of the direction bullet going by given double.
	 public void setYdir(double y_dir)
		{
		 this.y_dir = y_dir;
		}
	 
	//return y coordinate of the direction bullet going
	public double getYdir(){
			return y_dir;
			
		}
}

