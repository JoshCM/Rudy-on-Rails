package models.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SignalsTests {
	@Test
	public void Signals_SwitchSignals_HasNorthAndSouthAsIdlePositions() {
		Signals signals = new Signals("TestSession", new Square("TestSession", 0, 0));
		
		assertTrue(signals.isNorthSignalActive());
		assertTrue(signals.isSouthSignalActive());
		assertFalse(signals.isEastSignalActive());
		assertFalse(signals.isWestSignalActive());
	}
	
	@Test
	public void Signals_SwitchSignals_SwitchesFromFromNorthSouthToEastWest() {
		Signals signals = new Signals("TestSession", new Square("TestSession", 0, 0));
		
		signals.switchSignals();
		
		assertFalse(signals.isNorthSignalActive());
		assertFalse(signals.isSouthSignalActive());
		assertTrue(signals.isEastSignalActive());
		assertTrue(signals.isWestSignalActive());
	}
	
	@Test
	public void Signals_SwitchSignals_TwoSwitchesLeadsToIdlePosition() {
		Signals signals = new Signals("TestSession", new Square("TestSession", 0, 0));
		
		signals.switchSignals();
		signals.switchSignals();
		
		assertTrue(signals.isNorthSignalActive());
		assertTrue(signals.isSouthSignalActive());
		assertFalse(signals.isEastSignalActive());
		assertFalse(signals.isWestSignalActive());
	}
}
