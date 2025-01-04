import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class ResourceEntity extends AnimationEntity{

    private int resourceLimit;
    private int resourceCount;

    // Attempt to go to destination and prevent ping pong entities
    private Point destPosTry;
    private int destPosTryCount = 0;

//    private PathingStrategy strategy = new SingleStepPathingStrategy();
    private PathingStrategy strategy = new AStarPathingStrategy();

    public ResourceEntity(
            String id,
            List<PImage> images,
            int imageIndex,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            int resourceCount){
        super(id, images, imageIndex, position, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    protected void setDestPosTry(Point destPosTry) {
        this.destPosTry = destPosTry;
    }

    protected Point getDestPosTry(){
        return destPosTry;
    }

    protected int getDestPosTryCount() {
        return destPosTryCount;
    }

    protected void setDestPosTryCount(int destPosTryCount){
        this.destPosTryCount = destPosTryCount;
    }

    protected int     getResourceLimit() {
        return resourceLimit;
    }

    protected int     getResourceCount(){
        return resourceCount;
    }

    protected PathingStrategy getStrategy() { return strategy; }

    protected abstract void moveHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler);

    protected void    setResourceCount(int resourceCount){
        this.resourceCount = resourceCount;
    }

    protected boolean move(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        if (getPosition().adjacent(target.getPosition())) {
            moveHelper(world,
                    target,
                    scheduler);
            return true;
        }

        Point nextPos = nextPositionHelper(world, target.getPosition());

        if (!getPosition().equals(nextPos)) {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent()) {
                scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
        }

        return false;
    }

    protected abstract Point nextPositionHelper(WorldModel world, Point destPos);

    protected Point     nextPosition(WorldModel world, Point destPos) {

        List<Point> path;

        // Ol' College Try. Currently tries for three iterations
        // Try to go to desitination three times before allowing a new destination to be set
        if (destPosTryCount == 0){
            destPosTry = destPos;
            destPosTryCount++;
        }
        else if (destPosTryCount > 3){      // Increase number of attempts to reach the same destination
            destPosTry = destPos;
            destPosTryCount = 0;
        }

        path = strategy.computePath(
                getPosition(),
                destPosTry,
                p -> world.withinBounds(p) && !world.isOccupied(p),
                (p1, p2) -> p1.adjacent(p2),
                PathingStrategy.CARDINAL_NEIGHBORS);

        destPosTryCount++;
        if (path.size() == 0){
            return getPosition();
        }
        return path.get(0);

//        int horiz = Integer.signum(destPos.getX() - getPosition().getX());
//        Point newPos = new Point(getPosition().getX() + horiz, getPosition().getY());
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.getY() - getPosition().getY());
//            newPos = new Point(getPosition().getX(), getPosition().getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = getPosition();
//            }
//        }
//
//        return newPos;
    }

    protected void    transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore,
            ActivityEntity miner){

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
        return;
    }

}
