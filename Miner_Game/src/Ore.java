import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Ore  extends ActivityEntity {
    private final int BLOB_ANIMATION_MAX = 150;
    private final int BLOB_ANIMATION_MIN = 50;
    private final String BLOB_ID_SUFFIX = " -- blob";
    private static String GREEN_OREBLOB_KEY = "blobg";
    private static String RED_OREBLOB_KEY = "blobr";
    private static String BLOB_KEY = "blobg";
    private static boolean attackMode;                  // False is harvest mode
    private final int BLOB_PERIOD_SCALE = 14;           // Larger Number means faster OreBlobs

    private static final Random rand = new Random();

    public Ore(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod)
    {
        super(id, images, 0, position, actionPeriod);
    }


    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = getPosition();

        if (BLOB_KEY == null) {
            BLOB_KEY = GREEN_OREBLOB_KEY;
        }
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = world.createOreBlob(
                getId() + BLOB_ID_SUFFIX,
                pos,
                getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN + rand.nextInt(
                        BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        setAttackMode(blob.getAttack());
        setBlobKey();
        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }

    private void setAttackMode(boolean attackMode){
        this.attackMode = attackMode;
    }

    private void setBlobKey(){
        if(!attackMode){
            BLOB_KEY = GREEN_OREBLOB_KEY;
        }
        else {
            BLOB_KEY = RED_OREBLOB_KEY;
        }
    }

}
