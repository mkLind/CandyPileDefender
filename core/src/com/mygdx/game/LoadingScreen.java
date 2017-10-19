package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;

public class LoadingScreen implements Screen {

	final Core game;
	
    private Label status;
    private Stage stage;
    private int stateTime;

	public LoadingScreen(final Core game) {
		this.game = game;
	}
	

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (game.getLoader().getManager().update()) {
        	game.getLoader().getManager().finishLoading();
        	game.setScreen(new MainMenuScreen(game));
        }else{
        	game.batch.begin();
        	game.font.draw(game.batch, "Loading: " + MathUtils.round(game.getLoader().getManager().getProgress()*100) + "%", 100, 100);
       game.batch.end();
//       System.out.println("LOADED ASSETS: " + game.getLoader().getManager().getLoadedAssets() );
        }

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
