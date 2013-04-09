package mrhid6.zonus.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;

public class PacketUtils {

	public static void sendToPlayer( EntityPlayer entityplayer, Packet packet ) {
		EntityPlayerMP player = (EntityPlayerMP) entityplayer;
		player.playerNetServerHandler.sendPacketToPlayer(packet);
	}

	public static void sendToPlayers( Packet packet, TileEntity theTile ) {
		if (!theTile.worldObj.isRemote) {
			sendToPlayers(packet, theTile.worldObj, theTile.xCoord, theTile.yCoord, theTile.zCoord, 192);
		}
	}

	public static void sendToPlayers( Packet packet, World w, int x, int y, int z, int maxDistance ) {
		if (packet != null) {
			for (int j = 0; j < w.playerEntities.size(); j++) {
				EntityPlayerMP player = (EntityPlayerMP) w.playerEntities.get(j);

				if ((Math.abs(player.posX - x) <= maxDistance) && (Math.abs(player.posY - y) <= maxDistance) && (Math.abs(player.posZ - z) <= maxDistance)) {
					player.playerNetServerHandler.sendPacketToPlayer(packet);
				}
			}
		}
	}

	public static void sendToServer( Packet packet ) {
		if (packet != null) {
			FMLClientHandler.instance().getClient().getNetHandler().addToSendQueue(packet);
		}
	}
}
