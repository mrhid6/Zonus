package mrhid6.zonus.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketTile extends PacketMain {

	public Payload payload;
	public EntityPlayerMP thePlayer;
	public int xCoord;
	public int yCoord;
	public int zCoord;

	public PacketTile( int id, EntityPlayerMP player ) {
		packetId = id;
		thePlayer = player;
		packetType = 0;
		payload = new Payload();
	}

	public PacketTile( int id, int x, int y, int z, Payload payload ) {
		packetId = id;
		packetType = 0;

		xCoord = x;
		yCoord = y;
		zCoord = z;

		this.payload = payload;
	}

	public TileEntity getTarget( World world ) {
		return world.getBlockTileEntity(xCoord, yCoord, zCoord);
	}

	@Override
	public void readData( DataInputStream data ) throws IOException {
		xCoord = data.readInt();
		yCoord = data.readInt();
		zCoord = data.readInt();

		payload.readPayloadData(data);

	}

	public boolean targetExists( World world ) {
		return world.blockExists(xCoord, yCoord, zCoord);
	}

	@Override
	public void writeData( DataOutputStream data ) throws IOException {
		data.writeInt(xCoord);
		data.writeInt(yCoord);
		data.writeInt(zCoord);

		payload.writePayloadData(data);

	}

}
