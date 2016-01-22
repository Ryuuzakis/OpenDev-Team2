package gameframework.drawing;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * This implementation of {@link SpriteManager} assumes that sprite types are in
 * rows whereas increments of a type are in columns
 * 
 */
public class SpriteManagerDefaultImpl implements SpriteManager {

	private final DrawableImage image;
	private Map<String, Integer> types;
	private final int spriteSizeWidth;
	private final int spriteSizeHeight;
	private int spriteNumber = 0;
	private final int maxSpriteNumber;
	private int currentRow;
	private final int width;
	private final int height;
	
	
	// Square
	public SpriteManagerDefaultImpl(DrawableImage image, int renderingSize,
			int maxSpriteNumber) {
		this.width = renderingSize;
		this.height = renderingSize;
		this.image = image;
		this.maxSpriteNumber = maxSpriteNumber;
		this.spriteSizeWidth = image.getWidth() / maxSpriteNumber;
		this.spriteSizeHeight = spriteSizeWidth;
	}
	
	
	/**
	 * Keep the height of the image
	 * 
	 * @param image
	 * @param maxSpriteNumber
	 */
	public SpriteManagerDefaultImpl(DrawableImage image, int maxSpriteNumber) {
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.spriteSizeWidth = width / maxSpriteNumber;
		this.spriteSizeHeight = height;
		this.image = image;
		this.maxSpriteNumber = maxSpriteNumber;
	}

	@Override
	public void setTypes(String... types) {
		int i = 0;
		this.types = new HashMap<String, Integer>(types.length);
		for (String type : types) {
			this.types.put(type, i);
			i++;
		}
	}

	@Override
	public void draw(Graphics g, Point position) {
		// Destination image coordinates
		int dx1 = (int) position.getX();
		int dy1 = (int) position.getY();
		int dx2 = dx1 + width;
		int dy2 = dy1 + height;

		// Source image coordinates
		int sx1 = spriteNumber * spriteSizeWidth;
		int sy1 = currentRow * spriteSizeHeight;
		int sx2 = sx1 + spriteSizeWidth;
		int sy2 = sy1 + spriteSizeHeight;
		g.drawImage(image.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,
				null);
	}

	@Override
	public void setType(String type) {
		if (!types.containsKey(type)) {
			throw new IllegalArgumentException(type
					+ " is not a valid type for this sprite manager.");
		}
		this.currentRow = types.get(type);
	}

	@Override
	public void increment() {
		spriteNumber = (spriteNumber + 1) % maxSpriteNumber;
	}

	@Override
	public void reset() {
		spriteNumber = 0;
	}

	@Override
	public void setIncrement(int increment) {
		this.spriteNumber = increment;
	}
}
