import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerFull extends ResourceEntity {

    public MinerFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, images, 0, position, actionPeriod, animationPeriod, resourceLimit, 0);
    }

    @Override
    public void     executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> fullTarget =
                world.findNearest(getPosition(), Blacksmith.class);

        if (fullTarget.isPresent() && move(
                world,
                fullTarget.get(),
                scheduler))
        {
            transformFull(
                    world,
                    scheduler,
                    imageStore);
        }
        else {
            scheduler.scheduleEvent(
                    this,
                    Factory.createActivityAction(
                            this,
                            world,
                            imageStore),
                    getActionPeriod());
        }
    }

    protected void moveHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler){
        return;
    }

    protected Point nextPositionHelper(WorldModel world, Point destPos){
        return nextPosition(world, destPos);
    }

    private void    transformFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        MinerNotFull miner = world.createMinerNotFull(
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

    }
}
