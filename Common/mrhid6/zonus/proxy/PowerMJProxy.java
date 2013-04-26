package mrhid6.zonus.proxy;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerProvider;

public class PowerMJProxy extends PowerProvider {

	public PowerMJProxy() {
		powerLoss = 0;
		powerLossRegularity = 72000;
		configure(0, 0);
	}

	public void addEnergy( float quantity ) {
		energyStored += quantity;

		if (energyStored > maxEnergyStored) {
			energyStored = maxEnergyStored;
		}
	}

	public void configure( int maxEnergyReceived, int maxStoredEnergy ) {
		super.configure(0, 0, maxEnergyReceived, 0, maxStoredEnergy);
	}

	public void roundEnergyStored() {
		energyStored = Math.round(energyStored);
	}

	public void setEnergyStored( float quantity ) {
		energyStored = quantity;

		if (energyStored > maxEnergyStored) {
			energyStored = maxEnergyStored;
		} else if (energyStored < 0.0F) {
			energyStored = 0.0F;
		}
	}

	public void subtractEnergy( float quantity ) {
		energyStored -= quantity;

		if (energyStored < 0.0F) {
			energyStored = 0.0F;
		}
	}

	@Override
	public boolean update( IPowerReceptor receptor ) {
		return false;
	}
}
