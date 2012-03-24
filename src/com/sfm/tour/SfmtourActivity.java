package com.sfm.tour;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SfmtourActivity extends Activity {
    
	public ArrayList<Movie> movieData;
	public static Movie selectedMovie;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //
        XMLFunctions myXml= new XMLFunctions();
        movieData = myXml.getMovies(this);
        ArrayList<String> movieTitles = getMovieTitles();
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner_selectmovie);
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, movieTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        Button go = (Button) findViewById(R.id.button_go);
       
    }
    
 // go.setOnClickListener(new View.OnClickListener() {
    
	public void onClick(View v) {
		//
        Intent mapIntent = new Intent(getApplicationContext(), SFMapActivity.class);
        //mapIntent.putExtra("SelectedMovie", selectedMovie);
        startActivity(mapIntent);
        //setResult(RESULT_OK, mapIntent);
        //finish();
    }
//});
    
    public ArrayList<String> getMovieTitles() {
    	ArrayList<String> titles = new ArrayList<String> ();
    	for(Movie m : movieData) {
    		titles.add(m.getTitle());
    	}
    	
    	return titles;
    	
    }

    
    public class MyOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          
          String selectedTitle =  parent.getItemAtPosition(pos).toString();
          for(Movie m : movieData) {
        	  if(m.getTitle() == selectedTitle) {
        		  selectedMovie = new Movie(m);
        	  }
          }
          
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    

}