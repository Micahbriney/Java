import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends ResourceEntity {


    public MinerNotFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, images, 0, position, actionPeriod, animationPeriod, resourceLimit, resourceCount);
    }

    @Override
    public void     executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> notFullTarget =
                world.findNearest(getPosition(), Ore.class);

        if (!notFullTarget.isPresent() || !move(
                world,
                notFullTarget.get(),
                scheduler)
                || !transformNotFull(
                        world,
                        scheduler,
                        imageStore))
        {
            scheduler.scheduleEvent(
                    this,
                    Factory.createActivityAction(
                            this,
                            world,
                            imageStore),
                    getActionPeriod());
        }
    }

    protected Point nextPositionHelper(WorldModel world, Point destPos){
        return nextPosition(world, destPos);
    }

    protected void moveHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler){
        setResourceCount(getResourceCount() + 1);
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
        return;
    }

    private boolean transformNotFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (getResourceCount() >= getResourceLimit()) {
            MinerFull miner = world.createMinerFull(
                    getId(),
                    getResourceLimit(),
                    getPosition(),
                    getActionPeriod(),
                    getAnimationPeriod(),
                    getImages());

            super.transform(
                    world,
                    scheduler,
                    imageStore,
                    miner);

            return true;
        }

        return false;
    }
}
