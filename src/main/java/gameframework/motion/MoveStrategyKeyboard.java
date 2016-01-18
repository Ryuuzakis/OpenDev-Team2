package gameframework.motion;

import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link SpeedVector speed vectors} based on what the user typed.
 */
public class MoveStrategyKeyboard extends MoveStrategyConfigurableKeyboard {
	public MoveStrategyKeyboard() {
		this(true);
	}

	public MoveStrategyKeyboard(Boolean alwaysMove) {
		this(alwaysMove, new SpeedVector(new Point(0, 0)));
	}

	public MoveStrategyKeyboard(SpeedVector speedVector) {
		this(true, speedVector);
	}
	
	public MoveStrategyKeyboard(Boolean alwaysMove, SpeedVector speedVector) {
		this(alwaysMove, speedVector, false);
	}
	
	/**
	 * @param alwaysMove is a boolean value that decide if a player moves continually or not. (True by default)
	 * @param speedVector is a given custom speedVector for the strategy.
	 * @param combineDirections is a boolean value that decide if directions are combined (False by default)
	 */
	public MoveStrategyKeyboard(Boolean alwaysMove, SpeedVector speedVector, Boolean combineDirections) {
		super(alwaysMove, speedVector, combineDirections);
		
		addKeyDirection(KeyEvent.VK_RIGHT, new Point(1, 0));
		addKeyDirection(KeyEvent.VK_LEFT, new Point(-1, 0));
		addKeyDirection(KeyEvent.VK_DOWN, new Point(0, 1));
		addKeyDirection(KeyEvent.VK_UP, new Point(0, -1));
	}
}
