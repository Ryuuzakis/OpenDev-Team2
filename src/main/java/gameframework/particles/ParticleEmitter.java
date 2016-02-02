package gameframework.particles;

import gameframework.game.GameEntity;
import gameframework.particles.behaviors.ParticleBehavior;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

/**
 * A game entity that can be added to a game universe
 * This entity can emit, draw and update an infinite number of particles
 */
public class ParticleEmitter implements GameEntity {
	/* List containing every "alive" particles that will be updated */
	protected List<Particle> aliveParticles;
	/* List containing every particles that should be removed */
	protected List<Particle> deadParticles;
	
	/**
	 * Constructor
	 */
	public ParticleEmitter() {
		aliveParticles = new LinkedList<>();
		deadParticles = new LinkedList<>();
	}
	
	/**
	 * Update a particle according to its behavior
	 * @param particle Particle to update
	 */
	protected void update(Particle particle) {
		particle.incrementTimeAlive();
		
		if (particle.isDead()) {
			deadParticles.add(particle);
		}
		
		if (particle.isDrawn()) {
			particle.update();
		}
	}
	
	/**
	 * Remove every dead particles contained in deadParticles
	 */
	protected void removeDeadParticles() {
		aliveParticles.removeAll(deadParticles);
		deadParticles.clear();
	}
	
	/**
	 * Draw a single particle on a Graphics
	 * @param particle The particle to draw
	 * @param graphics The Graphics
	 */
	public void drawParticle(Particle particle, Graphics graphics) {
		if (particle.isDrawn()) {
			graphics.setColor(particle.getColor());
			graphics.fillRect((int) particle.getX(), (int) particle.getY(), particle.getWidth(), particle.getHeight());
		}
	}
	
	/**
	 * Draw every particles that are still alive
	 * @see gameframework.game.GameEntity#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		for (Particle p : aliveParticles) {
			update(p);
			drawParticle(p, g);
		}
		
		// We remove the particles after the loop to avoid concurrent modifications
		removeDeadParticles();
	}
	
	/**
	 * Emits a certain amount of particles that will react
	 * according to the specified behavior
	 * @param color Color of these particle
	 * @param rectangle Rectangle defining the initial position and size of the particles to emit
	 * @param nb Number of particles to emit
	 * @param behavior Behavior of these newly created particles
	 */
	public void emit(Color color, Rectangle rectangle, int nb, ParticleBehavior behavior) {
		for (; nb > 0; --nb) {
			aliveParticles.add(new Particle(nb, color, rectangle, behavior));
		}
	}

	/**
	 * Returns always false as this entity is not movable and shouldn't be tested for collisions
	 * @see gameframework.game.GameEntity#isMovable()
	 */
	@Override
	public boolean isMovable() {
		return false;
	}

}
