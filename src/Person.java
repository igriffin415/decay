import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Person {
	Body body;
	PApplet app;
	
	float noiseScale = 0.02f;
	
//	PImage head, ribs;
	
	public Person(PApplet app) {
		this.app = app;		
//		head = app.loadImage("skull.png");
//		ribs = app.loadImage("ribcase.png");
	}

	public void update(Body body) {
		this.body = body;
	}	
	
	public void drawHead(PImage image) {
	 // Get a noise value based on xoff and scale it according to the window's width
		float nx = 0;
		float ny=0;
		if(body.getJoint(Body.HEAD) != null) {
			nx = app.noise(body.getJoint(Body.HEAD).x)*noiseScale;
			ny = app.noise(body.getJoint(Body.HEAD).y)*noiseScale;
			  
			app.image(image, 0, 0);
		}
	}
	
	public Body getBody() {
		return body;
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