import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class OreBlob extends ResourceEntity {

    private final String QUAKE_KEY = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
    private static boolean attack = false;
    private Class target;

    public OreBlob(
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
        // OreBlobs will target the Attackers when triggered
        setTarget();

        Optional<Entity> blobTarget =
                world.findNearest(getPosition(), target);
        long nextPeriod = getActionPeriod();

        if (blobTarget.isPresent()) {
            Point tgtPos = blobTarget.get().getPosition();

            if (move(world, blobTarget.get(), scheduler)) {
                Death quake = world.createDeath(tgtPos,
                        imageStore.getImageList(QUAKE_KEY),
                        QUAKE_ACTION_PERIOD,
                        QUAKE_ANIMATION_PERIOD,
                        QUAKE_ANIMATION_REPEAT_COUNT);

                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
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
                      world.getOccupant(p).get().getClass() == Ore.class),
                (p1, p2) -> p1.adjacent(p2),
                PathingStrategy.CARDINAL_NEIGHBORS);

        setResourceCount(getDestPosTryCount() + 1);
        if (path.size() == 0){
            return getPosition();
        }
        return path.get(0);

    }

    public boolean getAttack(){
        return attack;
    }

    private void setTarget(){
        if (attack){
            target = Attacker.class;
        }
        else{
            target = Vein.class;
        }
    }

    public void setAttack(boolean attack){
        this.attack = attack;
    }

}
