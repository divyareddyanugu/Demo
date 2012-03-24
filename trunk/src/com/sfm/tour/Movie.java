package com.sfm.tour;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private ArrayList<LocationData> locations;
	
	public Movie(String name) {
		setTitle(name);
		this.locations = new ArrayList<LocationData>();		
	}
	
	public Movie(Movie copyObj) {
		this.title = copyObj.getTitle();
		//this.locations = new ArrayList<LocationData>();
		this.locations = copyObj.getLocations();
	}
	
	public void addLocation(LocationData loc) {
		this.locations.add(loc);
	}
	
	public ArrayList<LocationData> getLocations() {
		return this.locations;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	

}
