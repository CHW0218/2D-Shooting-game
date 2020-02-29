//Name: James Cai
//Class: ICS4U - 01
//Date:2020/01/24
//Instructor: Mr.Raduovic
//Assignment name: Culminating Activity - 2D shooter

//import needed class.
import javafx.scene.shape.Circle;

//Class that represent all the unit in the game like enemy.
public class Unit implements Units{
	private double speed;//speed of the unit
	private double total_health;//total health the unit had.
	private double current_health;//current health of the unit
	private int current_sframe,shooting_speed,current_dframe, damage_frame = 5,damage;
	private int type;//type of the unit. Enemy1,2,3 will be type 1,2,3, Player will be type zero and Bonus ball is -2, bullet is type -1.
	private Circle shape;//shape of the unit
	private boolean attacked = false;// is unit get attacked or not.
	
	public Unit(double speed,double health,Circle shape, int type, int shooting_speed, int damage) {
		//initialized the variable.
		this.speed = speed;
		this.total_health = health;
		this.shape = shape;
		this.type = type;
		this.damage = damage;
		current_health = health;
		this.shooting_speed = shooting_speed;
		current_sframe = 0;
		current_dframe = 0;
	}
	
	//return the speed of the unit.
	public double getSpeed(){
		return speed;
	}
	
	//set the speed of the unit by given double.
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	//return the type of the unit.
	public int getType(){
		return type;
	}
	
	//set y center of the circle by given double.
	@Override
	public void setY(double y)
	{
	    shape.setCenterY(y);
	}
	
	//return y center of the circle
	@Override
	public double getY(){
		return shape.getCenterY();
	}
	
	//set x center of the circle by given double.
	@Override
	public void setX(double x)
	{
		shape.setCenterX(x);
	}
	
	//return x center of the circle.
	@Override
	public double getX(){
		return shape.getCenterX();
	}
	
	//set total health of the unit by given double.
	public void setTotalhealth(double health)
	{
		this.total_health = health;
	}
	
	//return total health of the unit
	public double getTotalhealth(){
		return total_health;
	}
	
	//set current health of the unit by given health.
	public void setCurrenthealth(double health)
	{
		this.current_health = health;
	}
	
	//return total health of the unit.
	public double getCurrenthealth(){
		return current_health;
	}
	
	//subtract current health of the unit by given damage.
	public void LossHealth(double damage){
		this.current_health -= damage;
	}

	//return radius of circle of this unit.
	public double getRadius()
	{
		return shape.getRadius();
	}
	
	//set radius of this unit by given radius.
	public void setRadius(double Radius){
		shape.setRadius(Radius);
	}
	
	//return shape of this unit.
	@Override
	public Circle getShape(){
		return shape;
	}
	
	//set shape of this unit by given circle.
	@Override
 	public void setShape(Circle shape)
	{
 		this.shape = shape;
	}
 	
 	//return the current frame that count shooting or damage animation depends on the given str value.
 	@SuppressWarnings("null")
	public int getCurrentFrame(String str){
 		if(str.equals("S"))
 			return current_sframe ;
 		else if(str.equals("D"))
 			return current_dframe ;
 		else return (Integer) null;//if give a invalid value, return null.
	}
 	
 	//increase current frame that count shooting or damage animation of this unit by 1 depends on the given str value.
 	public void AddCurrentFrame(String str)
	{
 		if(str.equals("S"))
 			current_sframe ++;
 		else if(str.equals("D"))
 			current_dframe ++;
	}
 	
 	//reset current frame to zero depends on the given str value.
 	public void resetCurrentFrame(String str)
	{
 		if(str.equals("S"))
 			current_sframe = 0;
 		else if(str.equals("D"))
 			current_dframe = 0;
	}
 	
 	//return shooting speed of this unit.
 	public int getShootingSpeed(){
		return shooting_speed;
	}
 	
 	//set shooting speed of this unit by given integer.
 	public void setShootingSpeed(int ShootingSpeed)
	{
 		shooting_speed = ShootingSpeed;
	}
 	
 	//return total frame that damage animation will take.
 	public int getDamageFrame(){
		return damage_frame;
	}
 	
 	//set total frame that damage animation will take by given integer.
 	public void setDamageFrame(int damage_frame)
	{
 		this.damage_frame = damage_frame;
	}
 	
 	//set this unit get attacked or not by given boolean.
	public boolean setAttacked(boolean attacked){
		this.attacked = attacked;
		return attacked;
	}
	
	//return this unit get attacked or not.
	public boolean getAttacked(){
		return attacked;
	}
	
	//return attack damage of this unit
 	public int getDamage(){
		return damage;
	}
 	
 	//set attack damage of this unit by given integer.
 	public void setDamage(int Damage)
	{
 		damage = Damage;
	}

}

