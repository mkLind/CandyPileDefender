package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Pile extends SpriteCommons {
	
	private int health;
	
	private Texture pileTexture;
	private Texture pileTexture2;
	private Texture pileTexture3;
	private Texture pileTexture4;
	private ArrayList<Vector2> trail;
	
	public Pile(int width, int height, float x, float y, Texture pileTexture, Texture pileTexture2, Texture pileTexture3, Texture pileTexture4) {
		
		super(width, height, x, y, 0f, 0f);
		
		this.pileTexture = pileTexture;
		this.pileTexture2 = pileTexture2;
		this.pileTexture3 = pileTexture3;
		this.pileTexture4 = pileTexture4;

		
		health = 8;
		trail = new  ArrayList<Vector2>();
		
	}

	
	public void reduceHealth() {
		health--;
	}


	public int getHealth() {
		return health;
	}


	public void setHealth(int health) {
		this.health = health;
	}


	public Texture getPileTexture() {
		return pileTexture;
	}

	public Texture getPileTexture2() {
		return pileTexture2;
	}
	
	public Texture getPileTexture3() {
		return pileTexture3;
	}

	public Texture getPileTexture4() {
		return pileTexture4;
	}
public void calculatePointsAroundObstacle(Rectangle obstacle, Rectangle collidingEnemy) {
	// Right and up collision
	if(obstacle.getX() + obstacle.getWidth()<collidingEnemy.getX() && obstacle.getY() + obstacle.getHeight()<collidingEnemy.getY() ) {
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth(),collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth(),collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth(),collidingEnemy.getY() - collidingEnemy.getHeight() * 2));
	}
	// Right and down collision
	if(obstacle.getX() + obstacle.getWidth()<collidingEnemy.getX() && obstacle.getY()>collidingEnemy.getY() + collidingEnemy.getHeight()) {
		trail.add(new Vector2(collidingEnemy.getX(),collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() - collidingEnemy.getWidth() ,collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() - collidingEnemy.getWidth()*2 ,collidingEnemy.getY() - collidingEnemy.getHeight()));
	}
	//  Left and up collision
	if(obstacle.getX()>collidingEnemy.getX() + collidingEnemy.getWidth() && obstacle.getY() + obstacle.getHeight()<collidingEnemy.getY()) {
		trail.add(new Vector2(collidingEnemy.getX() - collidingEnemy.getWidth() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() - collidingEnemy.getWidth(),collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() - collidingEnemy.getWidth() ,collidingEnemy.getY()- collidingEnemy.getHeight()*2));
	}
	// Left and down collision
	if(obstacle.getX()>collidingEnemy.getX() + collidingEnemy.getWidth() && obstacle.getY()>collidingEnemy.getY() + collidingEnemy.getHeight()) {
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth() ,collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth()*2,collidingEnemy.getY() - collidingEnemy.getHeight()));
	}
	// Right collision
	if(obstacle.getX() + obstacle.getWidth()<collidingEnemy.getX()) {
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
	}
	// Left collision
	if(obstacle.getX()>collidingEnemy.getX() + collidingEnemy.getWidth()) {
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
		
	}
	// Up collision
	if(obstacle.getY() + obstacle.getHeight()<collidingEnemy.getY()) {
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
	}
	// down collision
	if(obstacle.getY()>collidingEnemy.getY() + collidingEnemy.getHeight()) {
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY()));
	}
	
}

public Vector2 getPath() {
	
	Vector2 target;
	if(!trail.isEmpty()) {
	target = trail.get(0);
	}else {
		target = new Vector2(super.getX(), super.getY());
	}
	return target;
}
public void consumePath(Rectangle enemy, Vector2 pathTail) {
	if(!trail.isEmpty() && enemy.contains(pathTail.x, pathTail.y)) {
		trail.remove(0);
	}
	
}

}
