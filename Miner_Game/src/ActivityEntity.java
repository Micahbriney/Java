import processing.core.PImage;

import java.util.List;

public abstract class ActivityEntity extends Entity{

    private int actionPeriod;

    public ActivityEntity(
            String id,
            List<PImage> images,
            int imageIndex,
            Point position,
            int actionPeriod) {
        super(id, images, imageIndex, position);
        this.actionPeriod = actionPeriod;
    }

    protected abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    protected int           getActionPeriod(){
        return actionPeriod;
    }

    protected void          scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                getActionPeriod());
}
}