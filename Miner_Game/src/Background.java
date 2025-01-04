import java.util.List;

import processing.core.PImage;

public final class Background extends Entity
{
    public Background(
            String id,
            List<PImage> images,
            Point pt)
    {
        super(id, images,0, pt);
    }

}
