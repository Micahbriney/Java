import processing.core.PImage;

import java.util.List;

public abstract class Entity {

    private String id;
    private List<PImage> images;
    private int imageIndex;
    private Point position;

    public Entity(
            String id,
            List<PImage> images,
            int imageIndex,
            Point position){
        this.id = id;
        this.images = images;
        this.imageIndex = imageIndex;
        this.position = position;
    }

    protected PImage       getCurrentImage(){
        return images.get(imageIndex);
    }

    protected String       getId() {
        return id;
    }

    protected List<PImage> getImages(){
        return images;
    }

    protected int          getImageIndex() {
        return imageIndex;
    }

    protected Point        getPosition(){
        return position;
    }

    protected void         setImages(List<PImage> images){
        this.images = images;
    }

    protected void         setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    protected void         setPosition(Point position) {
        this.position = position;
    }
}
