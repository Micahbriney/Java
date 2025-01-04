import processing.core.PImage;

import java.util.List;

public class Blacksmith extends AnimationEntity{

    public Blacksmith(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, images, 0, position, actionPeriod, animationPeriod);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Point pos = getPosition();
//
//        world.removeEntity(this);
//        scheduler.unscheduleAllEvents(this);
//
//        OreBlob blob = world.createOreBlob(
//                getId() + BLOB_ID_SUFFIX,
//                pos,
//                getActionPeriod() / BLOB_PERIOD_SCALE,
//                BLOB_ANIMATION_MIN + rand.nextInt(
//                        BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
//                imageStore.getImageList(BLOB_KEY));
//
//        world.addEntity(blob);
//        blob.scheduleActions(scheduler, world, imageStore);
    }
}
