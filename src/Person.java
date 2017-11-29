import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Person {
	Body body;
	PApplet app;
	PImage head, ribs;
	
	float xoff = 0.0f;
	float yoff = 0.0f;
	float increment = 0.005f;
	
	public Person(PApplet app, float size) {
		this.app = app;		
		head = app.loadImage("skull.png");
		ribs = app.loadImage("ribcase.png");
	}

	public void update(Body body) {
		this.body = body;
	}	
	
	public void drawHead() {
	 // Get a noise value based on xoff and scale it according to the window's width
		float nx = 0;
		float ny=0;
		if(body.getJoint(Body.HEAD) != null) {
			nx = app.noise(xoff)* body.getJoint(Body.HEAD).x;
			ny = app.noise(yoff)* body.getJoint(Body.HEAD).y;
		}
	  
	  // With each cycle, increment xoff
	  xoff += increment;
	  yoff += increment;
	  
	  app.image(head, nx, ny, head.width/10, head.height/10);
	}
	
	public PVector getRightHand() {
		PVector handRight = body.getJoint(Body.HAND_RIGHT);
		if(handRight != null)
			return handRight;
		return null;
	}
	
	public PVector getLeftHand() {
		PVector handLeft = body.getJoint(Body.HAND_LEFT);
		if(handLeft != null)
			return handLeft;
		return null;
	}
	
	public PVector getHead() {
		PVector head = body.getJoint(Body.HEAD);
		if(head != null)
			return head;
		return null;
	}
}