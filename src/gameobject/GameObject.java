package gameobject;

public interface GameObject {
    default void update() {}
    default boolean expired() { return false; }
}
