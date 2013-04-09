package mrhid6.zonus.entities;

import mrhid6.zonus.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTitan extends Entity {

	private boolean field_70279_a;
	EntityPlayer player;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;

	@SideOnly(Side.CLIENT)
	private double velocityZ;

	public EntityTitan( World par1World ) {
		super(par1World);
		field_70279_a = true;
		preventEntitySpawning = true;
		this.setSize(1.5F, 2F);
		yOffset = height / 1.3F;
	}

	public EntityTitan( World par1World, double par2, double par4, double par6 ) {
		this(par1World);
		this.setPosition(par2, par4 + yOffset, par6);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = par2;
		prevPosY = par4;
		prevPosZ = par6;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom( DamageSource par1DamageSource, int par2 ) {
		if (this.isEntityInvulnerable()) {
			return false;
		} else if (!worldObj.isRemote && !isDead) {
			this.setForwardDirection(-this.getForwardDirection());
			this.setTimeSinceHit(10);
			this.setDamageTaken(this.getDamageTaken() + par2 * 10);
			this.setBeenAttacked();
			boolean flag = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer) par1DamageSource.getEntity()).capabilities.isCreativeMode;

			if (flag || this.getDamageTaken() > 40) {
				if (riddenByEntity != null) {
					riddenByEntity.mountEntity(this);
				}

				if (!flag) {
					this.dropItemWithOffset(Item.boat.itemID, 1, 0.0F);
				}

				this.setDead();
			}

			return true;
		} else {
			return true;
		}
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(17, new Integer(0));
		dataWatcher.addObject(18, new Integer(1));
		dataWatcher.addObject(19, new Integer(0));
	}

	@SideOnly(Side.CLIENT)
	public void func_70270_d( boolean par1 ) {
		field_70279_a = par1;
	}

	/**
	 * returns the bounding box for this entity
	 */
	@Override
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like boats or
	 * minecarts.
	 */
	@Override
	public AxisAlignedBB getCollisionBox( Entity par1Entity ) {
		return par1Entity.boundingBox;
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public int getDamageTaken() {
		return dataWatcher.getWatchableObjectInt(19);
	}

	/**
	 * Gets the forward direction of the entity.
	 */
	public int getForwardDirection() {
		return dataWatcher.getWatchableObjectInt(18);
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding
	 * this one.
	 */
	@Override
	public double getMountedYOffset() {
		return height * 0.0D - 0.2D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 1.6F;
	}

	/**
	 * Gets the time since the last hit.
	 */
	public int getTimeSinceHit() {
		return dataWatcher.getWatchableObjectInt(17);
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact( EntityPlayer par1EntityPlayer ) {

		if (Utils.isClientWorld()) {
			return true;
		}
		player = par1EntityPlayer;

		return true;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (player == null) {
			// System.out.println("playeris null!");
			return;
		}

		lastTickPosX = prevPosX = player.prevPosX;
		lastTickPosY = prevPosY = player.prevPosY + player.getEyeHeight() - 0.35F;
		lastTickPosZ = prevPosZ = player.prevPosZ;

		setPosition(posX, posY, posZ);
		posX = player.posX;
		posY = player.posY + player.getEyeHeight() - 0.35F;
		posZ = player.posZ;

		prevRotationYaw = player.prevRotationYawHead;
		rotationYaw = player.rotationYawHead;

		motionX = posX - prevPosX;
		motionY = posY - prevPosY;
		motionZ = posZ - prevPosZ;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
	 */
	public void performHurtAnimation() {
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT( NBTTagCompound par1NBTTagCompound ) {
	}

	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setDamageTaken( int par1 ) {
		dataWatcher.updateObject(19, Integer.valueOf(par1));
	}

	/**
	 * Sets the forward direction of the entity.
	 */
	public void setForwardDirection( int par1 ) {
		dataWatcher.updateObject(18, Integer.valueOf(par1));
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	 * posY, posZ, yaw, pitch
	 */
	@Override
	public void setPositionAndRotation2( double par1, double par3, double par5, float par7, float par8, int par9 ) {
		if (field_70279_a) {
		} else {
			double d3 = par1 - posX;
			double d4 = par3 - posY;
			double d5 = par5 - posZ;
			double d6 = d3 * d3 + d4 * d4 + d5 * d5;

			if (d6 <= 1.0D) {
				return;
			}
		}

		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
	public void setTimeSinceHit( int par1 ) {
		dataWatcher.updateObject(17, Integer.valueOf(par1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity( double par1, double par3, double par5 ) {
		velocityX = motionX = par1;
		velocityY = motionY = par3;
		velocityZ = motionZ = par5;
	}

	@Override
	public void updateRiderPosition() {
		if (riddenByEntity != null) {
			double d0 = Math.cos(rotationYaw * Math.PI / 180.0D) * 0.4D;
			double d1 = Math.sin(rotationYaw * Math.PI / 180.0D) * 0.4D;
			riddenByEntity.setPosition(posX + d0, posY + this.getMountedYOffset() + riddenByEntity.getYOffset(), posZ + d1);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT( NBTTagCompound par1NBTTagCompound ) {
	}
}
