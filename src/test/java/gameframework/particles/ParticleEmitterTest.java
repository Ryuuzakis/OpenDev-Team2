package gameframework.particles;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Rectangle;

import gameframework.particles.behaviors.DefaultParticleBehavior;
import gameframework.particles.behaviors.ParticleBehaviorMock;

import org.junit.Before;
import org.junit.Test;

public class ParticleEmitterTest {
	protected ParticleEmitter emitter;
	protected ParticleBehaviorMock behavior;
	
	@Before
	public void intializeEmitter() {
		emitter = new ParticleEmitterMock();
		behavior = new ParticleBehaviorMock(new DefaultParticleBehavior());
	}
	
	
	@Test
	public void particlesAreCorrectlyCreated() {
		assertEquals(0, emitter.aliveParticles.size());
		emitter.emit(Color.WHITE, new Rectangle(), 42, behavior);
		assertEquals(42, emitter.aliveParticles.size());
	}
	
	@Test
	public void particlesAreCorrectlyRemoved() {
		assertEquals(0, emitter.aliveParticles.size());
		assertEquals(0, emitter.deadParticles.size());
		emitter.emit(Color.WHITE, new Rectangle(), 42, behavior);
		// When a particle is in deadParticles, it should be removed after a call to removeDeadParticles
		assertEquals(42, emitter.aliveParticles.size());
		emitter.deadParticles.add(emitter.aliveParticles.get(0));
		emitter.removeDeadParticles();
		assertEquals(41, emitter.aliveParticles.size());
		assertEquals(0, emitter.deadParticles.size());
	}
	
	@Test
	public void particlesAreCorrectlyUpdated() {
		assertEquals(0, behavior.getNbUpdate());
		emitter.emit(Color.WHITE, new Rectangle(), 42, behavior);
		// We simulate a draw that should updates our particles
		emitter.draw(null);
		assertEquals(42, behavior.getNbUpdate());
	}
}
