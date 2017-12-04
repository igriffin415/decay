import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Person {
	private Body body;
	private PApplet app;
	private PVector headv, ribsv, lefthandv, righthandv;
	private PImage head, ribs;
	private Bouquet flowers;
	private boolean disappear = false; //true if flowers should be disappearing
	PImage headFlower;
	private boolean noDecay = true;
	
	public Person(PApplet app) {
		this.app = app;		
		head = app.loadImage("assets/skull.png");
		ribs = app.loadImage("assets/ribcase.png");
		flowers = new Bouquet(app);
		headFlower = app.loadImage("assets/pinkflower.png");
	}

	public void update(Body body) {
		headv = body.getJoint(Body.HEAD);
		ribsv = body.getJoint(Body.SPINE_BASE);
		
		//track hands
		lefthandv = body.getJoint(Body.HAND_LEFT);
		righthandv = body.getJoint(Body.HAND_RIGHT);
	}	
	
	/**
	 * Display the background appropriate to the current state
	 */
	public void drawBackground() {
		if(noDecay) {
			//nice background
		} else {
			//vein thing
		}
	}
	
	public void drawHead() {
		if(headv != null) {
			app.image(head, headv.x, headv.y+.1f,
					  head.width/60, head.height/60);
			
			//determine here whether event is being triggered - hands above head maybe?
			if(lefthandv != null && righthandv != null) {
				if(lefthandv.y > headv.y && righthandv.y > headv.y) {
					disappear = true;
				} else {
					disappear = false;
				}
			}
			
			//draw flower on head
			app.image(headFlower, headv.x, headv.y+.2f, .2f, .2f);
			if(disappear) {
				//make it black
				app.tint(0);
			} else {
				//color
				app.tint(255);
			}
		}
	}
	
	public void drawRibs() {
		if(ribsv != null) {
			//draw ribs themselves
			app.image(ribs, ribsv.x, ribsv.y - .25f,
					  ribs.width/100, ribs.height/100);
			
			//draw flowers on the ribs
			boolean stateChange = flowers.draw(ribsv.x, ribsv.y, disappear);
			if(stateChange && disappear) {
				noDecay = false;
			}
			if(stateChange && !disappear) {
				noDecay = true;
			}
		}
	}
	
	public PVector getHead() {
		PVector head = body.getJoint(Body.HEAD);
		if(head != null)
			return head;
		return null;
	}
}