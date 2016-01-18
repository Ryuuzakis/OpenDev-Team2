package gameframework.motion.overlapping;

/**
 * This interface is designed to provide a system to check the Overlappable
 * for overlapping. When two entities overlap, it will trigger methods
 * that are meant to be called in such occasions.
 */
public interface OverlapProcessor {
	public void addOverlappable(Overlappable p);

	public void removeOverlappable(Overlappable p);

	public void setOverlapRules(OverlapRulesApplier overlapRules);

	/**
	 * This method examines all the Overlappable that it has to check
	 * if they are overlaping another Overlappable. If so, it will
	 * apply the rule that must be called when it happens.
	 */
	public void processOverlapsAll();
}
