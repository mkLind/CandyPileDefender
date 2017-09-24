package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {
private int row;
private int column;
private float frametime;
private Texture spritesheet;
private TextureRegion[][] splitImages;
private ArrayList<TextureRegion[]> animatedFrames;

public Animator(int row, int column, float frametime, Texture spritesheet){
	this.spritesheet = spritesheet;
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


}
