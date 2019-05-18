package minicraft.level.tile;

import minicraft.entity.Direction;
import minicraft.entity.mob.Mob;
import minicraft.entity.mob.Player;
import minicraft.gfx.Color;
import minicraft.gfx.Screen;
import minicraft.gfx.Sprite;
import minicraft.item.Item;
import minicraft.item.Items;
import minicraft.level.Level;

public class SaplingTile extends Tile {
	private static Sprite sprite = new Sprite(11, 3, Color.get(20, 40, 50, -1));
	
	private Tile onType;
	private Tile growsTo;
	
	protected SaplingTile(String name, Tile onType, Tile growsTo) {
		super(name, sprite);
		this.onType = onType;
		this.growsTo = growsTo;
		connectsToSand = onType.connectsToSand;
		connectsToGrass = onType.connectsToGrass;
		connectsToWater = onType.connectsToWater;
		connectsToLava = onType.connectsToLava;
		maySpawn = true;
	}

	public void render(Screen screen, Level level, int x, int y) {
		onType.render(screen, level, x, y);
		
		sprite.render(screen, x*16, y*16);
	}

	public void tick(Level level, int x, int y) {
		int age = level.getData(x, y) + 1;
		if (age > 100) {
			level.setTile(x, y, growsTo);
		} else {
			level.setData(x, y, age);
		}
	}

	public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
		level.setTile(x, y, onType);
		return true;
	}

	@Override
	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item.equals(Items.get("Cloud Dust"))) {
			level.setTile(xt, yt, Tiles.get("Tree"));
			return true;
		} else {
			return this.hurt(level, xt, yt, player, 1, attackDir);
		}
	}
}
