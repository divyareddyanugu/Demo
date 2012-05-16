package com.sfm.tour;

/*
 *  XMLFunctions
 *  Handles all the read, write and parse methods for the XML data. Defines functions that allow the application to read from and write to the server.

 */
import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class XMLFunctions {

	/*
	 * Gets the movies that are tagged
	 */
	public ArrayList<Movie> getMovies() {
		ArrayList<Movie> items = new ArrayList<Movie>();
		Movie newMovie = null;
		LocationData newLocation = null;
		boolean isDesc = false;
		boolean res = false;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			URL url = new URL(
					"http://solaropportunity.org/sfmtServer/SFServer?command=getMovieData");
			String text = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				text += inputLine;

			in.close();
			text = text.replace("null", "");

			StringReader reader = new StringReader(text);
			xpp.setInput(reader);

			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {

					if (xpp.getName().equals("movie")) {
						newMovie = new Movie(xpp.getAttributeValue(0));
					} else if (xpp.getName().equals("location")) {
						String lat = xpp.getAttributeValue(null, "lat");
						String lon = xpp.getAttributeValue(null, "long");
						newLocation = new LocationData(lat, lon);
					} else if (xpp.getName().equals("description")) {
						isDesc = true;
					}

				} else if (xpp.getEventType() == XmlPullParser.END_TAG) {
					if (xpp.getName().equals("movie")) {
						items.add(newMovie);
					} else if (xpp.getName().equals("location")) {
						newMovie.addLocation(newLocation);
					} else if (xpp.getName().equals("description")) {
						isDesc = false;
					}

				} else if (xpp.getEventType() == XmlPullParser.TEXT) {
					if (isDesc == true) {
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

	/*
	 * Get the tag information for movie with given title
	 */
	public Movie getMovieData(String title) {

		Movie newMovie = null;
		LocationData newLocation = null;
		boolean isDesc = false;
		boolean isTime = false;
		// XmlPullParser xpp=context.getResources().getXml(R.xml.moviedata);
		boolean res = false;
		// res = writeFile(context);
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser xpp = factory.newPullParser();
			URL url = new URL(
					"http://solaropportunity.org/sfmtServer/SFServer?command=getMovieData&movieName="
							+ URLEncoder.encode(title));
			String text = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				text += inputLine;

			in.close();
			text = text.replace("null", "");

			StringReader reader = new StringReader(text);
			xpp.setInput(reader);

			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {

					if (xpp.getName().equals("movie")) {
						newMovie = new Movie(xpp.getAttributeValue(null,
								"title"));
					} else if (xpp.getName().equals("location")) {
						String lat = xpp.getAttributeValue(null, "lat");
						String lon = xpp.getAttributeValue(null, "long");
						newLocation = new LocationData(lat, lon);
					} else if (xpp.getName().equals("description")) {
						isDesc = true;
					} else if (xpp.getName().equals("time")) {
						isTime = true;
					}

				} else if (xpp.getEventType() == XmlPullParser.END_TAG) {
					if (xpp.getName().equals("movie")) {
					} else if (xpp.getName().equals("location")) {
						newMovie.addLocation(newLocation);
					} else if (xpp.getName().equals("description")) {
						isDesc = false;
					} else if (xpp.getName().equals("time")) {
						isTime = false;
					}

				} else if (xpp.getEventType() == XmlPullParser.TEXT) {
					if (isDesc == true) {
						newLocation.setDescription(xpp.getText());
					} else if (isTime == true) {
						newLocation.setTime(xpp.getText());
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
		return newMovie;

	}

	/*
	 * List of all movies shot in SF
	 */
	public ArrayList<String> getAllMovies(Context context) {
		ArrayList<String> items = new ArrayList<String>();
		ArrayList<String> taggedItems = new ArrayList<String>();
		ArrayList<Movie> movieItems = new ArrayList<Movie>();
		boolean isMovieElement = false;
		boolean isMovieTagged = false;
		// XmlPullParser xpp=context.getResources().getXml(R.xml.movielist);

		try {

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser xpp = factory.newPullParser();
			URL url = new URL(
					"http://solaropportunity.org/sfmtServer/SFServer?command=getAllMovies");
			String text = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				text += inputLine;

			in.close();
			text = text.replace("null", "");
			StringReader reader = new StringReader(text);
			xpp.setInput(reader);
			Movie m = null;
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {

					if (xpp.getName().equals("movie")) {
						isMovieElement = true;
						m = new Movie();
						String numLocs = xpp.getAttributeValue(null, "locs");
						if (numLocs != null) {
							if (!numLocs.equals("0")) {
								// taggedItems.add(xpp.getText());
								isMovieTagged = true;
							}
						}
						String id = xpp.getAttributeValue(null, "id");
						if (id != null && !id.equals("")) {

							m.setId(id);

						}
					}

				} else if (xpp.getEventType() == XmlPullParser.END_TAG) {
					if (xpp.getName().equals("movie")) {
						isMovieElement = false;
						isMovieTagged = false;
					}

				} else if (xpp.getEventType() == XmlPullParser.TEXT) {
					if (isMovieElement == true) {
						if (xpp.getText() != null) {
							items.add(xpp.getText());
							m.setTitle(xpp.getText());
							// Check if this movie is tagged
							if (isMovieTagged) {
								taggedItems.add(xpp.getText());
							}
							movieItems.add(m);
						}
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
		SfmtourActivity.allMovies = items;
		SfmtourActivity.taggedMovies = taggedItems;
		SfmtourActivity.allMovieObjects = movieItems;
		return items;

	}

	/*
	 * deprecated
	 * No longer used
	 */
	public boolean writeFile(Context context) {
		InputStream in = context.getResources()
				.openRawResource(R.raw.moviedata);

		InputStreamReader inputreader = new InputStreamReader(in);
		BufferedReader buffreader = new BufferedReader(inputreader);
		String line;
		StringBuilder text = new StringBuilder();

		try {
			while ((line = buffreader.readLine()) != null) {
				text.append(line);

			}

			FileOutputStream fos = context.openFileOutput("moviedata.xml",
					Context.MODE_PRIVATE);
			fos.write(text.toString().getBytes("UTF-8"));
			fos.close();
		} catch (IOException e) {
			return false;
		}
		return true;

	}

	/*
	 * Add tag information to the movie. Send it to server.
	 */
	public boolean addMovieTag(Context c, Movie movieToAdd) {
		try {

			for (LocationData l : movieToAdd.getLocations()) {

				String writeURL = "http://solaropportunity.org/sfmtServer/SFServer?command=addMovieTag&movieName="
						+ URLEncoder.encode(movieToAdd.getTitle())
						+ "&time="
						+ URLEncoder.encode(l.getTime())
						+ "&desc="
						+ URLEncoder.encode(l.getDescription())
						+ "&lat="
						+ URLEncoder.encode(Double.toString(l.getLatitude()))
						+ "&lng="
						+ URLEncoder.encode(Double.toString(l.getLongitude()));

				URL url = new URL(writeURL);
				URLConnection con = url.openConnection();
				String text = "";
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				while ((inputLine = in.readLine()) != null)
					text += inputLine;

				in.close();
				if (text.equals("results")) {
					return true;
				}
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		finally {

		}
		return false;
	}

}