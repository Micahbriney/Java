import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import processing.core.PImage;

public final class ImageStore
{
    private List<PImage> defaultImages;
    private Map<String, List<PImage>> images;

    public ImageStore(PImage defaultImage) {
        this.images   = new HashMap<>();
        defaultImages = new LinkedList<>();
        defaultImages.add(defaultImage);
    }

    public Map<String, List<PImage>> getImages() {
        return images;
    }

    public List<PImage>              getImageList(String key) {
        return images.getOrDefault(key, defaultImages);
    }
}
