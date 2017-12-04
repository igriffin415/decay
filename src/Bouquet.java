import java.util.ArrayList;

import processing.core.PApplet;

/**
 * Contains and maintains all of the flowers appearing on the rib cage
 * @author Sara, Isabelle, Lauren
 *
 */
public class Bouquet {
	
	private float x;
	private float y;
	private ArrayList<Flower> flowerList;
	private PApplet parent;
	
	public Bouquet(PApplet parent) {
		//create a flower object for each set location from the center
		this.parent = parent;
		flowerList = new ArrayList<Flower>();
		float displaceX = -.7f;
		float displaceY = -.7f;
		
		//customize each row
		flowerList.add(new Flower(this.parent, 0f, .19f));
		displaceY = .14f;
		displaceX = -.1f;
		for(int i=0; i<2; i++) {
			flowerList.add(new Flower(this.parent, displaceX, displaceY));
			displaceX += .2f;
		}
		displaceY = .05f;
		displaceX = -.25f;
		for(int i=0; i<4; i++) {
			flowerList.add(new Flower(this.parent, displaceX, displaceY));
			displaceX += .15f;
		}
		displaceY = -.06f;
		displaceX = -.3f;
		for(int i=0; i<5; i++) {
			flowerList.add(new Flower(this.parent, displaceX, displaceY));
			displaceX += .15f;
		}
		displaceY = -.15f;
		displaceX = -.25f;
		for(int i=0; i<5; i++) {
			flowerList.add(new Flower(this.parent, displaceX, displaceY));
			displaceX += .15f;
		}
		displaceY = -.24f;
		displaceX = -.3f;
		for(int i=0; i<5; i++) {
			flowerList.add(new Flower(this.parent, displaceX, displaceY));
			displaceX += .15f;
		}
		displaceY = -.33f;
		displaceX = -.4f;
		for(int i=0; i<6; i++) {
			flowerList.add(new Flower(this.parent, displaceX, displaceY));
			displaceX += .15f;
		}
		displaceY = -.42f;
		displaceX = -.35f;
		for(int i=0; i<6; i++) {
			flowerList.add(new Flower(this.parent, displaceX, displaceY));
			displaceX += .15f;
		}
		displaceY = -.51f;
		displaceX = -.3f;
		for(int i=0; i<5; i++) {
			flowerList.add(new Flower(this.parent, displaceX, displaceY));
			displaceX += .15f;
		}
		displaceY = -.62f;
		displaceX = -.3f;
		for(int i=0; i<2; i++) {
			flowerList.add(new Flower(this.parent, displaceX, displaceY));
			displaceX += .6f;
		}
	}
	
	public boolean draw(float newX, float newY, boolean disappear) {
		boolean stateChange = false;
		for(Flower flower : flowerList) {
			if(disappear) {
				stateChange = flower.vanish();
			} else {
				stateChange = flower.grow();
			}
			flower.draw(newX, newY);
		}
		return stateChange;
	}
	
	public void addAllGrow(float add) {
		for(Flower flower : flowerList){
			flower.addGrow(add);
		}
	}
}