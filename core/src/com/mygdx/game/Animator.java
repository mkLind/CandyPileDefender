package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*
 * Class for handling spritesheet based animations in the game
 */
public class Animator {
private int row;
private int column;
private float frametime;
private Texture spritesheet;
private TextureRegion[][] splitImages;
private ArrayList<TextureRegion[]> animatedFrames;

public Animator(int row, int column, float frametime, Texture Spritesheet){
	spritesheet = Spritesheet;
	this.row = row;
	this.column = column;
	this.frametime = frametime;
	animatedFrames = new ArrayList<>();
	// Split images of the spriteSheet into a matrix
	splitImages = TextureRegion.split(spritesheet, spritesheet.getWidth()/this.column,spritesheet.getHeight()/this.row );
	for(int i = 0; i<row; i++){
		TextureRegion[] oneAnimation = new TextureRegion[column]; 
		for(int j = 0; j<column; j++){
			oneAnimation[j] = splitImages[i][j];
			
		}
		animatedFrames.add(oneAnimation);
	}
	
}

/*
 * Method For getting a single row of animation from the spritesheet
 */
public Animation getAnimation(int row){
	return new Animation(frametime, animatedFrames.get(row));
}



}
