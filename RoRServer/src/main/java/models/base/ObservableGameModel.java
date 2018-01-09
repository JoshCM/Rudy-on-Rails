package models.base;

import exceptions.ObservableModelException;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableGameModel extends ModelBase implements ModelObservable {
    private transient List<ModelObserver> observerList = new ArrayList<ModelObserver>();

    public void registerObserver(ModelObserver newObserver) {
        if (observerList == null) {
            observerList = new ArrayList<ModelObserver>();
        }
        if (observerList.contains(newObserver)) {
            throw new ObservableModelException("Observer is already in Observerlist");
        }
        observerList.add(newObserver);
    }


    /**
     * Works only if the Observer has 1 Observer
     */
    public void unregisterObserver() {
        if (observerList.size() == 1) {
            unregisterAllObservers();
        }
    }

    /**
     * @param observer Removes specific Observer from Observerlist.
     */
    public void unregisterObserver(ModelObserver observer) {
        if (observerList.contains(observer)) {
            observerList.remove(observer);
        }
    }

    /**
     * Removes ALL Observers from Observerlist
     */
    public void unregisterAllObservers() {
        observerList.clear();
    }


    /**
     * Teilt allen Observern mit, dass sich etwas geändert hat.
     *
     * @param arg
     */
    public void notifyObservers(Object arg) {
        for (ModelObserver observer : observerList) {
            observer.update(this, arg);
        }
    }

}
