package PopDemBubbles;
import org.mt4j.MTApplication;

public class RunPopDemBubbles extends MTApplication {

	public static void main(String[] args) {
		initialize();
	}

	@Override
	public void startUp() {
		addScene(new BubbleScene(this));
	}

}
