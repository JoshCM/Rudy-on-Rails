package models.game;

import models.base.Model;

/**
 * Interface für platzierbare Objekte
 */
public interface Placeable extends Model {
    int getX();
	int getY();
}