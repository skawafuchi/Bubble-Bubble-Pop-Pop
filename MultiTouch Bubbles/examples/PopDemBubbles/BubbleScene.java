package PopDemBubbles;

import java.util.Timer;

import org.mt4j.MTApplication;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

public class BubbleScene extends AbstractScene{
	boolean isRunning;
	public int score;
	public MTTextField scoreText, lifeText,GameOver;
	public BubbleScene(MTApplication mtapp) {
		super(mtapp,"Bubble Scene");
		
		
		//set text things in scene
		IFont font = FontManager.getInstance().getDefaultFont(mtapp);
        MTTextField title = new MTTextField(0,0,200,35,font,mtapp); 
        title.setText("Pop The Bubbles");
        title.unregisterAllInputProcessors();
        title.removeAllGestureEventListeners();
        this.getCanvas().addChild(title);
        
        MTTextField desc = new MTTextField(0,20,300,35,font,mtapp); 
        desc.setText("Use two fingers to pull bubbles apart!");
        desc.unregisterAllInputProcessors();
        desc.removeAllGestureEventListeners();
        this.getCanvas().addChild(desc);
        
        
        scoreText = new MTTextField(0,40,100,35,font,mtapp);   
        scoreText.setText("Score: 0");
        scoreText.unregisterAllInputProcessors();
        scoreText.removeAllGestureEventListeners();
        this.getCanvas().addChild(scoreText);
        
        lifeText = new MTTextField(0,60,100,35,font,mtapp);   
        lifeText.setText("Lives: 3");
        lifeText.unregisterAllInputProcessors();
        lifeText.removeAllGestureEventListeners();
        this.getCanvas().addChild(lifeText);
        
        
        GameOver = new MTTextField(400,350,200,35,font,mtapp);   
        GameOver.unregisterAllInputProcessors();
        GameOver.removeAllGestureEventListeners();
        this.getCanvas().addChild(GameOver);
        
		
		GameThread gt = new GameThread(this,mtapp);
	}

	@Override
	public void init() {
		//set bg color
		this.setClearColor(new MTColor(255,255,255,255));

	}

	@Override
	public void shutDown() {
		
	}
	
}
