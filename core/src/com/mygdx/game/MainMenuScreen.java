package com.mygdx.game;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

	final Core game;
	private Skin mySkin;
	private TextButton playButton;
	private Stage stage;
	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;
	
	private SimpleDateFormat format;
	private Date date;
	private String DateToStr;
	private String currentScore;
	private String tmp;
	private String name;
	private Music ambience;
	
	public MainMenuScreen(final Core game) {
		this.game = game;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		mySkin = new Skin(Gdx.files.internal("C:/CandyPile/CandyPileDefender/core/assets/skin/uiskin.json"));
//		mySkin.getFont("font-label").getData().setScale(1.5f);
		playButton = new TextButton("Play", mySkin);
		playButton.setWidth(Gdx.graphics.getWidth()/3);
        playButton.setPosition(Gdx.graphics.getWidth()/3 - playButton.getWidth()/2,Gdx.graphics.getHeight()/2 - playButton.getHeight()/3);
        ambience = game.getLoader().getManager().get("C:/CandyPile/CandyPileDefender/core/assets/Music/POL-horror-ambience-2-short_16bit.wav", Music.class);
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
//        game.getLoader().setSecond("12340     26/10/2017");
     
        date = new Date(TimeUtils.millis());
        format = new SimpleDateFormat("dd/MM/yyyy");
        DateToStr = format.format(date);
        
        currentScore = Integer.toString(game.getLoader().getScore()) + "     " + DateToStr;
        tmp = checkScores();
        
//        name = Base64Coder.encodeString("Tommi");
//        try {
//        sendScores();
//        }catch(Exception e) {
//        	System.out.println(e.toString());
//        }
        
        int row_height = Gdx.graphics.getWidth() / 10;
        int col_width = Gdx.graphics.getWidth() / 12;
        
    	label1 = new Label("Highscores: ", mySkin);
    	label1.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() - row_height*0.8f);
        label1.setAlignment(Align.topRight);
        label1.setWidth(Gdx.graphics.getWidth()/4);
        stage.addActor(label1);
        
        label2 = new Label("Your score: " + game.getLoader().getScore(), mySkin);
    	label2.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight() - Gdx.graphics.getWidth()/9);
        label2.setAlignment(Align.topLeft);
        label2.setWidth(Gdx.graphics.getWidth()/4);
        stage.addActor(label2);
        
        label3 = new Label("1. " + game.getLoader().getHighScore(), mySkin);
    	label3.setPosition(Gdx.graphics.getWidth()/1.5f,Gdx.graphics.getHeight() - row_height*1.1f);
        label3.setAlignment(Align.left);
        label3.setWidth(Gdx.graphics.getWidth()/4);
        stage.addActor(label3);
        
        label4= new Label("2. " + game.getLoader().getSecond(), mySkin);
    	label4.setPosition(Gdx.graphics.getWidth()/1.5f,Gdx.graphics.getHeight() - row_height*1.3f);
        label4.setAlignment(Align.left);
        label4.setWidth(Gdx.graphics.getWidth()/4);
        stage.addActor(label4);
        
        label5 = new Label("3. " + game.getLoader().getThird(), mySkin);
    	label5.setPosition(Gdx.graphics.getWidth()/1.5f,Gdx.graphics.getHeight() - row_height*1.5f);
        label5.setAlignment(Align.left);
        label5.setWidth(Gdx.graphics.getWidth()/4);
        stage.addActor(label5);
        
        label6 = new Label("4. " + game.getLoader().getFourth(), mySkin);
    	label6.setPosition(Gdx.graphics.getWidth()/1.5f,Gdx.graphics.getHeight() - row_height*1.7f);
        label6.setAlignment(Align.left);
        label6.setWidth(Gdx.graphics.getWidth()/4);
        stage.addActor(label6);
        
        label7 = new Label("5. " + game.getLoader().getFifth(), mySkin);
    	label7.setPosition(Gdx.graphics.getWidth()/1.5f,Gdx.graphics.getHeight() - row_height*1.9f);
        label7.setAlignment(Align.left);
        label7.setWidth(Gdx.graphics.getWidth()/4);
        stage.addActor(label7);
        
        if(game.getLoader().getScore() == 0) {
            label2.setText("");
        }
        if(game.getLoader().getScore() != 0) {
	    	if(tmp == "first") {
	    		label2.setText("New highscore!: " + game.getLoader().getScore());
	    		label3.setText("1. " + game.getLoader().getHighScore() + "  <-");
	    		label4.setText("2. " + game.getLoader().getSecond());
	    		label5.setText("3. " + game.getLoader().getThird());
	    		label6.setText("4. " + game.getLoader().getFourth());
	    		label7.setText("5. " + game.getLoader().getFifth());
	    	}else if (tmp == "second"){
	            label2.setText("Your score: " + game.getLoader().getScore());
	            label4.setText("2. " + game.getLoader().getSecond() + "  <-");
	            label5.setText("3. " + game.getLoader().getThird());
	    		label6.setText("4. " + game.getLoader().getFourth());
	    		label7.setText("5. " + game.getLoader().getFifth());
	    	}else if (tmp == "third"){
	            label2.setText("Your score: " + game.getLoader().getScore());
	            label5.setText("3. " + game.getLoader().getThird() + "  <-");
	    		label6.setText("4. " + game.getLoader().getFourth());
	    		label7.setText("5. " + game.getLoader().getFifth());
	    	}else if (tmp == "fourth"){
	            label2.setText("Your score: " + game.getLoader().getScore());
	            label6.setText("4. " + game.getLoader().getFourth() + "  <-");
	    		label7.setText("5. " + game.getLoader().getFifth());
	    	}else if (tmp == "fifth"){
	            label2.setText("Your score: " + game.getLoader().getScore());
	            label7.setText("5. " + game.getLoader().getFifth() + "  <-");
	    	}else {
	    		label2.setText("Your score: " + game.getLoader().getScore());
	    	}
        }
	}
	
	public String checkScores() {
		
		if(game.getLoader().getScore() > Integer.parseInt(game.getLoader().getHighScore().split(" ")[0])) {
			game.getLoader().setFifth(game.getLoader().getFourth());
			game.getLoader().setFourth(game.getLoader().getThird());
			game.getLoader().setThird(game.getLoader().getSecond());
			game.getLoader().setSecond(game.getLoader().getHighScore());
			game.getLoader().setHighScore(currentScore);
        	return "first";
		}else if (game.getLoader().getScore() <= Integer.parseInt(game.getLoader().getHighScore().split(" ")[0]) &&
				game.getLoader().getScore() > Integer.parseInt(game.getLoader().getSecond().split(" ")[0])) {
			game.getLoader().setFifth(game.getLoader().getFourth());
			game.getLoader().setFourth(game.getLoader().getThird());
			game.getLoader().setThird(game.getLoader().getSecond());
			game.getLoader().setSecond(currentScore);
			return "second";
			
        }else if (game.getLoader().getScore() <= Integer.parseInt(game.getLoader().getSecond().split(" ")[0]) &&
				game.getLoader().getScore() > Integer.parseInt(game.getLoader().getThird().split(" ")[0])) {
			game.getLoader().setFifth(game.getLoader().getFourth());
			game.getLoader().setFourth(game.getLoader().getThird());
			game.getLoader().setThird(currentScore);
			return "third";
			
        }else if (game.getLoader().getScore() <= Integer.parseInt(game.getLoader().getThird().split(" ")[0]) &&
				game.getLoader().getScore() > Integer.parseInt(game.getLoader().getFourth().split(" ")[0])) {
			game.getLoader().setFifth(game.getLoader().getFourth());
			game.getLoader().setFourth(currentScore);
			return "fourth";
			
        }else if (game.getLoader().getScore() <= Integer.parseInt(game.getLoader().getFourth().split(" ")[0]) &&
				game.getLoader().getScore() > Integer.parseInt(game.getLoader().getFifth().split(" ")[0])) {
			game.getLoader().setFifth(currentScore);
			return "fifth";
        }else return "";
	}
/*	
	public void sendScores() {
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.POST).url(
        		"http://ftp.thilanne.altervista.org/CandyPileDefender/addscore.php").content(
        				"name="+name+"&score="+(Integer.toString(game.getLoader().getScore()))+"&hash=mkGZgaG0Gl").build();
        HttpResponseListener httpResponseListener = new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				System.out.println(httpResponse.getHeaders());
				
			}
			
			@Override
			public void failed(Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				
			}
		};
		Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
	}
	public void getScores() {
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.GET).url(
        		"http://ftp.thilanne.altervista.org/CandyPileDefender/addscore.php").content(
        				"name="+name+"&score="+(Integer.toString(game.getLoader().getScore()))+"&hash=mkGZgaG0Gl").build();
        HttpResponseListener httpResponseListener = new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				String answer = httpResponse.getResultAsString();
				
			}
			
			@Override
			public void failed(Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				
			}
		};
		Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
	}*/
	
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
