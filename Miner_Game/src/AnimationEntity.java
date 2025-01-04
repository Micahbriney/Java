import processing.core.PImage;

import java.util.List;

public abstract class AnimationEntity extends ActivityEntity {

    private int animationPeriod;

    public AnimationEntity(
            String id,
            List<PImage> images,
            int imageIndex,
            Point position,
            int actionPeriod,
            int animationPeriod){
        super(id, images, imageIndex, position, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    protected int  getAnimationPeriod(){
        return animationPeriod;
    }

    protected void nextImage() {
        super.setImageIndex((getImageIndex() + 1) % getImages().size());
    }

    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                getActionPeriod());
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this, 0),
                animationPeriod);
    }

}
