import java.io.IOException;
import java.util.LinkedHashMap;

import processing.core.PApplet;
import processing.core.PVector;

public class DecayApp extends PApplet{
	public static float PROJECTOR_RATIO = 1080f/1920.0f;
	
	String recordingFile = "test.kinect";
	KinectBodyDataProvider kinectReader;
	PersonTracker tracker = new PersonTracker();
	
	LinkedHashMap<Long, Person> people;
	
	float xoff = 0.0f;
	float yoff = 0.0f;
	float increment = 0.005f; 
	
	public void settings() {
		createWindow(false, false, .5f);
	}

	public void setup() {
		
		try {
			kinectReader = new KinectBodyDataProvider("test2.kinect", 10);
		} catch (IOException e) {
			System.out.println("Unable to create kinect producer");
		}
		
		people = new LinkedHashMap<Long, Person>();
		
		kinectReader.start();
	}

	public void draw() {
		setScale(.5f);
		background(0);
		KinectBodyData bodyData = kinectReader.getData();
		tracker.update(bodyData);
		
		// Create an alpha blended background
//		fill(0, 10);
//		rect(0,0,width,height);
//		noStroke();
		
		for(Long id : tracker.getEnters()) {
			people.put(id,  new Person(this, .1f));
		}
		
		for(Long id: tracker.getExits()) {
			people.remove(id);
		}
		 
		 for(Body b : tracker.getPeople().values()) {
			Person p = people.get(b.getId());
			p.update(b);
			p.drawHead();
		}
		 
	  
	}
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void createWindow(boolean useP2D, boolean isFullscreen, float windowsScale) {
		if (useP2D) {
			if(isFullscreen) {
				fullScreen(P2D);  			
			} else {
				size((int)(1920 * windowsScale), (int)(1080 * windowsScale), P2D);
			}
		} else {
			if(isFullscreen) {
				fullScreen();  			
			} else {
				size((int)(1920 * windowsScale), (int)(1080 * windowsScale));
			}
		}		
	}

	// use lower numbers to zoom out (show more of the world)
	// zoom of 1 means that the window is 2 meters wide and appox 1 meter tall.
	public void setScale(float zoom) {
		scale(zoom* width/2.0f, zoom * -width/2.0f);
		translate(1f/zoom , -PROJECTOR_RATIO/zoom );		
	}
	
	public static void main(String[] args) {
		PApplet.main(DecayApp.class.getName());
	}
}
