package mrhid6.zonus;

import java.util.ArrayList;
import java.util.HashMap;
import mrhid6.zonus.network.PacketGrid;
import mrhid6.zonus.network.Payload;
import mrhid6.zonus.tileEntity.TECableBase;
import mrhid6.zonus.tileEntity.TEPoweredBase;
import mrhid6.zonus.tileEntity.TEStearilliumEnergyCube;
import mrhid6.zonus.tileEntity.TETriniumConverter;
import mrhid6.zonus.tileEntity.TEZoroChest;
import mrhid6.zonus.tileEntity.TEZoroController;
import mrhid6.zonus.tileEntity.multiblock.TEStearilliumReactor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;

public class GridPower {

	public static final float energyCubeIncrease = 5000.0F;
	private HashMap<TECableBase, Integer> cablesArray;
	private float ClientMaxPower = 0;
	private HashMap<TETriniumConverter, Integer> converterArray;
	private HashMap<TEStearilliumEnergyCube, Integer> energyCubeArray;
	private HashMap<TEStearilliumReactor, Integer> reactorArray;
	private int energystorage = 0;
	public int gridIndex;

	private ArrayList<TEPoweredBase> machineArray;
	public TEZoroController masterController;

	private float maxPower = 30000.0F;

	private float Power = 0.0F;

	private ArrayList<TEZoroChest> storageArray;

	public GridPower() {

		cablesArray = new HashMap<TECableBase, Integer>();
		machineArray = new ArrayList<TEPoweredBase>();
		energyCubeArray = new HashMap<TEStearilliumEnergyCube, Integer>();
		reactorArray = new HashMap<TEStearilliumReactor, Integer>();
		converterArray = new HashMap<TETriniumConverter, Integer>();
		storageArray = new ArrayList<TEZoroChest>();

		Power = 0;

		gridIndex = GridManager.addGridToManager(this);

		System.out.println(Utils.isClientWorld()+"new grid power!");
	}

	public void addCable( TECableBase te ) {
		if(!hasCable(te)){
			cablesArray.put(te, 2);
			te.gridindex = gridIndex;
			te.setUpdate(true);
			System.out.println("Added Cable :"+te.toString());
		}
	}

	public void addConverter( TETriniumConverter te,int i) {
		
		if(!hasConverter(te) && ((TETriniumConverter) te).canConnectOnSide(i)){
			converterArray.put(te, 2);
			te.gridindex = gridIndex;
			te.setUpdate(true);
			System.out.println("Added Converter :"+te.toString());
		}
	}

	public void addEnergy( float f ) {
		Power += f;

		if (Power > getMaxEnergy()) {
			Power = getMaxEnergy();
		}

	}

	public void addEnergyCube( TEStearilliumEnergyCube te ) {
		energyCubeArray.put(te, 2);
		energystorage++;
		te.gridindex = gridIndex;
		te.setUpdate(true);
		System.out.println("added cube");
		WorkOutMaxPower();

	}
	
	public void addReactorCore( TEStearilliumReactor te ) {
		reactorArray.put(te, 2);
		te.gridindex = gridIndex;
		te.setUpdate(true);
		System.out.println("added reactor");
		
	}

	public void addMachine( TEPoweredBase te ) {
		machineArray.remove(te);
		machineArray.add(te);
		cleanupneeded();
	}

	public void addStorage( TEZoroChest te ) {
		storageArray.remove(te);
		storageArray.add(te);

		te.gridindex = gridIndex;
	}

	public boolean canDiscoverObj( TEPoweredBase te ) {

		for (TECableBase cable : cablesArray.keySet()) {

			if (cablesArray.get(cable) == 1) {
				continue;
			}

			for (int i = 0; i < 6; i++) {

				int x1 = cable.xCoord + Config.SIDE_COORD_MOD[i][0];
				int y1 = cable.yCoord + Config.SIDE_COORD_MOD[i][1];
				int z1 = cable.zCoord + Config.SIDE_COORD_MOD[i][2];

				TileEntity te1 = cable.worldObj.getBlockTileEntity(x1, y1, z1);

				if (te1 instanceof TEPoweredBase) {

					if (te.xCoord == x1 && te.yCoord == y1 && te.zCoord == z1) {
						return true;
					}
				}
			}
		}

		return false;

	}
	
