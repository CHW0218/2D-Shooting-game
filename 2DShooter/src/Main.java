//Name: James Cai
//Class: ICS4U - 01
//Date:2020/01/24
//Instructor: Mr.Raduovic
//Assignment name: Culminating Activity - 2D shooter
/*brief description of program: This program is a 2D game played by a single player on a 1600X900 window. 
 * The player control a grey ball on that have 150 health, 5 speed, 8 reload speed, and 5 attack at the beginning,
 * the player can press"WASD" to move around and pressed left mouse button to shoot bullet and kill the black ball, 
 * which is enemy. The main goal in this game is to stay alive as long as possible. For every second player stay alive, 
 * they will score a point. On the screen different kind of enemy will spawn every certain period of time. 
 * The smallest enemy will try to touch the player and they will spawn every 2 seconds. The middle size enemy can't 
 * move but they will aim and shoot player and they will spawn every 6 seconds. The largest enemy will move horizontally
 * on the screen and shoot bullet in four direction, they will spawn every 15 seconds. However, when player getting more 
 * and more point, there will be more and more enemy spawn on the screen. The number of smallest enemy spawn will increase 
 * every 20 seconds, the number of middle size will increase every 36 seconds, for largest one it take 45 seconds to increase.
 * If player kill a enemy, there is a chance it drop a bonus ball. Player's stats will increase after get a bonus ball. 
 * Different bonus ball can increase different abilities, the green ball can restore 100 health, the red ball will 
 * increase player's attack by 3,the yellow ball can increase player's speed and the purple ball can reduce player's reload time,
 * which is increase shooting speed. 
 */

//import all the needed class to run our program.
import java.util.Date;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;  

//the main class of the program
public class Main extends Application { 

	//Create the needed Objects and variables that needed to run the program.
	private double  width = 900, height = 1600; // The width and height of the game window.
	private double order_x = 0, order_y = 0;// Variable that use to store the cursor position.
	private double player_x = height/2 ,player_y = width/2; // initialized player's spawn position. Middle of the window.
	private double ratio,x_distance,y_distance;//Variable that use to calculate slop form a point to cursor position. Used in enemy and bullet movement.
	private int bullet_size = 10,bullet_speed = 10; //Parameter of bullet in the game.
	
	//Parameter of player.
	private double player_speed = 5; //player's move speed.
	private int player_radius = 30
			,player_damage = 5 // damage that player's bullet deal.
			,player_health = 150//player's health.
			,Reload_speedP = 8;//Frame that player take to reload.
	private Circle player_model = new Circle(player_x, player_y, player_radius);//circle that represent player.
	private Player player = new Player(player_speed,player_health ,player_model, Reload_speedP ,player_damage);//create player in the game.
	
	//Parameter of enemy.
	private int enemy_damage = 5;// damage that enemy deal.
	private int enemy1_randius = 20 , enemy1_health = 20,enemy1_speed = 3;//Parameter of circle that represent smallest enemy.
	private int enemy2_randius = 40 , enemy2_health = 50,enemy2_speed = 0;//Parameter of circle that represent middle size enemy.
	private int enemy3_randius = 80 , enemy3_health = 120,enemy3_speed = 1;//Parameter of circle that represent largest enemy.
	private int Reload_speed2E = 20,Reload_speed3E = 40;//Frame that enemy take to reload.
	//2D array that contain parameter of enemy that can used generate enemy later in the game.
	private int[][] enemy_stat = {
			{enemy1_speed,enemy1_health,enemy1_randius,0},//because smallest enemy can't shoot, so the reload speed is zero.
			{enemy2_speed,enemy2_health,enemy2_randius,Reload_speed2E},
			{enemy3_speed,enemy3_health,enemy3_randius,Reload_speed3E}};
	private int spawnEnemy1 = 0,spawnEnemy2 = 0,spawnEnemy3 = 0;// Variable that used to record when did enemy spawn last time.
	
	private int BonusBall_radius = 15;//Radius of bonus ball.
	private int second, score;//Record the score player get and how many second passed since game started.
	private boolean gameover = false;//game over boolean
	private boolean move_up = false, move_down=false,move_right = false, move_left = false;//boolean that determine which direction player go.
	private boolean isShooting = false;//Is player shooting or not.
	
	private final Date createdDate = new java.util.Date(); //The current time when user run this program.
	
