package com.sfm.tour;
import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XMLFunctions {
	
	public ArrayList<Movie> getMovies(Context context) {
	ArrayList<Movie> items=new ArrayList<Movie>();
	Movie newMovie = null;  
	LocationData newLocation = null;
	boolean isDesc = false;
	      XmlPullParser xpp=context.getResources().getXml(R.xml.moviedata);
	      
	      try {
			while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
			    if (xpp.getEventType()==XmlPullParser.START_TAG) {
			      
			      if (xpp.getName().equals("movie")) {
			          newMovie = new Movie(xpp.getAttributeValue(0));
			      }
			      else if(xpp.getName().equals("location")) {
		            String lat = xpp.getAttributeValue(null, "lat");
		            String lon = xpp.getAttributeValue(null, "long");
			    	newLocation = new LocationData(lat,lon);
			      }
			      else if(xpp.getName().equals("description")) {
			    	  isDesc = true;
			      }
			    	
			    } else if(xpp.getEventType()==XmlPullParser.END_TAG) {
			    	if(xpp.getName().equals("movie")) {
			    		items.add(newMovie);
			    	}
			    	else if(xpp.getName().equals("location")) {
				    	  newMovie.addLocation(newLocation);
				    } 
			    	else if(xpp.getName().equals("description")) {
				    	  isDesc = false;
				      }
			    	
			    } 
			    else if(xpp.getEventType()==XmlPullParser.TEXT) {
			      if(isDesc == true) {
			    	  newLocation.setDescription(xpp.getName());
			      }
			    }
			    
			    xpp.next();
			  }
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      return items;
	}
	
}