package models.base;

import exceptions.ObservableModelException;
import states.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Eine eigene Implementierung von Observable
 * Wird gebraucht, damit die Observer nicht mitserialisiert werden, wenn eine Map gespeichert wird
 * @author micha
 *
 */
public class ObservableModel extends Observable {
	private transient List<ModelObserver> observerList = new ArrayList<ModelObserver>();
	private boolean changed = false; // müsste in einen State geändert werden.
    private GameState status;
	
	public void registerObserver(ModelObserver newObserver) {
		if(observerList == null) {
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
        if (observerList.size()==1) {
            unregisterAllObservers();
        }
    }

    /**
     * @param observer
     * Removes specific Observer from Observerlist.
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
	
	public void notifyObservers(Object arg) {
		if(hasChanged()) {
			for(ModelObserver observer : observerList) {
				observer.update(arg);
			}
		}
		clearChanged();
	}
	
	public void setChanged() {
		this.changed = true;
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public void clearChanged() {
		this.changed = false;
	}
}
