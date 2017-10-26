package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

	final Core game;
	private Skin mySkin;
	private TextButton playButton;
	private Stage stage;
	private Label label1;
	private Label label2;
	private Music ambience;
	
	public MainMenuScreen(final Core game) {
		this.game = game;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		mySkin = new Skin(Gdx.files.internal("C:/Users/Markus/Desktop/CandyPileDefender/core/assets/skin/uiskin.json"));
//		mySkin.getFont("font-label").getData().setScale(1.5f);
		playButton = new TextButton("Play", mySkin);
		playButton.setWidth(Gdx.graphics.getWidth()/3);
        playButton.setPosition(Gdx.graphics.getWidth()/3 - playButton.getWidth()/2,Gdx.graphics.getHeight()/2 - playButton.getHeight()/3);
        ambience = game.getLoader().getManager().get("C:/Users/Markus/Desktop/CandyPileDefender/core/assets/Music/POL-horror-ambience-2-short_16bit.wav", Music.class);
        ambience.setLooping(true);
        ambience.play();
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	dispose();
            	ambience.stop();
        		game.getLoader().setScore(0);
                game.setScreen(new Updater(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);
//        game.getLoader().setScore(30);
//        game.getLoader().setHighScore(3);
    	label1 = new Label("Highscore: " + game.getLoader().getHighScore(), mySkin);
    	label1.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() - Gdx.graphics.getWidth() /10);
        label1.setAlignment(Align.topRight);
        label1.setWidth(Gdx.graphics.getWidth()/4);
        stage.addActor(label1);
        
        label2 = new Label("Your score: " + game.getLoader().getScore(), mySkin);
    	label2.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() - Gdx.graphics.getWidth()/8);
        label2.setAlignment(Align.topRight);
        label2.setWidth(Gdx.graphics.getWidth()/4);
        stage.addActor(label2);
        
        if(game.getLoader().getScore() == 0) {
        	label1.setText("Highscore: " + game.getLoader().getHighScore());
            
            label2.setText("");
        }
        if(game.getLoader().getScore() != 0) {
	    	if(game.getLoader().getScore() > game.getLoader().getHighScore()) {
	    		game.getLoader().setHighScore(game.getLoader().getScore());
	    		label1.setText("New highscore!: " + game.getLoader().getScore());
	    		label2.setText("");
	    	}else {
	    		label1.setText("Highscore: " + game.getLoader().getHighScore());
	            label2.setText("Your score: " + game.getLoader().getScore());
	    	}
        }
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();

	}

}