	public boolean canDiscoverReactorObj( TEStearilliumReactor te ) {
		
		for (TECableBase cable : cablesArray.keySet()) {
			
			if (cablesArray.get(cable) == 1) {
				continue;
			}
			
			for (int i = 0; i < 6; i++) {
				
				int x1 = cable.xCoord + Config.SIDE_COORD_MOD[i][0];
				int y1 = cable.yCoord + Config.SIDE_COORD_MOD[i][1];
				int z1 = cable.zCoord + Config.SIDE_COORD_MOD[i][2];
				
				TileEntity te1 = cable.worldObj.getBlockTileEntity(x1, y1, z1);
				
				if (te1 instanceof TEStearilliumReactor) {
					
					
					
					if(((TEStearilliumReactor)te1).getCoreBlock() == te){
						return true;
					}
				}
			}
		}
		
		return false;
		
	}
	public boolean canDiscoverCable( TECableBase te ) {
		for (int i = 0; i < 6; i++) {
			int x1 = masterController.xCoord + Config.SIDE_COORD_MOD[i][0];
			int y1 = masterController.yCoord + Config.SIDE_COORD_MOD[i][1];
			int z1 = masterController.zCoord + Config.SIDE_COORD_MOD[i][2];
			
			TileEntity te1 = masterController.worldObj.getBlockTileEntity(x1, y1, z1);
			
			if (te1 instanceof TECableBase) {
				
				if((TECableBase)te1 == te){
					return true;
				}
			}
		}
		
		for (TECableBase cable : cablesArray.keySet()) {
			
			if (cablesArray.get(cable) == 1) {
				//System.out.println("cable is marked for removal!");
				continue;
			}
			
			for (int i = 0; i < 6; i++) {
				
				int x1 = cable.xCoord + Config.SIDE_COORD_MOD[i][0];
				int y1 = cable.yCoord + Config.SIDE_COORD_MOD[i][1];
				int z1 = cable.zCoord + Config.SIDE_COORD_MOD[i][2];
				
				TileEntity te1 = cable.worldObj.getBlockTileEntity(x1, y1, z1);
				
				if (te1 instanceof TECableBase) {
					
					if( ((TECableBase)te1) == te){
						return true;
					}
				}
			}
		}
		for (TETriniumConverter controller : converterArray.keySet()) {
			
			if (converterArray.get(controller) == 1) {
				//System.out.println("cable is marked for removal!");
				continue;
			}
			
			for (int i = 0; i < 6; i++) {
				
				int x1 = controller.xCoord + Config.SIDE_COORD_MOD[i][0];
				int y1 = controller.yCoord + Config.SIDE_COORD_MOD[i][1];
				int z1 = controller.zCoord + Config.SIDE_COORD_MOD[i][2];
				
				TileEntity te1 = controller.worldObj.getBlockTileEntity(x1, y1, z1);
				
				if (te1 instanceof TECableBase) {
					
					if( ((TECableBase)te1) == te){
						return true;
					}
				}
			}
		}
		
		return false;
		
	}

	public boolean checkCable( TECableBase te ) {

		return (te.gridindex == gridIndex);
	}

	public void cleanup() {
		machineArray.clear();
		storageArray.clear();
	}

	public void cleanupneeded() {
	}

	public int countEnergyCubes() {

		int res = 0;

		for (TEStearilliumEnergyCube cube : energyCubeArray.keySet()) {

			if (energyCubeArray.get(cube) == 2) {
				res++;
			}
		}
		return res;

	}

