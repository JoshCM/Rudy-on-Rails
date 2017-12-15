package models.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Eine eigene Implementierung von Observable
 * Wird gebraucht, damit die Observer nicht mitserialisiert werden, wenn eine Map gespeichert wird
 * @author micha
 *
 */
public class ObservableModel {
	private transient List<ModelObserver> observers = new ArrayList<ModelObserver>();
	private boolean changed = false;
	
	public void addObserver(ModelObserver observer) {
		observers.add(observer);
	}
	
	public void deleteObserver(ModelObserver observer) {
		observers.remove(observer);
	}
	
	public void deleteObservers() {
		for(ModelObserver observer : observers) {
			observers.remove(observer);
		}
	}
	
	public void notifyObservers(Object arg) {
		if(hasChanged()) {
			for(ModelObserver observer : observers) {
				observer.update(this, arg);
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
