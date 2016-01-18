package gameframework.motion.overlapping;

import gameframework.motion.GameMovable;
import gameframework.motion.SpeedVector;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OverlapProcessorDefaultImpl implements OverlapProcessor {

	/**
	 * These two lists contain all overlappables for which we want to compute
	 * overlaps. We distinguish between movable and non-movable because two
	 * non-movables never overlap.
	 */
	protected ConcurrentLinkedQueue<Overlappable> nonMovableOverlappables;
	protected ConcurrentLinkedQueue<Overlappable> movableOverlappables;

	protected OverlapRulesApplier overlapRules;

	public OverlapProcessorDefaultImpl() {
		nonMovableOverlappables = new ConcurrentLinkedQueue<Overlappable>();
		movableOverlappables = new ConcurrentLinkedQueue<Overlappable>();
	}

	@Override
	public void addOverlappable(final Overlappable p) {
		if (p.isMovable()) {
			movableOverlappables.add(p);
		} else {
			nonMovableOverlappables.add(p);
		}
	}

	@Override
	public void removeOverlappable(final Overlappable p) {
		if (p.isMovable()) {
			movableOverlappables.remove(p);
		} else {
			nonMovableOverlappables.remove(p);
		}
	}

	@Override
	public void setOverlapRules(final OverlapRulesApplier overlapRules) {
		this.overlapRules = overlapRules;
	}

	// for optimization purpose : prevents to compute two times the overlaps
	private List<Overlappable> movablesTmp;

	@Override
	public void processOverlapsAll() {
		final Vector<Overlap> overlaps = new Vector<Overlap>();

		movablesTmp = new Vector<Overlappable>(movableOverlappables);
		for (final Overlappable movableOverlappable : movableOverlappables) {
			movablesTmp.remove(movableOverlappable);
			computeOneOverlap(movableOverlappable, overlaps);
		}
		overlapRules.applyOverlapRules(overlaps);
	}

	/**
	 * This method checks if movableOverlappable overlaps any of the other
	 * overlappables of this processor. If so, it adds a new overlap in the
	 * overlaps vector
	 * @param movableOverlappable the overlappable tested
	 * @param overlaps the overlaps
	 */
	protected void computeOneOverlap(final Overlappable movableOverlappable,
			final Vector<Overlap> overlaps) {

		final Rectangle targetBox = getTargetBox(movableOverlappable);

		processOverlappables(movableOverlappable, overlaps, targetBox,
				new ArrayList<Overlappable>(nonMovableOverlappables));
		processOverlappables(movableOverlappable, overlaps, targetBox, movablesTmp);
	}

	/**
	 * This method checks if the movableOverlappable overlaps any of the overlappables
	 * in the list testedOverlappables. If so, it adds a new overlap in the
	 * overlaps vector
	 * @param movableOverlappable the overlappable tested
	 * @param overlaps the overlaps
	 * @param targetBox the rectangle representing the future position of the overlappable
	 * 		if it can move
	 * @param testedOverlappables the overlappables tested
	 */
	protected void processOverlappables(final Overlappable movableOverlappable,
			final Vector<Overlap> overlaps, final Rectangle targetBox,
			final List<Overlappable> testedOverlappables) {

		for (final Overlappable otherOverlappable : testedOverlappables) {
			final Rectangle otherBoundingBox = otherOverlappable.getBoundingBox();

			// We check that the overlappable tested is not the same as the one
			// from the list, and then that their boundingBoxes intersect or not
			if (!movableOverlappable.getBoundingBox().equals(otherBoundingBox)
					&& targetBox.intersects(otherBoundingBox)) {
				overlaps.add(new Overlap(movableOverlappable, otherOverlappable));
			}
		}
	}

	/**
	 * This method calculates the future position of the overlappable if it can move
	 * @param overlappable the overlappable tested
	 * @return the future boundingBox of the overlappable
	 */
	protected Rectangle getTargetBox(final Overlappable overlappable) {
		final GameMovable movable = (GameMovable) overlappable;
		final Rectangle r = (Rectangle) movable.getBoundingBox().clone();
		final SpeedVector vector = movable.getSpeedVector();

		r.x += vector.getDirection().x * vector.getSpeed();
		r.y += vector.getDirection().y * vector.getSpeed();

		return r;
	}
}
