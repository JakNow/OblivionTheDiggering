package pl.oblivion.effects.particles;

import pl.oblivion.scene.GameObject;

public abstract class Particle extends GameObject {

    //TODO Particle System

    protected Particle(String name) {
        super(name, Particle.class);
    }

}