	public void discover() {
		cleanup();
		
		if(masterController==null){
			System.out.println("controller null!");
			return;
			
		}
		
		int x1 = masterController.xCoord;
		int y1 = masterController.yCoord;
		int z1 = masterController.zCoord;
		
		ArrayList<TECableBase> cab = new ArrayList<TECableBase>();
		ArrayList<TETriniumConverter> con = new ArrayList<TETriniumConverter>();
		
		pathFinder(x1, y1, z1, masterController.worldObj,cab,con);
		
		for(TECableBase cable : cablesArray.keySet()){
			if(!cab.contains(cable)){
				removeCable(cable);
			}
		}
		
	}

	public Packet getDescriptionPacket() {
		WorkOutMaxPower();
		Payload payload = new Payload(0, 0, 2, 2, 0);

		// System.out.println(gridindex);

		payload.intPayload[0] = gridIndex;
		payload.intPayload[1] = energystorage;

		payload.floatPayload[0] = getEnergyStored();
		payload.floatPayload[1] = ClientMaxPower;

		PacketGrid packet = new PacketGrid(gridIndex, payload);
		return packet.getPacket();
	}

	public float getEnergyStored() {
		return Power;
	}

	public ArrayList<TEPoweredBase> getMachines() {
		return machineArray;
	}

	public float getMaxEnergy() {

		return ClientMaxPower;
	}

	public int getScaledEnergyStored( int scale ) {
		return Math.round(getEnergyStored() * scale / getMaxEnergy());

	}

	public void handleTilePacket( PacketGrid packet ) {
		gridIndex = packet.payload.intPayload[0];
		energystorage = packet.payload.intPayload[1];

		setEnergyStored(packet.payload.floatPayload[0]);
		// ClientMaxPower = packet.payload.floatPayload[1];

		if (Utils.isClientWorld()) {
			gridIndex = packet.payload.intPayload[0];
			energystorage = packet.payload.intPayload[1];
			setEnergyStored(packet.payload.floatPayload[0]);
			// ClientMaxPower = packet.payload.floatPayload[1];
		}
		WorkOutMaxPower();
	}

	public boolean hasCable( TECableBase te ) {
		for (TECableBase te1 : cablesArray.keySet()) {
			if (te == te1 && cablesArray.get(te1) == 2) {
				return true;
			}
		}
		return false;
	}

	public boolean hasConverter( TETriniumConverter te ) {
		for (TETriniumConverter cube : converterArray.keySet()) {
			if (cube == te && converterArray.get(cube) == 2) {
				return true;
			}
		}
	
		return false;
	}

	public boolean hasReactor( TEStearilliumReactor te ) {
		
		for (TEStearilliumReactor cube : reactorArray.keySet()) {
			if (cube == te && reactorArray.get(cube) == 2) {
				return true;
			}
		}
		
		return false;
	}

