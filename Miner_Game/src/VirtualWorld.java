import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import processing.core.*;

public final class VirtualWorld extends PApplet
{

    private static final int COLOR_MASK = 0xffffff;
    private static final int KEYED_IMAGE_MIN = 5;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;

    private static final int PROPERTY_KEY = 0;

    private static final String BGND_KEY = "background";
    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_ID = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;

    private static final String BROKEN_BOTTLE_DIRT_BGND_KEY = "dirtBottle";
    private static final String BROKEN_BOTTLE_GRASS_BGND_KEY = "grassBottle";
    private static final int BROKEN_BOTTLE__NUM_PROPERTIES = 4;
    private static final int BROKEN_BOTTLE__ID = 1;
    private static final int BROKEN_BOTTLE__COL = 2;
    private static final int BROKEN_BOTTLE__ROW = 3;

    private static final String ATTACKER_KEY = "rick";
    private static final int ATTACKER_NUM_PROPERTIES = 7;
    private static final int ATTACKER_ID = 1;
    private static final int ATTACKER_COL = 2;
    private static final int ATTACKER_ROW = 3;
    private static final int ATTACKER_LIMIT = 4;
    private static final int ATTACKER_ACTION_PERIOD = 5;
    private static final int ATTACKER_ANIMATION_PERIOD = 6;
    private static final int ATTACKER_ANIMATION_MAX = 150;
    private static final int ATTACKER_ANIMATION_MIN = 50;
    private static final int ATTACKER_ACTION_MAX = 3020;    // Larger difference between MIN & MAX = slower Attacker Movement
    private static final int ATTACKER_ACTION_MIN = 1000;

    private static final String MINER_KEY = "morty";
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final int MINER_ID = 1;
    private static final int MINER_COL = 2;
    private static final int MINER_ROW = 3;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_ACTION_PERIOD = 5;
    private static final int MINER_ANIMATION_PERIOD = 6;

    private static final String OBSTACLE_KEY = "obstacle";
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;

    public static final String ORE_KEY = "ore";
    private static final int ORE_NUM_PROPERTIES = 5;
    private static final int ORE_ID = 1;
    private static final int ORE_COL = 2;
    private static final int ORE_ROW = 3;
    private static final int ORE_ACTION_PERIOD = 4;

    private static final String SMITH_KEY = "portal";
    private static final int SMITH_NUM_PROPERTIES = 4;
    private static final int SMITH_ID = 1;
    private static final int SMITH_COL = 2;
    private static final int SMITH_ROW = 3;
    private static final int SMITH_ACTION_PERIOD = 4;
    private static final int SMITH_ANIMATION_PERIOD = 5;

    private static final String RED_OREBLOB_KEY = "blobr";
    private static final String GREEN_DEATH_KEY = "death";
    private static final String RED_DEATH_KEY = "deathr";

    private static final String VEIN_KEY = "isotope322";
    private static final int VEIN_NUM_PROPERTIES = 5;
    private static final int VEIN_ID = 1;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ROW = 3;
    private static final int VEIN_ACTION_PERIOD = 4;

    private static final int TIMER_ACTION_PERIOD = 100;

    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final int WORLD_WIDTH_SCALE = 2;
    private static final int WORLD_HEIGHT_SCALE = 2;

    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;

    private static final String LOAD_FILE_NAME = "world.sav";

    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    private static double timeScale = 1.0;

    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;

    private long nextTime;

    private static final Random rand = new Random();

    public void mousePressed()
    {
        Point pressed = mouseToPoint(mouseX, mouseY);
        List<Point> translatedSpawnLocations = new ArrayList<Point>();
        List<Point> spawnLocations;

        // Spawn Locations
        spawnLocations = getSpawnLocations();

        //translate the spawn points to viewport
        translatedSpawnLocations = spawnLocations
                .stream()
                .map(pressed::translate)
                .filter(p -> world.withinBounds(p))
                .collect(Collectors.toList());

        worldEvent(translatedSpawnLocations);

        redraw();

    }

    private Point mouseToPoint(int x, int y)
    {
        return view.getViewport().viewportToWorld(mouseX/TILE_WIDTH, mouseY/TILE_HEIGHT);
    }

    private List<Point> getSpawnLocations(){
        List<Point> spawnLocations = new ArrayList<Point>();

        // Square pattern of locations
        for(int i = -1; i < 2; i++){
            for (int g = -1; g < 2; g++){
                spawnLocations.add(new Point(i * 2,g * 2));
            }
        }
        // Remove middle clicked location for visual effect
        spawnLocations.remove(new Point(0, 0));
        return spawnLocations;
    }

    private void worldEvent(List<Point> translatedSpawnLocations){
        for (Point spawn : translatedSpawnLocations) {
            Optional<Entity> attackerTarget = world.findNearest(spawn, OreBlob.class);

            // Only allow World Event to occur when OreBlob is present
            if (attackerTarget.isPresent()){
                if (!world.isOccupied(spawn)){
                    backgroundEvent(spawn);
                    entityEvent(spawn);
                }
            }
        }
    }