	// Initializes playing field.
	private Group group= new Group();
	private Scene scene =  new Scene(group, height, width);
	
	private ArrayList player_bullet = new ArrayList();//ArrayList that store all the bullet units in the game.
	private ArrayList enemy_bullet = new ArrayList();//ArrayList that store all the enemy's bullet units in the game.
	private ArrayList bonus_list  = new ArrayList();//ArrayList that store all the bonus balls units in the game.
	private ArrayList enemy_list = new ArrayList();//ArrayList that store all the enemy units in the game.
	
	private Queue bonus_queue = new Queue();//Queue that control the drop order of 4 different kind of bonus balls. 
	private Circle BonusBall_circle = new Circle();//circle that represent BonusBall.
	private Bonus_ball HealthBonus = new Bonus_ball(BonusBall_circle, 1,Color.GREEN,100);//Bonus ball that increase player's health.
	private Bonus_ball AttackBonus = new Bonus_ball(BonusBall_circle, 2,Color.RED,3);//Bonus ball that increase player's Attack.
	private Bonus_ball SpeedBonus = new Bonus_ball(BonusBall_circle, 3,Color.ORANGE,0.5);//Bonus ball that increase player's Speed.
	private Bonus_ball AtkSpeedBonus = new Bonus_ball(BonusBall_circle, 4,Color.PURPLE,1);//Bonus ball that increase player's Shooting Speed.

	private Rectangle Healthbar_red,Healthbar_green;//Rectangle that used to draw health bar in the game.
	
	private Text second_text = new Text(); //text that used to display how many second passed in the game.
	
	//Main game loop.
	public void draw(){
			timer (second_text);//display how many second passed
			
			move();//Moving player.
			
			enemy_generator(second);//generate enemy in the game.
			
			Enemy_behavior(enemy_list);//calculate enemy's behavior
			
			//if left mouse button is pressed, then shoot bullet.
			if(isShooting == true) {
				Shooting();
			}
			
			bullet_movement(player_bullet);//calculate movement of player's bullet
			bullet_movement(enemy_bullet);//calculate movement of enemy's bullet
			
			getBonus(bonus_list);//calculate if player get any bonus ball
			
			enemy_attacked(player_bullet,enemy_list);//check if enemy get hit by player's bullet
			player_attacked(enemy_bullet, enemy_list);//check if player get hit by enemy's bullet or touched enemy.
			
			//if play get hit, then play damaged animation on player
			if( player.getAttacked() == true) {
				attacked(player);
			}
			
			//if any enemy get hit, then play damaged animation on them
			for(int i = 0; i< enemy_list.size();i++) {
				if( enemy_list.get(i).getAttacked() == true) {
					attacked(enemy_list.get(i));
				}
			}
			
			enemy_dead(enemy_list);//check if any enemy dead
			
			//Check if game end.
			if(gameover == true) {
				gameover();//If game end, show game over and display player's best score.
			}
	}
	
