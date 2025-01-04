import processing.core.PImage;

import java.util.*;

public final class WorldModel {
    private Background background[][];
    private Set<Entity> entities;

    private static final String QUAKE_ID = "quake";
    private static final String DEATH_ID = "death";
    private int numCols;
    private int numRows;
    private Entity occupancy[][];
    private static final int ORE_REACH = 1;

    public WorldModel(
            int numRows,
            int numCols,
            Background defaultBackground)
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }

    public void addEntity(Entity entity) {
        if (withinBounds(entity.getPosition())) {
            setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
        }
    }

    public Attacker createAttacker(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Attacker(
                id,
                position,
                images,
                actionPeriod,
                animationPeriod);
    }

    public MinerNotFull createMinerNotFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new MinerNotFull(
                id,
                position,
                images,
                resourceLimit,
                0,
                actionPeriod,
                animationPeriod);
    }

    public Blacksmith createBlacksmith(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        return new Blacksmith(
                id,
                position,
                images,
                actionPeriod,
                animationPeriod);
    }

    public Death createDeath(
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod,
            int repeatCount)
    {
        return new Death(
                DEATH_ID,
                position,
                images,
                actionPeriod,
                animationPeriod,
                repeatCount);
    }

    public MinerFull createMinerFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new MinerFull(
                id,
                position,
                images,
                resourceLimit,
                actionPeriod,
                animationPeriod);
    }



    public Obstacle createObstacle(
            String id,
            Point position,
            List<PImage> images)
    {
        return new Obstacle(
                id,
                position,
                images);
    }

    public Ore createOre(
            String id,
            Point position,
            int actionPeriod,
            List<PImage> images)
    {
        return new Ore(
                id,
                position,
                images,
                actionPeriod);
    }

    public OreBlob createOreBlob(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new OreBlob(
                id,
                position,
                images,
                actionPeriod,
                animationPeriod);
    }

    public static Vein createVein(
            String id,
            Point position,
            int actionPeriod,
            List<PImage> images)
    {
        return new Vein(
                id,
                position,
                images,
                actionPeriod);
    }

    public Optional<Point> findOpenAround(Point pos) {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
                Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
                if (withinBounds(newPt) && !isOccupied(newPt)) {
                    return Optional.of(newPt);
                }
            }
        }

        return Optional.empty();
    }

    public Optional<Entity> findNearest(Point pos, Class kind) {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : entities) {
            if (entity.getClass().equals(kind)) {
                ofType.add(entity);
            }
        }

        return nearestEntity(ofType, pos);
    }

    public Background getBackgroundCell(Point pos) {
        return background[pos.getY()][pos.getX()];
    }

    public Optional<PImage> getBackgroundImage(Point pos) {
        if (withinBounds(pos)) {
            return Optional.of(background[pos.getY()][pos.getX()].getCurrentImage());
        } else {
            return Optional.empty();
        }
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }

    public boolean isOccupied(Point pos) {
        return withinBounds(pos) && getOccupancyCell(pos) != null;
    }

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && !pos.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            removeEntityAt(pos);
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public void removeEntity(Entity entity) {
        removeEntityAt(entity.getPosition());
    }

    public void removeEntityAt(Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            Entity entity = getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }

    public void setBackground(Point pos, Background background) {
        if (withinBounds(pos)) {
            setBackgroundCell(pos, background);
        }
    }

    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.getY()][pos.getX()] = background;
    }

    public void setOccupancyCell(Point pos, Entity entity) {
        occupancy[pos.getY()][pos.getX()] = entity;
    }

    public void tryAddEntity(Entity entity) {
        if (isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }

    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getY() < numRows && pos.getX() >= 0
                && pos.getX() < numCols;
    }

    public int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.getX() - p2.getX();
        int deltaY = p1.getY() - p2.getY();

        return deltaX * deltaX + deltaY * deltaY;
    }

    public Entity getOccupancyCell(Point pos) {
        return occupancy[pos.getY()][pos.getX()];
    }
}
