package PopDemBubbles;

import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import org.mt4j.input.gestureAction.DefaultZoomAction;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.zoomProcessor.*;



public class GameThread{
	MTApplication mtapp;
	BubbleScene scene;
	boolean isRunning;
	Timer timer;
	Random rng;
	CopyOnWriteArrayList<MTEllipse> bubbles;
	int score;
	int lives = 3;
	int maxBubblesOnScreen = 3;
	float shrinkRate = 0.9995f;
	
	public GameThread(BubbleScene scene, MTApplication mtapp) {
		this.mtapp = mtapp;
		this.scene = scene;
		rng = new Random();
		bubbles = new CopyOnWriteArrayList<MTEllipse>();
		timer = new Timer();
		//add initial bubbles
		for (int i = 0; i < 3; i ++){
			addNewBubble();
		}
		isRunning = true;
		timer.schedule(new gameLoop(),0,1000/50);
		
		timer.schedule(new addBubbles(),0,1500);

	}
	
	public class addBubbles extends java.util.TimerTask{
		public void run(){
			if (bubbles.size() < maxBubblesOnScreen){
				addNewBubble();
			}
		}
	}

	private void update() {		
		//Shrink bubbles
		for (MTEllipse bubble: bubbles){
			bubble.scale(shrinkRate,shrinkRate,shrinkRate, bubble.getCenterPointGlobal());
			
			if (bubble.get2DPolygonArea()  < 25000){
				bubble.destroy();
				bubbles.remove(bubble);
				
				lives--;
				scene.lifeText.setText("Lives: " + lives);
				if (lives == 0){
					isRunning = false;
					scene.GameOver.setText("GAME OVER");
				}
			}
		}
	}
	
	private void addNewBubble(){
		
		int size = rng.nextInt(50) + 250;
		
		final MTEllipse circle = new MTEllipse(mtapp, new Vector3D(rng.nextInt(800)+ 100,rng.nextInt(450)+150), size,size);
		bubbles.add(circle);
		
		
		circle.setFillColor(new MTColor(255,0,255,125));
		circle.unregisterAllInputProcessors();
		circle.removeAllGestureEventListeners();
		
		circle.registerInputProcessor(new ZoomProcessor(mtapp));
		circle.addGestureListener(ZoomProcessor.class, new IGestureEventListener(){

			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				circle.setFillColor(new MTColor(0,255,0,125));
				circle.scale(1.007f, 1.007f, 1.007f, circle.getCenterPointGlobal());
				if (circle.get2DPolygonArea()  > 300000){
					circle.destroy();
					bubbles.remove(circle);
					score+=10;
					scene.scoreText.setText("Score: " + score);

					
					//Increase difficulty after scoring
					shrinkRate -= 0.0003f;
					maxBubblesOnScreen ++;
				}
				return false;
			}
			
		});
		
		scene.getCanvas().addChild(circle);
		
		
		
	}

	public void destroyAllBubbles(){
		for (MTEllipse bubble: bubbles){
			bubble.destroy();
			bubbles.remove(bubble);
		}
	}
	
	private class gameLoop extends java.util.TimerTask
	{
	    public void run() //this becomes the loop
	    {
    		update();
      
    		if (!isRunning){
    			timer.cancel();
    			destroyAllBubbles();
    		}	
	    	
	    }
	    	
	}
}
