******************************
SF MOVIE TOUR

Divya Reddy Anugu
******************************

https://code.google.com/p/sfmovietour/wiki/Design

List of all projects
--------------------
(1) sfmtour - The main project that implements all the features of the SF Movie Tour android mobile application

(2) AREngine - open source augmented reality engine used by sfmtour (as a library)

(3) sfmtServer - Server that manages the backend (read and write movie tag information, upload and save photos). Uses Java Servlets, HTTP POST, HTTP GET.


sfmtour - List of all classes
-----------------------------
(a) SfmtourActivity.java
    -Main activity that is launched when the application starts. Handles the home screen, allows user to select TAG or TOUR. 
    -If user selects TAG, SFMapTagActivity is started.
    -If user selects TOUR, the tour criteria are displayed. User selects the criteria and clicks 'GO' to start the SFMapActivity activity. The user can also launch the augmented reality tour by clicking on 'Switch to AR' button. AREngine project's main acitvity is launched to show the movie locations in augmented reality view.

(b) SFMapActivity.java
    -Allows user to tour movie locations. 
    -Depending on the tour criteria selected by the user, all the relevant movie locations are displayed using markers on the map. 
    -Map bounds are calculated using GeoLocation.java and the markers are defined in CustomItemizedOverlay.java

(c) CustomItemizedOverlay.java
    -Implements the custom map overlay that displays a balloon when user clicks on a map marker. 
    -This balloon shows all the movie tag information and the photo (if available) for the selected location.

(d) SFMapTagActivity.java
    -Displays the map with current location marked using a marker.
    -Provides UI to the user allowing him/her to enter tag information - movie title, time, description
    -Allows user to take a photo that will be a part of the movie tag information for the current location

(e) XMLFunctions.java
    -Handles all the read, write and parse methods for the XML data. Defines functions that allow the application to read and write to the server.

(f) Movie.java & LocationData.java
    -Defines the data used by the application. All the movie tag information is handled as Movie and LocationData objects. Each Movie object can have 1 or more LocationData child objects.

(g) GeoLocation.java
    -Provides functions to calculate the map bounds.
