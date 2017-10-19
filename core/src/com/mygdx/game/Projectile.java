package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
/**
 * Class that represents the projectiles the player can shoot around in the game
 * @author Markus
 *
 */
public class Projectile extends SpriteCommons {
	private Texture t;
	private long TimeAlive;
	private long currentTime;
	private float angle;
	private float targetX;
	private float targetY;
	/**
	 *  initializes the projectile with following parameters 
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @param xVel
	 * @param yVel
	 * @param t
	 */

	public Projectile(int width, int height, float x, float y, float xVel, float yVel, Texture t) {
		super(width, height, x, y, xVel, yVel);
		this.t = t;
		/*
		 * Current time = The time at which the projectile was fired
		 * 
		 */
		currentTime = 0;
		TimeAlive = 0;
		angle = 0f;
		targetX = 0;
		targetY = 0;
		
		// TODO Auto-generated constructor stub
	}
// getters and setters
	public float getTargetX() {
		return targetX;
	}

	public void setTargetX(float targetX) {
		this.targetX = targetX;
	}

	public float getTargetY() {
		return targetY;
	}

	public void setTargetY(float targetY) {
		this.targetY = targetY;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Texture getT() {
		return t;
	}

	public void setT(Texture t) {
		this.t = t;
	}

	public long getTimeAlive() {
		return TimeAlive;
	}

	public void setTimeAlive(long timeAlive) {
		TimeAlive = timeAlive;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long l) {
		this.currentTime = l;
	}
	
	/**
	 * Gives initial coordinates to two additional shots in tripple shot powerup based on middle coordinate of player and coordinate of
	 * Clicking point
	 * @param playerCoords
	 * @param ClickCoords
	 * @return
	 */
public static Vector3[] getSideShots(Player player, Vector3 ClickCoords){
	Vector3[] sideshots = new Vector3[2];
	// Left projectile
	float xl = 0f;
	float yl = 0f;
	// Norhwest
	if(player.getX()>ClickCoords.x && player.getY()  < ClickCoords.y  ){
		xl = player.getX() - (player.getWidth()/2);
		yl = player.getY() - (player.getHeight()/2);
			
		
		// southwest
	}else if(player.getX()>ClickCoords.x && player.getY()  > ClickCoords.y){
		xl = player.getX() + (player.getWidth()/2);
		yl = player.getY() - (player.getHeight()/2);
		
		// NorthEast
	}else if(player.getX()<ClickCoords.x && player.getY()  <ClickCoords.y ){
		xl = player.getX() -(player.getWidth()/2);
		yl = player.getY() + (player.getHeight()/2);
		
		//SouthEast
	}else if (player.getX()<ClickCoords.x && player.getY()  >ClickCoords.y ){
		xl = player.getX() +(player.getWidth()/2);
		yl = player.getY() + (player.getHeight()/2);
	}// UP
	else if(player.getY()  < ClickCoords.y && ClickCoords.x>(player.getX() - player.getWidth()/3) && ClickCoords.x<(player.getX() + player.getWidth()/3)){
	xl = player.getX() - player.getWidth()/2;
	yl = player.getY();
	} // Down
	else if(player.getY()  > ClickCoords.y && ClickCoords.x>(player.getX() - player.getWidth()/3) && ClickCoords.x<(player.getX() + player.getWidth()/3)){
		xl = player.getX() + player.getWidth()/2;
		yl = player.getY(); 
	}//left
	else if(player.getX()  < ClickCoords.x && ClickCoords.y>(player.getY() - player.getHeight()/3) && ClickCoords.y<(player.getY() + player.getHeight()/3)){
		xl = player.getX();
		yl = player.getY() - player.getHeight()/2;
	}//right
	else if(player.getX()  < ClickCoords.x && ClickCoords.y>(player.getY() - player.getHeight()/3) && ClickCoords.y<(player.getY() + player.getHeight()/3)){
		xl = player.getX();
		yl = player.getY() - player.getHeight()/2;
	}
	
	
	Vector3 leftShot = new Vector3(xl,yl,0);
	
	// Right Projectile
	// Norhwest
	float xr = 0f;
	float yr = 0f;
		if(player.getX()>ClickCoords.x && player.getY()  < ClickCoords.y  ){
			xr = player.getX() + (player.getWidth()/2);
			yr = player.getY() + (player.getHeight()/2);
				
			
			// southwest
		}else if(player.getX()>ClickCoords.x && player.getY()  > ClickCoords.y){
			xr = player.getX() - (player.getWidth()/2);
			yr = player.getY() + (player.getHeight()/2);
			
			// NorthEast
		}else if(player.getX()<ClickCoords.x && player.getY()  < ClickCoords.y ){
			xr = player.getX() +(player.getWidth()/2);
			yr = player.getY() - (player.getHeight()/2);
			
			//SouthEast
		}else if (player.getX()<ClickCoords.x && player.getY()  > ClickCoords.y ){
			xr = player.getX() -(player.getWidth()/2);
			yr = player.getY() - (player.getHeight()/2);
		}// UP
		else if(player.getY()  < ClickCoords.y && ClickCoords.x>(player.getX() - player.getWidth()/3) && ClickCoords.x<(player.getX() + player.getWidth()/3)){
			xr = player.getX() + player.getWidth()/2;
			yr = player.getY();
			} // Down
			else if(player.getY()  > ClickCoords.y && ClickCoords.x>(player.getX() - player.getWidth()/3) && ClickCoords.x<(player.getX() + player.getWidth()/3)){
				xr = player.getX() - player.getWidth()/2;
				yr = player.getY(); 
			}//left
			else if(player.getX()  < ClickCoords.x && ClickCoords.y>(player.getY() - player.getHeight()/3) && ClickCoords.y<(player.getY() + player.getHeight()/3)){
				xr = player.getX();
				yr = player.getY() + player.getHeight()/2;
			}//right
			else if(player.getX()  < ClickCoords.x && ClickCoords.y>(player.getY() - player.getHeight()/3) && ClickCoords.y<(player.getY() + player.getHeight()/3)){
				xr = player.getX();
				yr = player.getY() - player.getHeight()/2;
			}
			
		Vector3 rightShot = new Vector3(xr,yr,0);	
	
	sideshots[0] = leftShot;
	sideshots[1] = rightShot;
	return sideshots;
	
	
}
}
