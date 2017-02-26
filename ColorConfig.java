/**
 * Created by yann on 26/02/17.
 */
public class ColorConfig {
    public int targetHue;
    public int targetHueMax;
    public int hueIndex;
    public int targetSaturation;
    public int targetSaturationMax;
    public int saturationIndex;

    public ColorConfig(int targetHue, int targetHueMax, int hueIndex, int targetSaturation, int targetSaturationMax, int saturationIndex) {
        this.targetHue = targetHue;
        this.targetHueMax = targetHueMax;
        this.hueIndex = hueIndex;
        this.targetSaturation = targetSaturation;
        this.targetSaturationMax = targetSaturationMax;
        this.saturationIndex = saturationIndex;
    }
}