    private void backgroundEvent(Point spawn){
        List<Point> eventBackgroundPoints;

        eventBackgroundPoints = PathingStrategy.CARDINAL_NEIGHBORS
                .apply(spawn)
                .filter(p -> world.withinBounds(p) &&
                        !world.getBackgroundImage(p).equals(imageStore.getImageList(OBSTACLE_KEY).get(0)))
                .collect(Collectors.toList());

        // Background Tile Modification
        for (Point eventBackgroundPoint : eventBackgroundPoints) {

            Background test = world.getBackgroundCell(eventBackgroundPoint);

            if (world.getBackgroundCell(eventBackgroundPoint).getId().equals("dirt"))
            {
                world.setBackground(
                        eventBackgroundPoint,
                        new Background(BROKEN_BOTTLE_DIRT_BGND_KEY +
                                "_" + eventBackgroundPoint.getX() +
                                "_" + eventBackgroundPoint.getY(),
                                imageStore.getImageList(BROKEN_BOTTLE_DIRT_BGND_KEY),
                                eventBackgroundPoint));
            }
            else {
                world.setBackground(
                        eventBackgroundPoint,
                        new Background(BROKEN_BOTTLE_GRASS_BGND_KEY +
                                "_" + eventBackgroundPoint.getX() +
                                "_" + eventBackgroundPoint.getY(),
                                imageStore.getImageList(BROKEN_BOTTLE_GRASS_BGND_KEY),
                                eventBackgroundPoint));
            }
        }
    }

    private void entityEvent(Point spawn){

        // Create Static Entites
        Blacksmith eventPortal = world.createBlacksmith(SMITH_KEY + "_" + spawn.getX() + "_" + spawn.getY(),
                spawn,
                imageStore.getImageList(SMITH_KEY),
                0,
                0);

        // Create Mobile Entites
        Attacker eventAttacker = world.createAttacker(ATTACKER_KEY + "_" + spawn.getX() + "_" + spawn.getY(),
                spawn,
      ATTACKER_ACTION_MIN + rand.nextInt(
          ATTACKER_ACTION_MAX - ATTACKER_ANIMATION_MIN),
   ATTACKER_ANIMATION_MIN + rand.nextInt(
          ATTACKER_ANIMATION_MAX - ATTACKER_ANIMATION_MIN),
                imageStore.getImageList(ATTACKER_KEY)
        );

        changeEntityBehavior(spawn);
        changeEntityAppearance();
        // Add Entity
        world.addEntity(eventPortal);
        eventPortal.scheduleActions(scheduler, world, imageStore);
        world.addEntity(eventAttacker);
        eventAttacker.scheduleActions(scheduler, world, imageStore);

    }

    // Make OreBlobs attack Ricks
    public void changeEntityBehavior(Point spawn){
        Optional<Entity> attackTarget =
                world.findNearest(spawn, OreBlob.class);

        attackTarget.ifPresent(entity -> ((OreBlob) entity).setAttack(true));
    }

