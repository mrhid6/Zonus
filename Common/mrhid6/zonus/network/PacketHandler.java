package mrhid6.zonus.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import mrhid6.zonus.GridManager;
import mrhid6.zonus.interfaces.IPacketXorHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	private static int packetIdCounter = 0;

	public static int getAvailablePacketId() {
		return ++packetIdCounter;
	}

	@Override
	public void onPacketData( INetworkManager manager, Packet250CustomPayload packet, Player player ) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int id = data.read();
			int type = data.read();
			if (type == 0) {
				PacketTile tilePacket = new PacketTile(id, (player instanceof EntityPlayerMP) ? (EntityPlayerMP) player : null);
				tilePacket.readData(data);

				World world = ((EntityPlayer) player).worldObj;
				if (!tilePacket.targetExists(world)) {
					// System.out.println("tile was not exsits!");
					return;
				}
				TileEntity tile = tilePacket.getTarget(world);
				if (!(tile instanceof IPacketXorHandler)) {
					// System.out.println("tile was not implementing!");
					return;
				}
				// System.out.println("handling data");
				((IPacketXorHandler) tile).handleTilePacket(tilePacket);
			}

			if (type == 1) {

				PacketGrid tilePacket = new PacketGrid(id, (player instanceof EntityPlayerMP) ? (EntityPlayerMP) player : null);
				tilePacket.readData(data);

				GridManager.handleGridPacket(id, tilePacket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
