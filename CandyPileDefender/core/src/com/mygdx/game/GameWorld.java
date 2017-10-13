package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

public class GameWorld {
	
	private TiledMap map;
	private MapObjects borders;
	private MapObjects spawnPoints;
	private MapProperties prop;
	
	public GameWorld(){
		map = new TmxMapLoader().load("C:\\CandyPile\\CandyPileDefender\\core\\assets\\GameMap.tmx");
		borders = map.getLayers().get("Borders").getObjects();
		spawnPoints = map.getLayers().get("Coordinates").getObjects();
		prop = map.getProperties();
		
	}
	public Array<RectangleMapObject> getHitboxes(){
		return borders.getByType(RectangleMapObject.class);
	}
	public Array<RectangleMapObject> getSpawnPoints(){
		return spawnPoints.getByType(RectangleMapObject.class);
	}
public OrthogonalTiledMapRenderer getMapRenderer(OrthographicCamera camera){
	OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map);
	renderer.setView(camera);
	return renderer;
	
}
public TiledMap getMap() {
	return map;
}
public void setMap(TiledMap map) {
	this.map = map;
}
public MapObjects getBorders() {
	return borders;
}
public void setBorders(MapObjects borders) {
	this.borders = borders;
}
public void setSpawnPoints(MapObjects spawnPoints) {
	this.spawnPoints = spawnPoints;
}
public float mapWidth(){
	return (float) prop.get("width", Integer.class)*32;
}
public float mapHeight(){
	return (float) prop.get("height", Integer.class)*32;
}
}
