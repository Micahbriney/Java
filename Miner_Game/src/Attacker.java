import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Attacker extends ResourceEntity{

    private final int DEATH_ANIMATION_REPEAT_COUNT = 15;
    public static String DEATH_KEY;
    private final int DEATH_ANIMATION_PERIOD = 200;
    private final int DEATH_ACTION_PERIOD = 4500;

    public Attacker(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, images, 0, position, actionPeriod, animationPeriod, 0, 0);
    }


    @Override
    public void     executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        Optional<Entity> attackTarget =
                world.findNearest(getPosition(), OreBlob.class);
        long nextPeriod = getActionPeriod();

        if (attackTarget.isPresent()) {
            Point tgtPos = attackTarget.get().getPosition();

            if (move(world, attackTarget.get(), scheduler)) {
                Death oreBlobDeath = world.createDeath(tgtPos,
                        imageStore.getImageList(DEATH_KEY),
                        DEATH_ACTION_PERIOD,
                        DEATH_ANIMATION_PERIOD,
                        DEATH_ANIMATION_REPEAT_COUNT);

                world.addEntity(oreBlobDeath);
                nextPeriod += getActionPeriod();
                oreBlobDeath.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    protected void moveHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler){
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
        return;
    }

    protected Point nextPositionHelper(WorldModel world, Point destPos){
        return nextPosition(world, destPos);
    }


    protected Point   nextPosition(WorldModel world, Point destPos) {

        List<Point> path;

        // Ol' College Try. Currently tries for three iterations
        if (getDestPosTryCount() == 0){
            setDestPosTry(destPos);
            setResourceCount(getDestPosTryCount() + 1);
        }
        else if (getDestPosTryCount() > 3){      // Increase number of attempts to reach the same destination
            setDestPosTry(destPos);
            setResourceCount(0);
        }

        path = getStrategy().computePath(
                getPosition(),
                getDestPosTry(),
                p -> world.withinBounds(p) &&
                        (!world.getOccupant(p).isPresent() ||
                                world.getOccupant(p).get().getClass() == OreBlob.class),
                (p1, p2) -> p1.adjacent(p2),
                PathingStrategy.CARDINAL_NEIGHBORS);

        setResourceCount(getDestPosTryCount() + 1);
        if (path.size() == 0){
            return getPosition();
        }
        return path.get(0);

    }

}

