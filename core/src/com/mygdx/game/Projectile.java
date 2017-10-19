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
	if(player.getMiddleX()>ClickCoords.x && player.getMiddleY()  < ClickCoords.y  ){
		xl = player.getMiddleX() - (player.getWidth()/2);
		yl = player.getMiddleY() - (player.getHeight()/2);
			
		
		// southwest
	}else if(player.getMiddleX()>ClickCoords.x && player.getMiddleY()  > ClickCoords.y){
		xl = player.getMiddleX() + (player.getWidth()/2);
		yl = player.getMiddleY() - (player.getHeight()/2);
		
		// NorthEast
	}else if(player.getMiddleX()<ClickCoords.x && player.getMiddleY()  <ClickCoords.y ){
		xl = player.getMiddleX() -(player.getWidth()/2);
		yl = player.getMiddleY() + (player.getHeight()/2);
		
		//SouthEast
	}else if (player.getMiddleX()<ClickCoords.x && player.getMiddleY()  >ClickCoords.y ){
		xl = player.getMiddleX() +(player.getWidth()/2);
		yl = player.getMiddleY() + (player.getHeight()/2);
	}// UP
	else if(player.getMiddleY()  < ClickCoords.y && ClickCoords.x>(player.getMiddleX() - player.getWidth()/3) && ClickCoords.x<(player.getMiddleX() + player.getWidth()/3)){
	xl = player.getMiddleX() - player.getWidth()/2;
	yl = player.getMiddleY();
	} // Down
	else if(player.getMiddleY()  > ClickCoords.y && ClickCoords.x>(player.getMiddleX() - player.getWidth()/3) && ClickCoords.x<(player.getMiddleX() + player.getWidth()/3)){
		xl = player.getMiddleX() + player.getWidth()/2;
		yl = player.getMiddleY(); 
	}//left
	else if(player.getMiddleX()  < ClickCoords.x && ClickCoords.y>(player.getMiddleY() - player.getHeight()/3) && ClickCoords.y<(player.getMiddleY() + player.getHeight()/3)){
		xl = player.getMiddleX();
		yl = player.getMiddleY() - player.getHeight()/2;
	}//right
	else if(player.getMiddleX()  < ClickCoords.x && ClickCoords.y>(player.getMiddleY() - player.getHeight()/3) && ClickCoords.y<(player.getMiddleY() + player.getHeight()/3)){
		xl = player.getMiddleX();
		yl = player.getMiddleY() - player.getHeight()/2;
	}
	
	
	Vector3 leftShot = new Vector3(xl,yl,0);
	
	// Right Projectile
	// Norhwest
	float xr = 0f;
	float yr = 0f;
		if(player.getMiddleX()>ClickCoords.x && player.getMiddleY()  < ClickCoords.y  ){
			xr = player.getMiddleX() + (player.getWidth()/2);
			yr = player.getMiddleY() + (player.getHeight()/2);
				
			
			// southwest
		}else if(player.getMiddleX()>ClickCoords.x && player.getMiddleY()  > ClickCoords.y){
			xr = player.getMiddleX() - (player.getWidth()/2);
			yr = player.getMiddleY() + (player.getHeight()/2);
			
			// NorthEast
		}else if(player.getMiddleX()<ClickCoords.x && player.getMiddleY()  < ClickCoords.y ){
			xr = player.getMiddleX() +(player.getWidth()/2);
			yr = player.getMiddleY() - (player.getHeight()/2);
			
			//SouthEast
		}else if (player.getMiddleX()<ClickCoords.x && player.getMiddleY()  > ClickCoords.y ){
			xr = player.getMiddleX() -(player.getWidth()/2);
			yr = player.getMiddleY() - (player.getHeight()/2);
		}// UP
		else if(player.getMiddleY()  < ClickCoords.y && ClickCoords.x>(player.getMiddleX() - player.getWidth()/3) && ClickCoords.x<(player.getMiddleX() + player.getWidth()/3)){
			xr = player.getMiddleX() + player.getWidth()/2;
			yr = player.getMiddleY();
			} // Down
			else if(player.getMiddleY()  > ClickCoords.y && ClickCoords.x>(player.getMiddleX() - player.getWidth()/3) && ClickCoords.x<(player.getMiddleX() + player.getWidth()/3)){
				xr = player.getMiddleX() - player.getWidth()/2;
				yr = player.getMiddleY(); 
			}//left
			else if(player.getMiddleX()  < ClickCoords.x && ClickCoords.y>(player.getMiddleY() - player.getHeight()/3) && ClickCoords.y<(player.getMiddleY() + player.getHeight()/3)){
				xr = player.getMiddleX();
				yr = player.getMiddleY() + player.getHeight()/2;
			}//right
			else if(player.getMiddleX()  < ClickCoords.x && ClickCoords.y>(player.getMiddleY() - player.getHeight()/3) && ClickCoords.y<(player.getMiddleY() + player.getHeight()/3)){
				xr = player.getMiddleX();
				yr = player.getMiddleY() - player.getHeight()/2;
			}
			
		Vector3 rightShot = new Vector3(xr,yr,0);	
	
	sideshots[0] = leftShot;
	sideshots[1] = rightShot;
	return sideshots;
	
	
}
}
