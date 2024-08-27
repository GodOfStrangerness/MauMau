import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.HashMap;

public class ImportFilesWithMap {
    public HashMap<String, BufferedImage> imagesMap = new HashMap<>();

    public void importFilesIntoMap() {
        File path = new File("playing_cards");
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    if (image != null) {
                        // Stellen Sie sicher, dass die Dateinamen korrekt verarbeitet werden
                        String fileName = file.getName().replace(".png", "").replace("_of_", "_").toLowerCase();
                        imagesMap.put(fileName, image);
                        System.out.println("Loaded image: " + fileName);
                    } else {
                        System.out.println("Failed to load image: " + file.getName());
                    }
                } catch (IOException ie) {
                    System.out.println("Error:" + ie.getMessage());
                }
            }
        } else {
            System.out.println("No files found in directory: " + path.getAbsolutePath());
        }
    }

    public BufferedImage getImage(String fileName) {
        return imagesMap.get(fileName);
    }

    public void clearImage(String fileName) {
        imagesMap.remove(fileName);
        System.out.println("Cleared image: " + fileName);
    }
}
