package org.usd232.robotics.powerup;

import edu.wpi.first.wpilibj.Encoder;

/**
 * An oversampled encoder
 * 
 * @author Zach Deibert
 * @since 2017
 * @version 2018
 */
public class OversampledEncoder extends Encoder {
    @Override
    public double getDistance() {
        return super.getDistance();
    }

    /**
     * Creates a new Oversampled encoder
     * 
     * @param channelA
     *            The first channel for the encoder
     * @param channelB
     *            The second channel for the encoder
     */
    public OversampledEncoder(int channelA, int channelB) {
        super(channelA, channelB, false, EncodingType.k4X);
        setSamplesToAverage(127);
    }
}
