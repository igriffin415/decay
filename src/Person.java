import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Person {
	private static int TRAIL = 30;
	
	private Body body;
	private PApplet app;
	private PVector headv, ribsv, lefthandv, righthandv;
	private PImage head, ribs;
	private Bouquet flowers;
	private boolean disappear = false; //true if flowers should be disappearing
	
	private Decay state;

	PImage headFlower, smoke;
	private boolean noDecay = true;

	
	PVector prevPosHead;
	PVector  prevPosRibs;

	PVector[] lefthandarray;
	PVector[] righthandarray;

	PVector prevPosLeft;
	PVector prevPosRight;

	int counter;

	
	public Person(PApplet app) {
		this.app = app;		
		head = app.loadImage("assets/goathead.png");
		ribs = app.loadImage("assets/ribcase.png");
		smoke = app.loadImage("assets/smoke.png");
		flowers = new Bouquet(app);
		headFlower = app.loadImage("assets/pinkflower.png");
		state = Decay.Sun;

		prevPosHead = new PVector(0,0,0);
		prevPosRibs = new PVector(0,0,0);

		lefthandarray = new PVector[TRAIL];
		righthandarray = new PVector[TRAIL];

		prevPosLeft = new PVector(999,999,0);
		prevPosRight = new PVector(999,999,0);

		counter = 0;
	}

	public void update(Body body) {
		headv = body.getJoint(Body.HEAD);
		ribsv = body.getJoint(Body.SPINE_BASE);
		
		//track hands
		lefthandv = body.getJoint(Body.HAND_LEFT);
		righthandv = body.getJoint(Body.HAND_RIGHT);
		
		if(lefthandv != null){
			prevPosLeft = lefthandv;
			lefthandarray[counter] = lefthandv;

		} else{
			lefthandarray[counter] = prevPosLeft;
		}

		if(righthandv != null){
			prevPosRight = righthandv;
			righthandarray[counter] = righthandv;

		} else {
			righthandarray[counter] = prevPosRight;

		}
	}	
	
	public void draw() {
		this.drawHead();
		this.drawRibs();
		this.drawHandTrails();
	}
	
	
	public void drawHandTrails(){
		
		PVector leftH;
		PVector rightH;

		//drawing here vvv

		for(int i = 0; i < TRAIL; i++){
			if(counter == 0 && i == 0 ){
				 leftH = lefthandarray[0];
				 rightH = righthandarray[0];
			} else {
				leftH = lefthandarray[(counter+i)%TRAIL];
				rightH = righthandarray[(counter+i)%TRAIL];
			}

			if(leftH != null && rightH != null){
				app.image(smoke, leftH.x-.1f, leftH.y,
							.2f, .2f);
				app.image(smoke, rightH.x+.1f, rightH.y,
						.2f, .2f);
				
//				app.fill(192, ((255/TRAIL) * i));
//				app.noStroke();
//				app.ellipse(leftH.x, leftH.y, .1f, .1f);
//				app.ellipse(rightH.x, rightH.y, .1f, .1f);
			}
		}
		
		counter++;
		if(counter >= TRAIL){
			counter = 0;
		}
	}
	
	/**
	 * Display the background appropriate to the current state
	 */
	public void drawBackground() {
		if(state == Decay.Sun) {
			//nice background
		} else if(state == Decay.Moon) {
			//vein thing
		}
	}
	
	public Decay getState() {
		return state;
	}
	
	public void drawHead() {
		if(headv != null) {	
			float smoothx = (prevPosHead.x+headv.x)/2f;
			float smoothy = (prevPosHead.y+headv.y)/2f;
			app.image(head, smoothx, smoothy+.1f,
					  1.3f, 1.1f);
			
			//draw flower on head
			if (disappear) {
				app.tint(230, 0, 150);
				app.image(headFlower, smoothx, smoothy + .2f, .2f, .2f);
				app.tint(255,255,255);
			} else {
				app.tint(255, 255, 255);
				app.image(headFlower, smoothx, smoothy + .2f, .2f, .2f);
			}
			prevPosHead = headv;
		}
		else {
			app.image(head, prevPosHead.x, prevPosHead.y+.1f,
					  1.3f, 1.1f);
			
			//draw flower on head
			app.image(headFlower, prevPosHead.x, prevPosHead.y+.2f, .2f, .2f);
			if (disappear) {
				app.tint(230, 0, 150);
				app.image(headFlower, prevPosHead.x, prevPosHead.y + .2f, .2f, .2f);
				app.tint(255,255,255);
			} else {
				app.tint(255, 255, 255);
				app.image(headFlower, prevPosHead.x, prevPosHead.y + .2f, .2f, .2f);
			}
		}
	}
	
	public void drawRibs() {
		if(ribsv != null) {		
			float smoothx = (prevPosRibs.x+ribsv.x)/2f;
			float smoothy = (prevPosRibs.y+ribsv.y)/2f;
			//draw ribs themselves
			app.image(ribs, smoothx, smoothy - .25f,
					  ribs.width/100, ribs.height/100);
			
			//draw flowers on the ribs
			flowerCheck();
			boolean stateChange = flowers.draw(smoothx, smoothy, disappear);
			if (stateChange && disappear) {
				state = Decay.Moon;
			}
			if (stateChange && !disappear) {
				state = Decay.Sun;
			}
			prevPosRibs = ribsv;
		}
		else {
			app.image(ribs, prevPosRibs.x, prevPosRibs.y - .25f,
					  ribs.width/100, ribs.height/100);
			
			//draw flowers on the ribs
			flowerCheck();
			boolean stateChange = flowers.draw(prevPosRibs.x, prevPosRibs.y, disappear);
			if (stateChange && disappear) {
				state = Decay.Moon;
			}
			if (stateChange && !disappear) {
				state = Decay.Sun;
			}
		}
	}
	
	public PVector getHead() {
		return headv;
	}
	
	public PVector getSpine() {
		return ribsv;
	}
	
	private void flowerCheck() {
//		if(ribsv != null) {
//			if(ribsv.x < 0) {
//				disappear = true;
//			}
//			else {
//				disappear = false;
//			}
//		}
		
		//if the hands are in front of the face-ish
		float diam = 0.3f;
		if(lefthandv != null && righthandv != null && headv != null) {
			if(Math.abs(lefthandv.x-headv.x) < diam && Math.abs(righthandv.x-headv.x) < diam
					&& Math.abs(lefthandv.y-headv.y) < diam && Math.abs(righthandv.y-headv.y) < diam) {
				
				if(lefthandv.z < headv.z && righthandv.z < headv.z) {
					disappear = true;
				} else {
					disappear = false;
				}
			} else {
				disappear = false;
			}
		}
	}
}