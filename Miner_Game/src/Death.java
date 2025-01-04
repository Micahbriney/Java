import processing.core.PImage;

import java.util.List;

public class Death extends AnimationEntity{


    private int repeatCount;

    public Death(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod,
            int repeatCount)
    {
        super(id, images, 0, position, actionPeriod, animationPeriod);
        this.repeatCount = repeatCount;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                getActionPeriod());
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this,
                repeatCount),
                getAnimationPeriod());
    }

}
