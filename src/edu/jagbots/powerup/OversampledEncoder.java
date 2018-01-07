package edu.jagbots.powerup;

import edu.wpi.first.wpilibj.Encoder;

public class OversampledEncoder extends Encoder {
    @Override
    public double getDistance() {
	return super.getDistance();
    }
    public OversampledEncoder(int channelA, int channelB) {
	super(channelA, channelB, false, EncodingType.k4X);
	setSamplesToAverage(127);
    }
}