    public void changeEntityAppearance(){

        Attacker.DEATH_KEY = RED_DEATH_KEY;

        List<Entity> oldBlobs = world.getEntities()
                .stream()
                .filter(p -> p.getClass().equals(OreBlob.class))
                .collect(Collectors.toList());

        List<Entity> attackBlobs = oldBlobs
                .stream()
                .map(p -> new OreBlob(
                        p.getId(),
                        p.getPosition(),
                        imageStore.getImageList(RED_OREBLOB_KEY),
                        ((OreBlob) p).getActionPeriod(),
                        ((OreBlob) p).getAnimationPeriod()))
                .collect(Collectors.toList());

        for (Entity blob : oldBlobs) {
            world.removeEntity(blob);
            scheduler.unscheduleAllEvents(blob);
//            ((OreBlob)blob).moveHelper(world, blob, scheduler);
        }

        for (Entity blob : attackBlobs) {
            world.addEntity(blob);
            ((OreBlob)blob).scheduleActions(scheduler, world, imageStore);
        }

    }


    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        this.imageStore = new ImageStore(
                createImageColored(TILE_WIDTH, TILE_HEIGHT,
                                   DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
                                    createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH,
                                  TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        nextTime = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw() {
        long time = System.currentTimeMillis();
        if (time >= nextTime) {
            scheduler.updateOnTime(time);
            nextTime = time + TIMER_ACTION_PERIOD;
        }

        view.drawViewport();
    }

    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }
            view.shiftView(dx, dy);
        }
    }

    private Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME,
                              imageStore.getImageList(DEFAULT_IMAGE_NAME),
                new Point(BGND_COL, BGND_ROW)
                );
    }

    private PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    private void loadImages(
            String filename, ImageStore imageStore, PApplet screen)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            loadImages(in, imageStore, screen);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private void loadWorld(
            WorldModel world, String filename, ImageStore imageStore)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            load(in, world, imageStore);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private void scheduleActions(
            WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        for (Entity entity : world.getEntities()) {
            if(entity instanceof ActivityEntity)
                ((ActivityEntity)entity).scheduleActions(scheduler, world, imageStore);
        }
    }

    private static void parseCommandLine(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }


    private void loadImages(
            Scanner in, ImageStore imageStore, PApplet screen)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                processImageLine(imageStore.getImages(), in.nextLine(), screen);
            }
            catch (NumberFormatException e) {
                System.out.println(
                        String.format("Image format error on line %d",
                                lineNumber));
            }
            lineNumber++;
        }
    }

    private void processImageLine(
            Map<String, List<PImage>> images, String line, PApplet screen)
    {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = getImages(images, key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN) {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }

    private List<PImage> getImages(
            Map<String, List<PImage>> images, String key)
    {
        List<PImage> imgs = images.get(key);
        if (imgs == null) {
            imgs = new LinkedList<>();
            images.put(key, imgs);
        }
        return imgs;
    }

    /*
      Called with color for which alpha should be set and alpha value.
      setAlpha(img, color(255, 255, 255), 0));
    */
    private void setAlpha(PImage img, int maskColor, int alpha) {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }

    private void load(
            Scanner in, WorldModel world, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!processLine(in.nextLine(), world, imageStore)) {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e) {
                System.err.println(
                        String.format("invalid entry on line %d", lineNumber));
            }
            catch (IllegalArgumentException e) {
                System.err.println(
                        String.format("issue on line %d: %s", lineNumber,
                                e.getMessage()));
            }
            lineNumber++;
        }
    }

    private boolean processLine(
            String line, WorldModel world, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[PROPERTY_KEY]) {
                case BGND_KEY:
                    return parseBackground(properties, world, imageStore);
                case MINER_KEY:
                    return parseMiner(properties, world, imageStore);
                case OBSTACLE_KEY:
                    return parseObstacle(properties, world, imageStore);
                case ORE_KEY:
                    return parseOre(properties, world, imageStore);
                case SMITH_KEY:
                    return parseSmith(properties, world, imageStore);
                case VEIN_KEY:
                    return parseVein(properties, world, imageStore);
                case ATTACKER_KEY:
                    return parseAttacker(properties, world, imageStore);
            }
        }

        return false;
    }

    private boolean parseAttacker(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == ATTACKER_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[ATTACKER_COL]),
                    Integer.parseInt(properties[ATTACKER_ROW]));
            Entity entity = world.createAttacker(properties[ATTACKER_ID],
                    pt,
                    Integer.parseInt(
                            properties[ATTACKER_ACTION_PERIOD]), Integer.parseInt(
                            properties[ATTACKER_ANIMATION_PERIOD]),
                    imageStore.getImageList(ATTACKER_KEY));
            Attacker.DEATH_KEY = GREEN_DEATH_KEY;
            world.tryAddEntity(entity);
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }

    private boolean parseBackground(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            world.setBackground(pt,
                    new Background(id,
                            imageStore.getImageList(id),
                            pt));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

    private boolean parseMiner(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == MINER_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                    Integer.parseInt(properties[MINER_ROW]));
            Entity entity = world.createMinerNotFull(properties[MINER_ID],
                    Integer.parseInt(
                            properties[MINER_LIMIT]),
                    pt, Integer.parseInt(
                            properties[MINER_ACTION_PERIOD]), Integer.parseInt(
                            properties[MINER_ANIMATION_PERIOD]),
                    imageStore.getImageList(MINER_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }

    private boolean parseObstacle(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Entity entity = world.createObstacle(properties[OBSTACLE_ID], pt,
                    imageStore.getImageList(OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    private boolean parseOre(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == ORE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                    Integer.parseInt(properties[ORE_ROW]));
            Entity entity = world.createOre(properties[ORE_ID], pt, Integer.parseInt(
                    properties[ORE_ACTION_PERIOD]),
                    imageStore.getImageList(ORE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == ORE_NUM_PROPERTIES;
    }

    private boolean parseSmith(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == SMITH_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            Entity entity = world.createBlacksmith(properties[SMITH_ID], pt,
                    imageStore.getImageList(SMITH_KEY),
                    SMITH_ACTION_PERIOD,
                    SMITH_ANIMATION_PERIOD
                    );
            world.tryAddEntity(entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    private boolean parseVein(
            String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == VEIN_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                    Integer.parseInt(properties[VEIN_ROW]));
            Entity entity = world.createVein(properties[VEIN_ID], pt,
                    Integer.parseInt(
                            properties[VEIN_ACTION_PERIOD]),
                    imageStore.getImageList(VEIN_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }


    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }
}
