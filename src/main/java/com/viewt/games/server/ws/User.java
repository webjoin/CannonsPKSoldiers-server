package com.viewt.games.server.ws;

import javax.websocket.Session;

public class User{
	int prole ; // 1 soldier  2 cannon  player's role .
	String uuid;  
	int status ; // 0 waiting , 1 playing
	int type ;  // the type of the chess. 
	String otherUuid;
	transient Session session;
	boolean myWin ;
	int turn ;
	
public boolean myWin() {
		return myWin;
	}
	public void setWin(boolean myWin) {
		this.myWin = myWin;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	//	int[] coordinate ; // [1,2,1,3]  ox=1,oy=1,nx=1,ny=3    u.c
	int ox ;
	int oy ;
	int nx ;
	int ny ;
	
	
	public int getProle() {
		return prole;
	}
	public void setProle(int prole) {
		this.prole = prole;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getOx() {
		return ox;
	}
	public void setOx(int ox) {
		this.ox = ox;
	}
	public int getOy() {
		return oy;
	}
	public void setOy(int oy) {
		this.oy = oy;
	}
	public int getNx() {
		return nx;
	}
	public void setNx(int nx) {
		this.nx = nx;
	}
	public int getNy() {
		return ny;
	}
	public void setNy(int ny) {
		this.ny = ny;
	}
	public String getOtherUuid() {
		return otherUuid;
	}
	public void setOtherUuid(String otherUuid) {
		this.otherUuid = otherUuid;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
	
}