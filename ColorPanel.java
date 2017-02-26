import javax.swing.*;
import java.awt.*;

/**
 * Created by yann on 26/02/17.
 */
public class ColorPanel extends JPanel {

    ColorPanel(){
        super();
        this.setBackground(new Color(0, 0, 0, 0));
    }

    private Image image;


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, null);
    }
}
