package Tools;

import java.io.Serializable;

import GameFeatures.BombIncreaserPower;
import GameFeatures.BombPower;
import GameFeatures.ButtonSubstitude;
import GameFeatures.Features;
import GameFeatures.HealthPower;
import GameFeatures.LoseLastPower;
import GameFeatures.SpeedPower;
import GameFeatures.TransparentPower;
import GameFeatures.loseBombPower;
import GameObjects.Map;

public class Probibility implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2716254223522087633L;

	public static Features canDo(double p, Map map) {
		if (p > 0 && p < 0.1)
			return new BombPower(map);
		else if (p >= 0.1 && p < 0.2)
			return new BombIncreaserPower(map);
		else if (p >= 0.2 && p < 0.3)
			return new HealthPower(map);
		else if (p >= 0.3 && p < 0.47)
			return new SpeedPower(map);
		else if (p >= 0.47 && p < 0.64)
			return new TransparentPower(map);
		else if (p >= 0.64 && p < 0.76)
			return new ButtonSubstitude(map);
		else if (p >= 0.76 && p < 0.88)
			return new loseBombPower(map);
		else if (p >= 0.88 && p < 1)
			return new LoseLastPower(map);

		return null;

	}
}
