//Name: James Cai
//Class: ICS4U - 01
//Date:2020/01/24
//Instructor: Mr.Raduovic
//Assignment name: Culminating Activity - 2D shooter

//import needed class.
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

//Class that represent bonus ball in the game. Most of the method were inheritance from unit.
public class Bonus_ball extends Unit {
	Circle shape;
	Color color;

	int effect;//represent different type of effect of the bonus ball
	/* Number and effect it represent:
	 * 1,HealthBonus
	 * 2,AttackBonus
	 * 3,SpeedBonus
	 * 4,AtkSpeedBonus
	 */
	double BoostAmount;//represent the amount of the effect.
	
	public Bonus_ball(Circle shape, int effect,Color color,double BoostAmount) {
		super(0, 0, shape,-2,0,0);
		this.shape = shape;
		this.effect = effect;
		this.color = color;
		this.BoostAmount = BoostAmount;
	}
	
	//Set color of the bonus ball by given color.
	public void setColor(Color color) {
		this.color = color;
	}
	
	//Return color of the bonus ball.
	public Color getColor() {
		return color;
	}
	
	//Set effect of the bonus ball by given integer.
	public void setEffect(int effect) {
		this.effect = effect;
	}
	
	//Return effect of the bonus ball.
	public int getEffect() {
		return effect;
	}
	
	//Set amount of the effect by given double.
	public void setBoostAmount(double BoostAmount) {
		this.BoostAmount = BoostAmount;
	}
	
	//Return amount of the effect by given double.
	public double getBoostAmount() {
		return BoostAmount;
	}
}
