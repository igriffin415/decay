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
	
	float[] prevPosHead;
	float[] prevPosRibs;
	
	public Person(PApplet app) {
		this.app = app;		
		head = app.loadImage("assets/goathead.png");
		ribs = app.loadImage("assets/ribcase.png");
		flowers = new Bouquet(app);
		headFlower = app.loadImage("assets/pinkflower.png");
		prevPosHead = new float[] {0.0f, 0.0f};
		prevPosRibs = new float[] {0.0f, 0.0f};
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
			prevPosHead[0] = headv.x;
			prevPosHead[1] = headv.y;
			
			app.image(head, headv.x, headv.y+.1f,
					  1.3f, 1.1f);
			
			//draw flower on head
			app.image(headFlower, headv.x, headv.y+.2f, .2f, .2f);
//			if(disappear) {
//				//make it black
//				app.tint(0);
//			} else {
//				//color
//				app.tint(255);
//			}
		}
		else {
			app.image(head, prevPosHead[0], prevPosHead[1]+.1f,
					  1.3f, 1.1f);
			
			//draw flower on head
			app.image(headFlower, prevPosHead[0], prevPosHead[1]+.2f, .2f, .2f);
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
			prevPosRibs[0] = ribsv.x;
			prevPosRibs[1] = ribsv.y;
			
			//draw ribs themselves
			app.image(ribs, ribsv.x, ribsv.y - .25f,
					  ribs.width/100, ribs.height/100);
			
			//draw flowers on the ribs
			flowerCheck();
			boolean stateChange = flowers.draw(ribsv.x, ribsv.y, disappear);
		}
		else {
			app.image(ribs, prevPosRibs[0], prevPosRibs[1] - .25f,
					  ribs.width/100, ribs.height/100);
			
			//draw flowers on the ribs
			flowerCheck();
			boolean stateChange = flowers.draw(prevPosRibs[0], prevPosRibs[1], disappear);
//			if(stateChange && disappear) {
//				noDecay = false;
//			}
//			if(stateChange && !disappear) {
//				noDecay = true;
//			}
		}
	}
	
	public PVector getHead() {
		return headv;
	}
	
	public PVector getSpine() {
		return ribsv;
	}
	
	private void flowerCheck() {
		if(ribsv != null) {
			if(ribsv.x < 0) {
				disappear = true;
			}
			else {
				disappear = false;
			}
		}
	}
}