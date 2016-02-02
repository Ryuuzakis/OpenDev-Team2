package gameframework.particles;

import java.awt.Graphics;

public class ParticleEmitterMock extends ParticleEmitter {

	/**
	 * @see gameframework.particles.ParticleEmitter#drawParticle(gameframework.particles.Particle, java.awt.Graphics)
	 */
	@Override
	public void drawParticle(Particle p, Graphics g) {
		// Draw should do nothing as it's not easily testable
	}
}
