import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


class Aimbot {

  private List<ColorConfig> colorConfigs = Arrays.asList(
          new ColorConfig(18, 360, 0, 80, 100, 1),
          new ColorConfig(220, 360, 0, 83, 100, 1)
  );

  private BufferedImage takeScreenshot() throws AWTException {
    return new Robot().createScreenCapture(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
  }

  private Aimbot() throws IOException, AWTException {

    BufferedImage img = takeScreenshot();
    ImageIO.write(img, "png", new File("gen/screenshot.png"));

    List<BufferedImage> colorLayers = new ArrayList<>();

    for (int idColorConfig = 0; idColorConfig < colorConfigs.size(); idColorConfig++) {

      ColorModel cm = img.getColorModel();
      WritableRaster raster = img.copyData(null);

      BufferedImage copy = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
      colorLayers.add(copy);

      ColorConfig currentColorConfig = colorConfigs.get(idColorConfig);

      int width = img.getWidth();
      int height = img.getHeight();

      double CEIL = 0.1;

      for (int i=0; i<height; i++) {
        for (int j=0; j<width; j++) {
          Color current = new Color(img.getRGB(j,i));
          float[] hsb = Color.RGBtoHSB(current.getRed(),current.getGreen(), current.getBlue(), null);

          List<Double> allPcts = Arrays.asList(
                  getDiffPct(currentColorConfig.targetSaturation, currentColorConfig.targetSaturationMax, hsb[currentColorConfig.saturationIndex]),
                  getDiffPct(currentColorConfig.targetHue, currentColorConfig.targetHueMax, hsb[currentColorConfig.hueIndex])
          );
          double avDiffPct = calculateAverage(allPcts);

          int diff255 = (int)Math.round((avDiffPct > CEIL ? 1 : 0) * 255);
          int alpha = (int)Math.round((avDiffPct > CEIL ? 0 : 1) * 255);
          Color resColor = new Color(diff255, diff255, diff255, alpha);
          colorLayers.get(idColorConfig).setRGB(j,i,resColor.getRGB());
        }
      }

      ImageIO.write(colorLayers.get(idColorConfig), "png", new File("gen/output"+idColorConfig+".png"));
    }

    BufferedImage colorLayer = colorLayers.get(0);

    ColorPanel cp = new ColorPanel();
    cp.setImage(colorLayer);

    JFrame frame = new JFrame("Glass aim");
    frame.setLocationByPlatform(false);
    frame.setUndecorated(true);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setBackground(new Color(0, 0, 0, 0));
    frame.add(cp);
    frame.setAlwaysOnTop(true);
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) throws IOException, AWTException {
    new Aimbot();
  }

  private static double getDiffPct(int target, double targetMax, float comp) {
    return Collections.min(Arrays.asList(
          Math.abs((comp * targetMax - targetMax)-target),
          Math.abs((comp * targetMax)-target),
          Math.abs((comp * targetMax + targetMax)-target)
        ))/ targetMax;
  }

  private double calculateAverage(List<Double> values) {
    double sum = 0;
    if(!values.isEmpty()) {
      for (double v : values) {
        sum += v;
      }
      return sum / values.size();
    }
    return sum;
  }
}
