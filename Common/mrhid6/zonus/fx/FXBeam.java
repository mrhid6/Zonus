package mrhid6.zonus.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class FXBeam extends EntityFX
{
	int type;
	double dX;
	double dY;
	double dZ;
	double updateX;
	double updateY;
	double updateZ;
	private float yaw;
	private float pitch;
	private float prevYaw;
	private float prevPitch;
	private float rotateSpeed = 5.0F;
	private double offset = 0.0D;
	private float length;
	private float endMod = 1.0F;
	private int maxLengthAge = 10;

	private boolean positionChanged = false;

	private boolean fppc = true;

	public FXBeam(World world, double x, double y, double z, double destX, double destY, double destZ) {
		this(world, x, y, z, destX, destY, destZ, 0);
	}

	public FXBeam(World world, double x, double y, double z, double destX, double destY, double destZ, int type) {
		super(world, x, y, z);
		this.type = type;
		this.dX = destX;
		this.dY = destY;
		this.dZ = destZ;

		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;

		setSize(0.2F, 0.2F);

		calculateLengthAndRotation();
		this.prevYaw = this.yaw;
		this.prevPitch = this.pitch;

		this.rotateSpeed = 30.0F;
		this.maxLengthAge = 10;

		this.particleMaxAge = 100;
	}

	private void calculateLengthAndRotation() {
		float deltaX = (float)(this.posX - this.dX);
		float deltaY = (float)(this.posY - this.dY);
		float deltaZ = (float)(this.posY - this.dZ);


		//TODO check This!
		this.length = MathHelper.floor_double(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
		double hDist = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
		this.yaw = ((float)(Math.atan2(deltaX, deltaZ) * 180.0D / 3.141592653589793D));
		this.pitch = ((float)(Math.atan2(deltaY, hDist) * 180.0D / 3.141592653589793D));
	}

	public void setBeamLocationAndTarget(double posX, double posY, double posZ, double targetX, double targetY, double targetZ)
	{
		this.updateX = posX;
		this.updateY = posY;
		this.updateZ = posZ;
		this.dX = targetX;
		this.dY = targetY;
		this.dZ = targetZ;

		if (this.particleAge > this.particleMaxAge - 5) {
			this.particleMaxAge = (this.particleAge + 5);
		}

		this.positionChanged = true;
	}

	private void storePrevInformation() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posY;

		if (this.positionChanged) {
			this.posX = this.updateX;
			this.posY = this.updateY;
			this.posY = this.updateZ;
			if (this.fppc) {
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				if (player != null) {
					float yaw = player.rotationYaw;
					if (yaw == 0.0F) yaw = 0.01F;
					float rotationYaw = (float)(yaw * 3.141592653589793D / 180.0D);
					float offsetX = (float)Math.cos(rotationYaw) * 0.1F;
					float offsetZ = (float)Math.sin(rotationYaw) * 0.1F;
					this.posX -= offsetX;
					this.posY -= offsetZ;
				}
			}
			this.positionChanged = false;
		}

		this.prevYaw = this.yaw;
		this.prevPitch = this.pitch;
	}

	public void setFirstPersonPlayerCast() {
		this.fppc = true;
	}

	private void handleAging() {
		this.particleAge += 1;
		if (this.particleAge >= this.particleMaxAge)
		{
			setDead();
		}
	}

	public void setType(int type) {
		this.type = type;
	}
	@Override
	public void onUpdate()
	{
		storePrevInformation();
		calculateLengthAndRotation();
		handleAging();
	}
	
	@Override
	public void renderParticle(Tessellator tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		tessellator.draw();

		GL11.glPushMatrix();{
			float scaleFactor = 1.0F;
			float slide = (float)this.worldObj.getTotalWorldTime();
			float rot = (float)this.worldObj.provider.getWorldTime() % (360.0F / this.rotateSpeed) * this.rotateSpeed + this.rotateSpeed * par2;

			float size = this.particleAge / this.maxLengthAge;
			if (size > 1.0F) size = 1.0F;

			float op = 0.4F;

			switch (this.type) {
			default:
				Minecraft.getMinecraft().renderEngine.bindTexture("/mods/zonus/textures/paricles/beam.png");
				break;
			}

			GL11.glTexParameterf(3553, 10242, 10497.0F);
			GL11.glTexParameterf(3553, 10243, 10497.0F);

			GL11.glDisable(2884);

			float var11 = slide + par2;
			float var12 = -var11 * 0.2F - MathHelper.floor_float(-var11 * 0.1F);

			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 1);
			GL11.glDepthMask(false);

			float xx = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
			float yy = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
			float zz = (float)(this.prevPosZ + (this.posY - this.prevPosZ) * par2 - interpPosZ);
			GL11.glTranslated(xx, yy, zz);

			if (this.fppc) {
				GL11.glScalef(0.4F, 0.4F, 0.4F);
			}

			float ry = this.prevYaw + (this.yaw - this.prevYaw) * par2;
			float rp = this.prevPitch + (this.pitch - this.prevPitch) * par2;
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(180.0F + ry, 0.0F, 0.0F, -1.0F);
			GL11.glRotatef(rp, 1.0F, 0.0F, 0.0F);

			double offset1 = -0.15D * size;
			double offset2 = 0.15D * size;
			double offset3 = -0.15D * size * this.endMod;
			double offset4 = 0.15D * size * this.endMod;

			GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
			int i = 5;
			float inc = 36.0F;

			for (int t = 0; t < i; t++)
			{
				double l = this.length * size * scaleFactor;
				double tl = 0.0D;
				double br = 1.0D;
				double mU = -1.0F + var12 + t / 3.0F;
				double mV = this.length * size * scaleFactor + mU;

				GL11.glRotatef(36.0F, 0.0F, 1.0F, 0.0F);
				tessellator.startDrawingQuads();
				tessellator.setBrightness(200);
				if (t % 2 == 0)
					tessellator.setColorRGBA_F(this.particleRed, this.particleBlue, this.particleGreen, op);
				else {
					tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.4F);
				}
				tessellator.addVertexWithUV(offset3, l, 0.0D, br, mV);
				tessellator.addVertexWithUV(offset1, 0.0D, 0.0D, br, mU);
				tessellator.addVertexWithUV(offset2, 0.0D, 0.0D, tl, mU);
				tessellator.addVertexWithUV(offset4, l, 0.0D, tl, mV);
				tessellator.draw();
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDepthMask(true);
			GL11.glDisable(3042);
			GL11.glEnable(2884);
		}
		GL11.glPopMatrix();

		GL11.glBindTexture(3553, Minecraft.getMinecraft().renderEngine.getTexture("/particles.png"));

		tessellator.startDrawingQuads();
	}

	public void setRGBColorF(float r, float g, float b) {
		this.particleGreen = g;
		this.particleRed = r;
		this.particleBlue = b;
	}
}