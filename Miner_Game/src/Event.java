public final class Event
{
    private Action action;
    private Entity entity;
    private long time;

    public Event(
            Action action,
            long time,
            Entity entity)
    {
        this.action = action;
        this.entity = entity;
        this.time   = time;
    }

    public Action getAction() {
        return action;
    }

    public Entity getEntity() {
        return entity;
    }

    public long   getTime() {
        return time;
    }
}
