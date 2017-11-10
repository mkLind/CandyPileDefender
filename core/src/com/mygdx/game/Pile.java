package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private HashMap<Integer,ArrayList<Vector2>> paths;
	
	public Pile(int width, int height, float x, float y, Texture pileTexture, Texture pileTexture2, Texture pileTexture3, Texture pileTexture4) {
		
		super(width, height, x, y, 0f, 0f);
		
		this.pileTexture = pileTexture;
		this.pileTexture2 = pileTexture2;
		this.pileTexture3 = pileTexture3;
		this.pileTexture4 = pileTexture4;

		paths = new HashMap<Integer, ArrayList<Vector2>>();
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
public void calculatePointsAroundObstacle(Rectangle obstacle, Rectangle collidingEnemy, int enemyId) {
	// Right and up collision
	ArrayList<Vector2> trail = new ArrayList<Vector2>();
	
	if(obstacle.getX() + obstacle.getWidth()<collidingEnemy.getX() && obstacle.getY() + obstacle.getHeight()<collidingEnemy.getY() ) {
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth(),collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth(),collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth(),collidingEnemy.getY() - collidingEnemy.getHeight() * 2));
	}
	// Right and down collision
	else if(obstacle.getX() + obstacle.getWidth()<collidingEnemy.getX() && obstacle.getY()>collidingEnemy.getY() + collidingEnemy.getHeight()) {
		trail.add(new Vector2(collidingEnemy.getX(),collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() - collidingEnemy.getWidth() ,collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() - collidingEnemy.getWidth()*2 ,collidingEnemy.getY() - collidingEnemy.getHeight()));
	}
	//  Left and up collision
	else if(obstacle.getX()>collidingEnemy.getX() + collidingEnemy.getWidth() && obstacle.getY() + obstacle.getHeight()<collidingEnemy.getY()) {
		trail.add(new Vector2(collidingEnemy.getX() - collidingEnemy.getWidth() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() - collidingEnemy.getWidth(),collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() - collidingEnemy.getWidth() ,collidingEnemy.getY()- collidingEnemy.getHeight()*2));
	}
	// Left and down collision
	else if(obstacle.getX()>collidingEnemy.getX() + collidingEnemy.getWidth() && obstacle.getY()>collidingEnemy.getY() + collidingEnemy.getHeight()) {
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth() ,collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth()*2,collidingEnemy.getY() - collidingEnemy.getHeight()));
	}
	// Right collision
	else if(obstacle.getX() + obstacle.getWidth()<collidingEnemy.getX()) {
		trail.add(new Vector2(collidingEnemy.getX()+collidingEnemy.getWidth() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX() +collidingEnemy.getWidth(),collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() +collidingEnemy.getWidth(),collidingEnemy.getY()- collidingEnemy.getHeight()*2));
	} 
	// Left collision
	else if(obstacle.getX()>collidingEnemy.getX() + collidingEnemy.getWidth()) {
		trail.add(new Vector2(collidingEnemy.getX()-collidingEnemy.getWidth() ,collidingEnemy.getY()));
		trail.add(new Vector2(collidingEnemy.getX()-collidingEnemy.getWidth()  ,collidingEnemy.getY()- collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX()-collidingEnemy.getWidth()  ,collidingEnemy.getY()- collidingEnemy.getHeight()*2));
		
	}
	// Up collision
	else if(obstacle.getY() + obstacle.getHeight()<collidingEnemy.getY()) {
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY() + collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth() ,collidingEnemy.getY() + collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth()*2 ,collidingEnemy.getY() + collidingEnemy.getHeight()));
	}
	// down collision
	else if(obstacle.getY()>collidingEnemy.getY() + collidingEnemy.getHeight()) {
		trail.add(new Vector2(collidingEnemy.getX() ,collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth() ,collidingEnemy.getY() - collidingEnemy.getHeight()));
		trail.add(new Vector2(collidingEnemy.getX() + collidingEnemy.getWidth()*2 ,collidingEnemy.getY() - collidingEnemy.getHeight()));
	}
	paths.put(enemyId, trail);

	
}

public Vector2 getPath(int enemyId) {
	
	Vector2 target;
	if(paths.containsKey(enemyId)) {
		System.out.println("GETTING POINT FOR ENEMY " + enemyId + "AFTER COLLISION");
	target = paths.get(enemyId).get(0);
	}else {
		System.out.println("NO PATH FOR ENEMY " + enemyId);
		
		target = new Vector2(super.getX(), super.getY());
	}
	return target;
}
public void consumePath(int enemyId, Rectangle enemy) {
	if(paths.containsKey(enemyId)) {
		if(enemy.contains(paths.get(enemyId).get(0).x,paths.get(enemyId).get(0).y)) {
			paths.get(enemyId).remove(0);
			System.out.println("CONSUMING PATH OF ENEMY: " + enemyId);
			if(paths.get(enemyId).size()==0) {
				System.out.println("PATH CONSUMED FOR ENEMY " + enemyId);
				paths.remove(enemyId);
			}
			
			
			
		}
		
	}
	
}

}