	//display how many second passed
	public void timer (Text second_text) {
		java.util.Date now = new java.util.Date();//get the time now
		
		//subtract the time right now to the time when we start the game to get how many time passed, then convert it to second.
        second = (int)((now.getTime() - this.createdDate.getTime()) / 1000);
        
        second_text.setText(String.valueOf(second));//set text to the second we just calculated.
        group.getChildren().removeAll(second_text);//remove previous seconds text on the screen.
        
        //set x, y coordinate of the text.
        second_text.setX(height/2-50); 
        second_text.setY(100); 
        
        //set font
        second_text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 100)); 
        
        //set color
        second_text.setFill(Color.BLACK); 
        
        //added it to the screen.
	    group.getChildren().add(second_text);
	}
	
	//Calculate player's movement.
	public void move(){		
		//if player want to moving and it did't crossed border of the window, then change the x,y coordinate of the player.
		if(move_up&& player.getY() - player_radius >= 0){
			player.setY(player.getY()- player.getSpeed());//Set the Y coordinate after play move up "Speed" units.
		}if(move_down&& player.getY() + player_radius <= width){
			player.setY(player.getY()+ player.getSpeed());//Set the Y coordinate after play move down "Speed" units.
		}if(move_left&& player.getX() - player_radius >= 0){
			player.setX(player.getX()- player.getSpeed());//Set the X coordinate after play move left "Speed" units.
		}if(move_right&& player.getX() + player_radius <= height){
			player.setX(player.getX()+ player.getSpeed());//Set the X coordinate after play move right "Speed" units.
		}
		
		//apply these x,y coordinate to circle that represent player.
		player_model.setCenterX(player.getX());
		player_model.setCenterY(player.getY());
	}
	
	//generate enemy in the game.
	public void enemy_generator(int second){
		//for every 2 second passed, spawn enemy1. 
		//"spawnEnemy1" used to prevent enemy1 get spawn multiply time when animeTimer runs game loop over and over again.
		if(second % 2 == 0 && second != spawnEnemy1) {
			spawnEnemy1 = second;
			for(int i = 0; i<(second/20+1);i++) {//for every 20 seconds, increase the number of enemy1 that spawned by 1.
				enemy_spawn(1);
			}
		//same as enemy 1,spawn every 6 seconds.
		}if(second % 6 == 0 && second != spawnEnemy2) {
			spawnEnemy2 = second;
			for(int i = 0; i<(second/36+1);i++) {//for every 36 seconds, increase the number of enemy2 that spawned by 1.
				enemy_spawn(2);
			}
			//same as enemy 1,spawn every 15 seconds.
		}if(second % 15 == 0 && second != spawnEnemy3) {
			spawnEnemy3 = second;
			for(int i = 0; i<(second/45+1);i++) {//for every 45 seconds, increase the number of enemy3 that spawned by 1.
				enemy_spawn(3);
			}
		}
	}
	
	//Check if the circle are overlap each other.
	public boolean isCollision(double x1, double y1,double radius1, double x2, double y2, double radius2){
		//calculate absolute x and y distance.
		double x_distance = Math.abs(x1-x2);
		double y_distance = Math.abs(y1-y2);
		
		//calculate the length of distance between two point.
		double distance_between = Math.sqrt(x_distance*x_distance + y_distance*y_distance);
		
		//if distance less than radius of two circle add up together, return true. Else return false.
		if(distance_between<= radius1+radius2) {
			return true;
		}else {
			return false;
			
		}
	}
	
	//Add enemy in to the game.
	public void enemy_spawn(int type) {
		type--;// because 2D array's index start from zero, so type -1.
		boolean overlap = false;// boolean to record if enemy spawn overlap with other circle.
		boolean spawn = false;// boolean to record if enemy spawned successfully.
		int maximum_try = 10000 , count = 0;// int to limited maximum time the program can try generate random number.
		//While enemy didn't spawn successfully.
		while(spawn == false&& count != maximum_try) {//Imitated maximum time it can try to avoid infinite loop.
			//generate a random position in the window.
			//minimum of enemy radius and maximum of height/width - enemy radius to prevent enemy spawn outside of the border.
			double spawn_positionX = (Math.random() * (height - enemy_stat[type][2]) + enemy_stat[type][2]);
			double spawn_positionY = (Math.random() * (width - enemy_stat[type][2]) + enemy_stat[type][2]);
			for(int m = 0; m< enemy_list.size();m++) {//check with other enemy to see if this random position overlap with other circle.
				if(isCollision(spawn_positionX,spawn_positionY,enemy_stat[type][2],enemy_list.get(m).getX(), 
								enemy_list.get(m).getY(), enemy_list.get(m).getShape().getRadius())
					//check if this random position overlap with other player. Add 150 to player's radius to prevent enemy spawn vary near to player. 
					||isCollision(spawn_positionX,spawn_positionY,enemy_stat[type][2],player.getX(), player.getY(), player.getRadius()+150)) {
							//if overlap, regenerate position.
							overlap = true;
							break;
							
				}
			}
			
			//if did not overlap with any circle, spawn enemy on that position.
			if(overlap == false) {
				
				//Access parameter of enemy's model from 2D array then create new enemy unit.
				Circle enemy_circle = new Circle(spawn_positionX,spawn_positionY,enemy_stat[type][2]);
				Unit enemy = new Unit(enemy_stat[type][0],enemy_stat[type][1],enemy_circle,type+1,enemy_stat[type][3],enemy_damage);
				
				//add it to the screen and enemy list, then stop loop
				enemy_list.add(enemy);
				group.getChildren().add(enemy_circle);
				spawn = true;
				
			}else {//reset overlap to test the new position regenerated.
				overlap = false;
				count++;
			}
		}
		
	}
	
	//calculate enemy's behavior
	public void Enemy_behavior(ArrayList enemy_list){
		//go through every enemy unit currently in the game.
		for(int i = 0; i< enemy_list.size();i++) {
			Unit enemy = enemy_list.get(i);
			
				//if enemy type equals 1.
				//enemy1 are try to touch player in order to damage player. So enemy1 will moving to player's position.
				//* enemy1 will shaking when touched player's circle center because it keep adding and subtracting "speed" value
				//* to make it's circle center equal to player's. Want to remove previously but found that it looks cool as enemy1 attack animation 
				//* so it didn't get fixed.
				if(enemy.getType() == 1) {
					//calculate the distance from enemy to player
					x_distance = Math.abs(player.getX() - enemy.getX());
					y_distance = Math.abs(player.getY() - enemy.getY());
						
						//If distance on x axis greater than distance on y axis.
						if(x_distance > y_distance) {
							//ratio of y axis to x axis in order to make x,y of the circle arrive target point at the same time.
							ratio = (y_distance/ x_distance);
							
							//enemy moving.
							if(player.getX()>= enemy.getX()){
								enemy.setX(enemy.getX()+enemy.getSpeed());
							}else {
								enemy.setX(enemy.getX()-enemy.getSpeed());
							}
							//times ratio to make circle move slower on shorter axis.
							if(player.getY() >= enemy.getY()) {
								enemy.setY(enemy.getY()+enemy.getSpeed()*ratio);
							}else {
								enemy.setY(enemy.getY()-enemy.getSpeed()*ratio);
							}
						}else {//If distance on y axis greater than distance on x axis.
							ratio = x_distance/ y_distance;//ratio of x axis to y axis
							
							//enemy moving.
							if(player.getY() >= enemy.getY()) {
								enemy.setY(enemy.getY()+enemy.getSpeed());
							}else {
								enemy.setY(enemy.getY()-enemy.getSpeed());
							}
							if(player.getX()>= enemy.getX()) {//times ratio to make circle move slower on shorter axis.
								enemy.setX(enemy.getX()+enemy.getSpeed()*ratio);
							}else {
								enemy.setX(enemy.getX()-enemy.getSpeed()*ratio);
							}
						}
						
						//apply these x,y coordinate to circle on the screen.
						enemy.getShape().setCenterX(enemy.getX());
						enemy.getShape().setCenterY(enemy.getY());
			}
				//if enemy type equals 2.
				//enemy2 aim and shoot bullet to the player
				if(enemy.getType() == 2) {
					//Shoot bullet every "ShootingSpeed" frame. Prevent 'machine gun' effect which enemy shoots very fast.
					if(enemy.getCurrentFrame("S") >= enemy.getShootingSpeed()) {
						
						//create bullet on enemy2 position, then add it to the game and enemy bullet list. 
						Circle bullet_shape = new Circle(enemy.getX(), enemy.getY(), bullet_size);
						bullet_shape.setFill(Color.BLACK);
						//set the pointing point to player's x,y coordinate.
						Bullet_circle bullet = new Bullet_circle(player.getX(),player.getY(),bullet_shape);
						enemy_bullet.add(bullet);
						group.getChildren().add(bullet.getShape());
						enemy.resetCurrentFrame("S");
					}
				//use to count frame between shooting.
				enemy.AddCurrentFrame("S");
				}
				
				//if enemy type equals 3.
				//enemy3 shoot bullets on four direction and moving horizontally slowly.
				if(enemy.getType() == 3) {
					
					//enemy3 shoot bullet on four directly and moving horizontal slowly.
					//if reaching the edge of the window, turn to another direction.
					if(enemy.getX()+enemy.getSpeed() + enemy.getRadius()>= scene.getWidth()) {
						enemy.setSpeed(enemy.getSpeed()*-1);
					}else if(enemy.getX()+enemy.getSpeed() - enemy.getRadius()<= 0) {
						enemy.setSpeed(enemy.getSpeed()*-1);
					}
					enemy.setX(enemy.getX()+enemy.getSpeed());
					
					//Create bullets on up down, left and right direction.
					//same as enemy2, shooting bullet every "ShootingSpeed" frame.
					if(enemy.getCurrentFrame("S") >= enemy.getShootingSpeed()) {
						Circle bullet_shape1 = new Circle(enemy.getX(), enemy.getY(), bullet_size);
						Circle bullet_shape2 = new Circle(enemy.getX(), enemy.getY(), bullet_size);
						Circle bullet_shape3 = new Circle(enemy.getX(), enemy.getY(), bullet_size);
						Circle bullet_shape4 = new Circle(enemy.getX(), enemy.getY(), bullet_size);
						bullet_shape1.setFill(Color.BLACK);
						bullet_shape2.setFill(Color.BLACK);
						bullet_shape3.setFill(Color.BLACK);
						bullet_shape4.setFill(Color.BLACK);
						Bullet_circle bullet1 = new Bullet_circle(enemy.getX()+10,enemy.getY(),bullet_shape1);
						Bullet_circle bullet2 = new Bullet_circle(enemy.getX()-10,enemy.getY(),bullet_shape2);
						Bullet_circle bullet3 = new Bullet_circle(enemy.getX(),enemy.getY()-10,bullet_shape3);
						Bullet_circle bullet4 = new Bullet_circle(enemy.getX(),enemy.getY()+10,bullet_shape4);
						enemy_bullet.add(bullet1);
						enemy_bullet.add(bullet2);
						enemy_bullet.add(bullet3);
						enemy_bullet.add(bullet4);
						group.getChildren().add(bullet1.getShape());
						group.getChildren().add(bullet2.getShape());
						group.getChildren().add(bullet3.getShape());
						group.getChildren().add(bullet4.getShape());
						enemy.resetCurrentFrame("S");
					}
				enemy.AddCurrentFrame("S");
				}
		}
	}
	
	//Shooting action for player, create that moving to cursor pointing direction.
	public void Shooting(){
		//Shooting every "ShootingSpeed" frame. Prevent 'machine gun' effect which player will shoot very fast.
		if(player.getCurrentFrame("S") >= player.getShootingSpeed()) {
			//create bullet then add then to the game.
			Circle bullet_shape = new Circle(player.getX(), player.getY(), bullet_size);
			bullet_shape.setFill(Color.GREY);
			Bullet_circle bullet = new Bullet_circle(order_x,order_y,bullet_shape);
			player_bullet.add(bullet);
			group.getChildren().add((Node) bullet.getShape());
			player.resetCurrentFrame("S");

		}
		player.AddCurrentFrame("S");
	}
	
	//Calculate the movement of the bullet.
	public void bullet_movement(ArrayList bullet_list){
		//go through every bullets on the list.
		for(int i = 0; i< bullet_list.size();i++) {
			//calculate the distance from player to pointing point.
			//calculate direction when created to make sure the shooting direction didn't change when the pointing point changed.
			Bullet_circle bullet = (Bullet_circle) bullet_list.get(i);
			x_distance = bullet.getXdistance();
			y_distance = bullet.getYdistance();
				//If distance on x axis greater than distance on y axis.
				if(Math.abs(x_distance)> Math.abs(y_distance)) {
					//ratio of y axis to x axis in order to make x,y of the circle arrive target point at the same time.
					ratio = (Math.abs(y_distance)/ Math.abs(x_distance));
					
					//different from enemy1 movement, bullet will keep moving on the same direction when reaching the target point. 
					//moving the bullet
					if(x_distance>=0) {
						bullet.setX(bullet.getX()+bullet_speed);
					}else {
						bullet.setX(bullet.getX()-bullet_speed);
					}
					//moving the shorter distance in ratio to make both x and y reach pointing point at the same time.
					if(y_distance>=0) {
						bullet.setY(bullet.getY()+bullet_speed*ratio);
					}else {
						bullet.setY(bullet.getY()-bullet_speed*ratio);
					}
				}else {//if distance on y axis greater than distance on x axis. Apply ratio on other axis.
					ratio = (Math.abs(x_distance)/ Math.abs(y_distance));
					if(y_distance>=0) {
						bullet.setY(bullet.getY()+bullet_speed);
					}else {
						bullet.setY(bullet.getY()-bullet_speed);
					}
					if(x_distance>=0) {
						bullet.setX(bullet.getX()+bullet_speed*ratio);
					}else {
						bullet.setX(bullet.getX()-bullet_speed*ratio);
					}
				}
				//apply calculated x, y to the game.
				((Circle) bullet.getShape()).setCenterX(bullet.getX());
				((Circle) bullet.getShape()).setCenterY(bullet.getY());
		}
	}

	//Check if player get a bonus ball then give the player bonus.
	public void getBonus(ArrayList bonus_list) {
		for(int m = 0; m< bonus_list.size();m++) {
			Bonus_ball bonus = (Bonus_ball) bonus_list.get(m);
			//If play collide with the bonus ball, given player bonus depends on what kind of bonus ball it is.
			if(isCollision(player.getX(),player.getY(),player.getRadius(),bonus.getX(), bonus.getY(), bonus.getShape().getRadius())) {
				//get the effect of the bonus ball.
				int effect = bonus.getEffect();
				//if it is first type of effect, restore "BoostAmount" health to the player.
				if(effect == 1) {
					if(player.getCurrenthealth()+bonus.getBoostAmount()<= player.getTotalhealth()) {
						player.setCurrenthealth((player.getCurrenthealth()+bonus.getBoostAmount()));
					}else {//if player was full health for lose less than "BoostAmount" health, restore to full health.
						player.setCurrenthealth(player.getTotalhealth());
					}
					
				//if it is second type of effect, increase player's attack damage.
				}if(effect == 2) {
					//add "BoostAmount" damage to player.
					player.setDamage((int)(player.getDamage()+bonus.getBoostAmount()));
				}
				//Increase player's speed by "BoostAmount".
				if(effect == 3) {
					player.setSpeed(player.getSpeed()+bonus.getBoostAmount());
				}//Increase player's shooting speed by deduce the time to reload bullet by "BoostAmount".
				if(effect == 4) {
					if((int)(player.getShootingSpeed()-bonus.getBoostAmount())>=2){
						player.setShootingSpeed((int)(player.getShootingSpeed()-bonus.getBoostAmount()));
					}else {
						player.setShootingSpeed(2);
					}
				}
				//remove the bonus ball after player get it.
				group.getChildren().remove(bonus.getShape());
				bonus_list.remove(m);
			}
		}
	}
	
	//check if enemy get attacked by player's bullet
	public void enemy_attacked(ArrayList bullet_list,ArrayList enemy_list) {
		for(int i = 0; i< bullet_list.size();i++) {
			Bullet_circle bullet = (Bullet_circle) bullet_list.get(i);
			for(int m = 0; m< enemy_list.size();m++) {
				Unit enemy = enemy_list.get(m);
				//check for every bullet and enemy if they collide each other.
				if(isCollision(bullet.getX(),bullet.getY(),bullet_size,enemy.getX(), enemy.getY(), enemy.getShape().getRadius())) {
					// if enemy and bullets collide each other, remove the bullet and set this enemy get attacked.
					group.getChildren().remove(bullet.getShape());
					//to prevent a bullet hit two enemy at the same time so that a bullet 
					//is being removed twice then caused out of bound error when there is 
					//only one bullet in the list. Check if the bullet list is not empty before removing.
					if(!bullet_list.isEmpty()) {
						bullet_list.remove(i);
					}
					enemy.setAttacked(true);
					break;
				}
			}
		}
	}

	//check if player get attacked by enemy's bullet or get touched by the enemy.
	public void player_attacked(ArrayList bullet_list,ArrayList enemy_list) {
		for(int i = 0; i< bullet_list.size();i++) {
			Bullet_circle bullet = (Bullet_circle) bullet_list.get(i);
				// if player and enemy's bullets collide each other, remove the bullets and set player get attacked.
				if(isCollision(bullet.getX(),bullet.getY(),bullet_size,player.getX(), player.getY(), player_radius)) {
						group.getChildren().remove(bullet.getShape());
						bullet_list.remove(i);
						player.setAttacked(true);
						break;
			}
		}
		// if player and enemy collide each other, set player get attacked.
		for(int m = 0; m< enemy_list.size();m++) {
			Unit enemy = enemy_list.get(m);
			if(isCollision(player.getX(),player.getY(),player.getRadius(),enemy.getX(), enemy.getY(), enemy.getShape().getRadius())) {
				player.setAttacked(true);
				break;
			}
		}
	}

	//displayer the health bar in the game.
	public void health_bar(Unit unit){
		//remove all previous health bar if there is any in the screen.
		group.getChildren().removeAll(Healthbar_red);
		group.getChildren().removeAll(Healthbar_green);
		//draw the new health bar above unit, length of the green bar depends on how many health the unit left.
		Healthbar_red = new Rectangle(unit.getX() - unit.getRadius(), unit.getY()-unit.getRadius()-20, 2*unit.getRadius(), 10);
		Healthbar_green = new Rectangle(unit.getX() - unit.getRadius(), unit.getY()-unit.getRadius()-20, 2*unit.getRadius()*(unit.getCurrenthealth()/unit.getTotalhealth()), 10);
		Healthbar_red.setFill(Color.RED);
		Healthbar_green.setFill(Color.GREEN);
		group.getChildren().add(Healthbar_red);
		group.getChildren().add(Healthbar_green);
	}
	
	//Animation when enemy get attacked.
	//Over load method
	public void attacked(Unit enemy){
			//display the health bar of the enemy and then set the enemy to red.
			enemy.getShape().setFill(Color.RED);
			health_bar(enemy);
			//remove the health bar and then set enemy back to black after few frame.
			if(enemy.getCurrentFrame("D") == enemy.getDamageFrame()) {
				enemy.getShape().setFill(Color.BLACK);
				enemy.resetCurrentFrame("D");
				enemy.setAttacked(false);
				enemy.LossHealth(player.getDamage());//Subtract health by player's damage.
				group.getChildren().removeAll(Healthbar_red);
				group.getChildren().removeAll(Healthbar_green);
			}
			//display animation for few frame.
			enemy.AddCurrentFrame("D");
	}
	
	//Method that display Animation when player get attacked.
	//Over load method
	public void attacked(Player player){
		//display the health bar of the player and then set the player's circle to red.
		player.getShape().setFill(Color.RED);
		health_bar(player);
		//remove the health bar and then set player back to grey after few frame.
		if(player.getCurrentFrame("D") == player.getDamageFrame()) {
			player.getShape().setFill(Color.GREY);
			player.resetCurrentFrame("D");
			player.setAttacked(false);
			player.LossHealth(enemy_damage);//Subtract health by enemy's damage.
			group.getChildren().removeAll(Healthbar_red);
			group.getChildren().removeAll(Healthbar_green);
			//check if player dead after losing health, if player's health less or equals to zero, game over.
			if(player.getCurrenthealth()<= 0&&gameover != true) {
				gameover  = true;
				score = second;
			}
		}
		player.AddCurrentFrame("D");
	}
	
	//Method that check if any enemy is dead and control the chance of enemy drop bonus ball.
	public void enemy_dead(ArrayList enemy_list){
		for(int i = 0; i< enemy_list.size();i++) {
			Unit enemy = enemy_list.get(i);
			//if enemy's health is less than equals to zero.
			if (enemy_list.get(i).getCurrenthealth() <= 0) {
				boolean drop = false;
				
				//largest enemy always drop a bonus ball.
				if(enemy_list.get(i).getType()==3) {
						drop = true;
				}else if(enemy_list.get(i).getType()==2) {
					//Roll a random number to see if a bonus will drop.
					int drop_chance = (int)(Math.random() * 3);
					// 1/4 chance to drop 
					if(drop_chance == 1) {
						drop = true;
					}
				}else if(enemy_list.get(i).getType() == 1) {
					//Roll a random number to see if a bonus will drop.
					int drop_chance = (int)(Math.random() * 5);
					// 1/6 chance to drop
					if(drop_chance == 1) {
						drop = true;
					}
				}if(drop == true) {
					//create a bonus ball by the order in bonus queue.
					Bonus_ball bonus_info = bonus_queue.dequeue();
					Circle ball = new Circle(enemy_list.get(i).getX(),enemy_list.get(i).getY(),BonusBall_radius);
					ball.setFill(bonus_info.getColor());
					Bonus_ball bonus = new Bonus_ball(ball,bonus_info.getEffect(),bonus_info.getColor(),bonus_info.getBoostAmount());
					group.getChildren().add(ball);
					bonus_list.add(bonus);
					//enqueue bonus back after ball created for next loop to use.
					bonus_queue.enqueue(bonus);
				}
				//remove enemy if they died.
				group.getChildren().remove(enemy.getShape());
				enemy_list.remove(i);
				
			}
		}
	}

	//Over the game and then display the score player get.
	private void gameover() {
		group.getChildren().clear();
		scene.setFill(Color.BLACK); 
		//display the game over text and score on the screen.
		Text game_over = new Text("GAME OVER!"); 
		Text score_text = new Text("Your Score is :"+String.valueOf(score)); 
		
		//set coordinate
		score_text.setX(height/2-250); 
		score_text.setY(width/2+100); 
		
		//set font and color
		score_text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50)); 
		score_text.setFill(Color.WHITE); 
		
		//add it to the game.
		group.getChildren().add(score_text);
		
		//set coordinate
	    game_over.setX(height/2-400); 
	    game_over.setY(width/2); 

	    //set font and color
	    game_over.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 100)); 
	    game_over.setFill(Color.WHITE); 
	    //add it to the game.
	    group.getChildren().add(game_over);
		
		
	}

	


	


	// class that start the program
   @Override     
   public void start(Stage primaryStage) throws Exception { 
	  //enqueue the bonus ball to bonus queue to decide the order of the bonus ball drop.
	  bonus_queue.enqueue(HealthBonus);
	  bonus_queue.enqueue(AttackBonus);
	  bonus_queue.enqueue(SpeedBonus);
	  bonus_queue.enqueue(AtkSpeedBonus);
	   
	  //add player's circle to the game and get it's color to grey
	  group.getChildren().add(player_model);
	  player_model.setFill(Color.GREY);
	  scene.setFill(Color.WHITE); 
	  //set title and scene 
      primaryStage.setTitle("2D Shooter"); 
      primaryStage.setScene(scene); 
      primaryStage.show(); 
      
      //Listening keyboard press event for the player movement. 
	  scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		   @Override
	        public void handle(KeyEvent key) {
			   //if the following key being pressed, move the player
	            if (key.getCode() == KeyCode.W) {
	            	move_up = true;
	            }if (key.getCode() == KeyCode.S) {
	            	move_down = true;
	            }if (key.getCode() == KeyCode.A) {
	            	move_left = true;
	            }if (key.getCode() == KeyCode.D) {
	            	move_right = true;
	            }
	        }
	    });
	  //Listening keyboard released event to stop the player movement. 
	  scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			 public void handle(KeyEvent key) {
				//if the following key being released, stop moving the player
	            if (key.getCode() == KeyCode.W) {
	            	move_up = false;
	            }if (key.getCode() == KeyCode.S) {
	            	move_down = false;
	            }if (key.getCode() == KeyCode.A) {
	            	move_left = false;
	            }if (key.getCode() == KeyCode.D) {
	            	move_right = false;
	            }
	        }
	  });	
	  
	  //Listening mouse press event for the player shooting. 
	  scene.setOnMousePressed(new EventHandler<MouseEvent>() {
		  @Override
		  public void handle(MouseEvent event)
          {
			  //if left button of mouse being pressed, record the cursor position and start shooting.
			  MouseButton button = event.getButton();
			  if(button==MouseButton.PRIMARY){
				  order_x = event.getX();
	    		  order_y = event.getY();
        		  isShooting = true;
			  }
          }
	  });
	  
	  //Listening mouse released event for stop the player shooting. 
      scene.setOnMouseReleased(new EventHandler <MouseEvent>()
      {
    	  @Override
          public void handle(MouseEvent event)
          {
    		//if left button of mouse being released, stop shooting.
    		  MouseButton button = event.getButton();
			  if(button==MouseButton.PRIMARY){
				  isShooting = false;
             }
          }
      });
      scene.setOnMouseDragged(new EventHandler <MouseEvent>()
      {
          public void handle(MouseEvent event)
          {
        	  //if user dragged mouse cursor, update the cursor position.
        	  MouseButton button = event.getButton();
        	  if(button==MouseButton.PRIMARY){
        		  order_x = event.getX();
        		  order_y = event.getY();
        	  }
          }
      });
      
	  //doing the animation part of the program.
	  AnimationTimer timer = new AnimationTimer() {
		   long oldTime = 0;
		   @Override
		   //update program in nanosecond.
		   public void handle(long time) {
			   oldTime += 1;
			   if (time - oldTime > 1) {
				draw();
			   oldTime = time;
			   }
			  }
			};
	  timer.start();

   }    
   //the class that launch the start class
   public static void main(String args[]){          
      launch(args);     
   }         
} 
