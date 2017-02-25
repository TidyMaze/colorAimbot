import java.lang.System;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.util.Collections;

class Aimbot {
  public static void main(String[] args) throws IOException  {

    String path = args[0];
    System.out.println(path);

    BufferedImage img = ImageIO.read(new File(path));

    int width = img.getWidth();
    int height = img.getHeight();


    //double CEIL = 0.05;
    double CEIL = 0.1;

    for (int i=0; i<height; i++) {
      for (int j=0; j<width; j++) {
        Color current = new Color(img.getRGB(j,i));
        float[] hsb = Color.RGBtoHSB(current.getRed(),current.getGreen(), current.getBlue(), null);

        int target = 80;
        double TARGET_MAX = 100;
        float key = hsb[1];
        double diffPct = Collections.min(Arrays.asList(
          Math.abs((key*TARGET_MAX-TARGET_MAX)-target),
          Math.abs((key*TARGET_MAX)-target),
          Math.abs((key*TARGET_MAX+TARGET_MAX)-target)
        ))/TARGET_MAX;

        int target2 = 18;
        double TARGET_MAX2 = 360;
        float key2 = hsb[0];
        double diffPct2 = Collections.min(Arrays.asList(
          Math.abs((key2*TARGET_MAX2-TARGET_MAX2)-target2),
          Math.abs((key2*TARGET_MAX2)-target2),
          Math.abs((key2*TARGET_MAX2+TARGET_MAX2)-target2)
        ))/TARGET_MAX2;

        double avDiffPct = (diffPct + diffPct2) / 2;

        int diff255 = (int)Math.round((avDiffPct > CEIL ? 1 : avDiffPct) * 255);
        Color resColor = new Color(diff255, diff255, diff255);
        img.setRGB(j,i,resColor.getRGB());
      }
    }

    File outputfile = new File("output.png");
    ImageIO.write(img, "png", outputfile);
  }
}
