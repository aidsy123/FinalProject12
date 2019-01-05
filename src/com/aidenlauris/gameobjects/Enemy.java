package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.ShotgunAmmo;
import com.aidenlauris.render.PaintHelper;

public class Enemy extends Entity {

	private boolean isHovering;

	public Enemy(int x, int y, int health, int strength, float speed) {
		super(x, y, 1, 1);

		addCollisionBox(new HitBox(this, -12.5f, -12.5f, 25, 25, true));

		this.health = health;
		this.maxHealth = health;
		setMoveSpeed(speed);
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if (box instanceof HurtBox && box.getOwner() instanceof Entity) {
			Entity owner = (Entity) box.getOwner();
		}
		if (box.isSolid) {
			collide(box, myBox);
		}

	}

	public void update() {
		tickUpdate();
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		if (isStunned()) {
			return;
		}
		move();

		if (dist < 80) {
			attack();
		}

	}

	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		if (dist < 300) {
			ForceAnchor f = new ForceAnchor(3f, this, WorldMap.getPlayer(), -1);

			f.setId("PlayerFollow");
			f.hasVariableSpeed(false);
			if (getForceSet().getForce("PlayerFollow") == null) {
				getForceSet().removeForce("Random");
				getForceSet().addForce(f);
				// System.out.println("Running Attack");
			}
		} else {
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
			f.setId("Random");
			f.setLifeSpan(60);
			f.setReduction(0f);
			if (getForceSet().getForce("Random") == null) {
				getForceSet().removeForce("PlayerFollow");
				getForceSet().addForce(f);
			}
		}
	}

	public void attack() {

	}

	@Override
	public void kill() {

		ItemDropEntity.drop(x, y, new BulletAmmo(1), 0.2, 4, 10);
		ItemDropEntity.drop(x, y, new ShotgunAmmo(1), 0.05, 2, 3);
		ItemDropEntity.drop(x, y, new ExplosiveAmmo(1), 0.05, 1, 1);
		
		WorldMap.addGameObject(new Corpse(x, y, this));

		removeSelf();
	}




	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX, drawY;
		drawX = PaintHelper.x(x);
		drawY = PaintHelper.y(y);

		g2d = super.draw(g2d);

		return g2d;
	}
}