	public boolean hasMachine( TEPoweredBase te ) {
		for (TEPoweredBase te1 : machineArray) {
			if (te.equals(te1)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPower() {
		return (Power > 0);
	}

	public boolean hasEnergyCube( TEStearilliumEnergyCube te ) {
	
		for (TEStearilliumEnergyCube cube : energyCubeArray.keySet()) {
			if (cube == te && energyCubeArray.get(cube) == 2) {
				return true;
			}
		}
	
		return false;
	}

	public boolean isController( World w, int x, int y, int z ) {

		TileEntity te1 = w.getBlockTileEntity(x, y, z);

		if (masterController != null && te1 != null && te1 instanceof TEZoroController) {
			if (masterController.xCoord == x && masterController.yCoord == y && masterController.zCoord == z) {
				return true;
			}
		} else {
			// System.out.println((masterController == null));
		}
		return false;
	}

	public void pathFinder( int x, int y, int z, World w,ArrayList cab,ArrayList con ) {
		for (int i = 0; i < 6; i++) {

			int x1 = x + Config.SIDE_COORD_MOD[i][0];
			int y1 = y + Config.SIDE_COORD_MOD[i][1];
			int z1 = z + Config.SIDE_COORD_MOD[i][2];

			TileEntity te = w.getBlockTileEntity(x, y, z);
			TileEntity te1 = w.getBlockTileEntity(x1, y1, z1);
			
			if(te1 instanceof TECableBase){
				
				TECableBase cable = (TECableBase)te1;
				
				if(!cab.contains(cable) && cable.canInteractWith(te, i, false)){
					addCable(cable);
					cab.add(cable);
					pathFinder(x1,y1,z1,w,cab,con);
				}
				
			}
			
			if (te1 instanceof TETriniumConverter) {
				
				TETriniumConverter converter = (TETriniumConverter)te1;
				if(!con.contains(converter)){

					addConverter(converter,i);
					con.add(converter);
					pathFinder(x1,y1,z1,w,cab,con);
				}
			}
			
			if (te1 instanceof TEStearilliumEnergyCube) {
				TEStearilliumEnergyCube cube = (TEStearilliumEnergyCube) te1;
				if (!hasEnergyCube(cube) && (cube).canInteractWith(te) && cube.canConnectOnSide(i ^ 1)) {
					addEnergyCube(cube);
					continue;
				}

			}
			
			if (te1 instanceof TEStearilliumReactor) {
				TEStearilliumReactor reactor = (TEStearilliumReactor) te1;
				if (reactor.getCoreBlock()!=null && !hasReactor(reactor.getCoreBlock()) && reactor.canInteractWith(te)) {
					addReactorCore(reactor.getCoreBlock());
					continue;
				}
			}
			
			if (te1 instanceof TEZoroController) {
				if (!isController(w, x1, y1, z1)) {
					((TEZoroController) te1).breakController();
				}
			}
		}

	}

	public void removeCable( TECableBase te ) {
		if(cablesArray.containsKey(te) && cablesArray.get(te).intValue() == 2){
			cablesArray.put(te, 1);
			System.out.println("remove cable!");
		}
	}

	public void removeController( World w, int x, int y, int z ) {

		if (this.isController(w, x, y, z)) {
			masterController = null;
		}
		removeGrid();
	}

	public void removeConverter( TETriniumConverter te ) {
		converterArray.remove(te);
		if(converterArray.containsKey(te) && converterArray.get(te).intValue() == 2){
			converterArray.put(te, 1);
			System.out.println("removed converter!");
		}
	}

	public void removeEnergyCube( TEStearilliumEnergyCube te ) {
		
		if(energyCubeArray.containsKey(te) && energyCubeArray.get(te).intValue() == 2){
			energyCubeArray.put(te, 1);
			energystorage--;
			System.out.println("removed cube!");
		}
	}
	
	public void removeReactor( TEStearilliumReactor te ) {
		
		if(reactorArray.containsKey(te) && reactorArray.get(te).intValue() == 2){
			reactorArray.put(te, 1);
			System.out.println("removed reactor!");
		}
	}

	public void removeGrid() {
		cablesArray.clear();
		machineArray.clear();
		energyCubeArray.clear();
		converterArray.clear();
		storageArray.clear();
		energystorage = 0;
		masterController = null;
		reactorArray.clear();

		System.out.println("grid was removed!");
	}

	public void removeMachine( TEPoweredBase te ) {
		machineArray.remove(te);
		cleanupneeded();
	}

	public void removeStorage( TEPoweredBase te ) {
		storageArray.remove(te);

		for (TEZoroChest te1 : storageArray) {
			te1.gridindex = -1;
		}

		cleanupneeded();
	}

	public void setController( TEZoroController te ) {
		removeGrid();
		masterController = te;
		te.gridindex = gridIndex;
	}

	public void setEnergyStored( float power ) {
		Power = power;
		
		if (Power > this.getMaxEnergy()) {
			Power = this.getMaxEnergy();
		}
	}

	public void setMaxEnergy( float amount ) {
		maxPower = amount;
	}

	public void subtractPower( float quantity ) {

		Power -= quantity;

		if (Power < 0.0F) {
			Power = 0.0F;
		}
	}

	public void update() {

		discover();
		energystorage = countEnergyCubes();
		WorkOutMaxPower();
	}

	public void WorkOutMaxPower() {
		ClientMaxPower = maxPower + (energystorage * energyCubeIncrease);
	}

	public void writeToNBT( NBTTagCompound data ) {

		data.setFloat("grid.power", getEnergyStored());
	}
}
