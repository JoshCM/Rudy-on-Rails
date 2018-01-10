package models.base;

public interface ModelObserver {
    void update(ModelObservable o, Object arg);
}
