package mrhid6.zonus.proxy;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerProvider;

public class PowerMJProxy extends PowerProvider
{
	public PowerMJProxy()
	{
		this.powerLoss = 0;
		this.powerLossRegularity = 72000;
		configure(0, 0);
	}

	public void configure(int maxEnergyReceived, int maxStoredEnergy)
	{
		super.configure(0, 0, maxEnergyReceived, 0, maxStoredEnergy);
	}

	public boolean update(IPowerReceptor receptor)
	{
		return false;
	}

	public void addEnergy(float quantity)
	{
		this.energyStored += quantity;

		if (this.energyStored > this.maxEnergyStored)
		{
			this.energyStored = this.maxEnergyStored;
		}
	}

	public void subtractEnergy(float quantity)
	{
		this.energyStored -= quantity;

		if (this.energyStored < 0.0F)
		{
			this.energyStored = 0.0F;
		}
	}

	public void setEnergyStored(float quantity)
	{
		this.energyStored = quantity;

		if (this.energyStored > this.maxEnergyStored)
		{
			this.energyStored = this.maxEnergyStored;
		}
		else if (this.energyStored < 0.0F)
		{
			this.energyStored = 0.0F;
		}
	}

	public void roundEnergyStored()
	{
		this.energyStored = Math.round(this.energyStored);
	}
}
