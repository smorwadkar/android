package com.example.dao;

public class GameItem {
	private String agent_id;
	private String agent_name;
	private String game_id;
	private String game_name;
	private String points;
	private String winner_points;
	public String getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}
	public String getAgent_name() {
		return agent_name;
	}
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	public String getGame_id() {
		return game_id;
	}
	public void setGame_id(String game_id) {
		this.game_id = game_id;
	}
	public String getGame_name() {
		return game_name;
	}
	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getWinner_points() {
		return winner_points;
	}
	public void setWinner_points(String winner_points) {
		this.winner_points = winner_points;
	}
	
}
