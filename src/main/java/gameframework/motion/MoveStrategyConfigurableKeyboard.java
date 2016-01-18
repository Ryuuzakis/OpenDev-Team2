package gameframework.motion;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link SpeedVector speed vectors} based on what the user typed.
 */
public class MoveStrategyConfigurableKeyboard extends KeyAdapter implements MoveStrategy {
	protected SpeedVector speedVector;
	protected final Boolean alwaysMove;
	protected Map<Integer, Point> directions;
	protected Set<Integer> keyPressed;
	protected boolean combineDirections;

	public MoveStrategyConfigurableKeyboard() {
		this(true);
	}

	public MoveStrategyConfigurableKeyboard(Boolean alwaysMove) {
		this(alwaysMove, new SpeedVector(new Point(0, 0)));
	}

	public MoveStrategyConfigurableKeyboard(SpeedVector speedVector) {
		this(true, speedVector);
	}
	
	public MoveStrategyConfigurableKeyboard(Boolean alwaysMove, SpeedVector speedVector) {
		this(alwaysMove, speedVector, false);
	}

	/**
	 * @param alwaysMove is a boolean value that decide if a player moves continually or not. (True by default)
	 * @param speedVector is a given custom speedVector for the strategy.
	 * @param combineDirections is a boolean value that decide if directions are combined (False by default)
	 */
	public MoveStrategyConfigurableKeyboard(Boolean alwaysMove, SpeedVector speedVector, Boolean combineDirections) {
		this.alwaysMove = alwaysMove;
		this.speedVector = speedVector;
		this.directions = new HashMap<Integer, Point>();
		this.keyPressed = new HashSet<Integer>();
		this.combineDirections = combineDirections;
	}
	
	/**
	 * Adds a direction controlled by a key
	 * @param key The key associated with the direction
	 * @param direction The direction used when the key is pressed
	 */
	public void addKeyDirection(int key, Point direction) {
		directions.put(key, direction);
	}

	@Override
	public SpeedVector getSpeedVector() {
		return speedVector;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		keyPressed(event.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent event) {
		keyReleased(event.getKeyCode());
	}
	
	protected void keyPressed(int keyCode) {
		keyPressed.add(keyCode);
		updateDirection();
	}
	
	protected void keyReleased(int keyCode) {
		keyPressed.remove(keyCode);
		updateDirection();
	}
	
	/**
	 * Update the direction depending on the keys pressed
	 */
	protected void updateDirection() {
		final Point newDirection = new Point(0, 0);
		
		for (Integer keyCode : keyPressed) {
			final Point keyDirection = directions.get(keyCode);
			if (keyDirection != null) {
				newDirection.x += keyDirection.x;
				newDirection.y += keyDirection.y;
				
				// If we don't combine directions, then we should stop here
				if (!combineDirections) {
					break;
				}
			}
		}
		
		if (newDirection.x != 0 || newDirection.y != 0 || !alwaysMove)
			speedVector.setDirection(newDirection);
	}

	@Override
	public int getSpeed() {
		return this.speedVector.getSpeed();
	}

	@Override
	public void setSpeed(int speed) {
		this.speedVector.setSpeed(speed);
	}
}
