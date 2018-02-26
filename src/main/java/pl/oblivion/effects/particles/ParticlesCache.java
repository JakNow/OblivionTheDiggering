package pl.oblivion.effects.particles;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ParticlesCache {
    private static final Logger logger = Logger.getLogger(ParticlesCache.class.getName());

    private static ParticlesCache INSTANCE;

    private Map<String, Particle> particleMap;

    private ParticlesCache() {
        particleMap = new HashMap<>();
    }

    public static ParticlesCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ParticlesCache();
            logger.info("Creating a new instance of " + ParticlesCache.class.getName());
        }
        return INSTANCE;
    }

    public Particle getParticle(String name) {
        Particle particle = particleMap.get(name);
        if (particle == null) {
            //TODO fix later
            particle = new Particle(name);
            particleMap.put(name, particle);
        }
        return particle;
    }


}
