package Functions;

import javax.swing.*;
import java.awt.*;

public class AssetLoader {
    public static Image load(String path) {
        return new ImageIcon(AssetLoader.class.getResource(path)).getImage();
    }
}
