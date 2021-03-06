package cyano.mineralogy.worldgen;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;
import cyano.mineralogy.Mineralogy;

public class StoneReplacer implements IWorldGenerator {

	private Geology geom = null;
	
	public StoneReplacer(){
		//
	}
	
	private final Lock glock = new ReentrantLock(); 
	/** is thread-safe */
	final Geology getGeology(World w){
		if(geom == null){
			glock.lock();
			try{
				if(geom == null){
					geom = new Geology(w.getSeed(),Mineralogy.GEOME_SIZE,Mineralogy.ROCK_LAYER_NOISE);
				}
			}finally{
				glock.unlock();
			}
		}
		return geom;
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		getGeology(world).replaceStoneInChunk(chunkX, chunkZ, world);
	}

}
