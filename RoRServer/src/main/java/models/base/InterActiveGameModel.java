package models.base;

import exceptions.ObservableModelException;
import states.RoRState;

import java.util.ArrayList;
import java.util.List;


/**
 * Interactive Gamemodels können sowohl Observen, als auch Observable sein.
 *
 * @author micha
 */
public abstract class InterActiveGameModel extends ModelBase implements ModelObserver, ModelObservable {
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
     * @param arg
     */
    public void notifyObservers(Object arg) {
        if (hasChanged()) {
            for (ModelObserver observer : observerList) {
                observer.update(this, arg);
            }
        }
        clearChanged();
    }




    public void setChanged() {
        setState(RoRState.CHANGED);
    }

    public boolean hasChanged() {
        if (RoRState.CHANGED == getState()) {
            return true;
        } else {
            return false;
        }
    }

    public void clearChanged() {
        setState(RoRState.CLEAR);
    }
}
