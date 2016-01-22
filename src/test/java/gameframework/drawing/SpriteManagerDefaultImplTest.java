package gameframework.drawing;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpriteManagerDefaultImplTest {

	SpriteManagerDefaultImpl manager, managerRectangle;
	int maxSpriteNumber = 6;
	int renderingSize = 16;
	int spriteSize = 16;
	int sourcePictureWidth = maxSpriteNumber * spriteSize;

	int actualSourceX, actualSourceY;

	DrawableImage image;
	GameCanvas canvas;
	MockGraphics graphics;

	public void createGraphics() {
		graphics = new MockGraphics() {
			@Override
			public boolean drawImage(Image img, int dx1, int dy1, int dx2,
					int dy2, int sx1, int sy1, int sx2, int sy2,
					ImageObserver observer) {
				actualSourceX = sx1;
				actualSourceY = sy1;
				return false;
			}
		};
	}

	public void createCanvas() {
		canvas = new GameCanvasDefaultImpl();
		canvas.setBounds(0, 0, 200, 200);
	}

	public void createImage() {
		image = new DrawableImage("/courbes.png", canvas) {
			@Override
			public int getWidth() {
				return sourcePictureWidth;
			}
		};
	}

	public void createManager() {
		manager = new SpriteManagerDefaultImpl(image, renderingSize,
				maxSpriteNumber);
		manager.setTypes("foo", "bar");
	}
	
	public void createManagerRectangle() {
		managerRectangle = new SpriteManagerDefaultImpl(image, maxSpriteNumber);
		managerRectangle.setTypes("foo", "bar");
	}

	@Before
	public void createObjects() {
		createCanvas();
		createImage();
		createGraphics();
		createManager();
		createManagerRectangle();
	}

	@Test(expected = IllegalArgumentException.class)
	public void validTypeRequired() {
		manager.setType("neither foo nor bar");
		managerRectangle.setType("neither foo nor bar");
	}

	@Test
	public void typeImpactRow() throws Exception {
		manager.setType("foo");
		manager.draw(graphics, new Point(0, 0));
		assertEquals(0, actualSourceX);
		assertEquals(0, actualSourceY);

		manager.setType("bar");
		manager.draw(graphics, new Point(0, 0));
		assertEquals(0, actualSourceX);
		assertEquals(spriteSize, actualSourceY);
	}
	
	@Test
	public void typeImpactRowManagerRectangle() throws Exception {
		managerRectangle.setType("foo");
		managerRectangle.draw(graphics, new Point(0, 0));
		assertEquals(0, actualSourceX);
		assertEquals(0, actualSourceY);

		managerRectangle.setType("bar");
		managerRectangle.draw(graphics, new Point(0, 0));
		assertEquals(0, actualSourceX);
		assertEquals(image.getHeight(), actualSourceY);
	}

	@Test
	public void incrementImpactColumn() throws Exception {
		manager.setType("foo");
		manager.draw(graphics, new Point(0, 0));
		assertEquals(0, actualSourceX);
		assertEquals(0, actualSourceY);

		manager.increment();
		manager.draw(graphics, new Point(0, 0));
		assertEquals(spriteSize, actualSourceX);
		assertEquals(0, actualSourceY);
	}
	
	@Test
	public void incrementImpactColumnForRectangle() throws Exception {
		managerRectangle.setType("foo");
		managerRectangle.draw(graphics, new Point(0, 0));
		assertEquals(0, actualSourceX);
		assertEquals(0, actualSourceY);

		managerRectangle.increment();
		managerRectangle.draw(graphics, new Point(0, 0));
		assertEquals(spriteSize, actualSourceX);
		assertEquals(0, actualSourceY);
	}

	@Test
	public void resetSetsColumnTo0() throws Exception {
		manager.setType("foo");
		manager.increment();
		manager.draw(graphics, new Point(0, 0));
		assertEquals(spriteSize, actualSourceX);
		assertEquals(0, actualSourceY);

		manager.reset();
		manager.draw(graphics, new Point(0, 0));
		assertEquals(0, actualSourceX);
		assertEquals(0, actualSourceY);
	}
	
	@Test
	public void resetSetsColumnTo0ForRectangle() throws Exception {
		managerRectangle.setType("foo");
		managerRectangle.increment();
		managerRectangle.draw(graphics, new Point(0, 0));
		assertEquals(spriteSize, actualSourceX);
		assertEquals(0, actualSourceY);

		managerRectangle.reset();
		managerRectangle.draw(graphics, new Point(0, 0));
		assertEquals(0, actualSourceX);
		assertEquals(0, actualSourceY);
	}

	@Test
	public void setIncrementChangesColumn() throws Exception {
		int column = 3;

		manager.setType("foo");
		manager.setIncrement(column);
		manager.draw(graphics, new Point(0, 0));
		assertEquals(column * spriteSize, actualSourceX);
	}
	
	@Test
	public void setIncrementChangesColumnForRectangle() throws Exception {
		int column = 3;

		managerRectangle.setType("foo");
		managerRectangle.setIncrement(column);
		managerRectangle.draw(graphics, new Point(0, 0));
		assertEquals(column * spriteSize, actualSourceX);
	}

}
