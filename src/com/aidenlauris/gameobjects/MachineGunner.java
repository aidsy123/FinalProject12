/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * MachineGunner
 * Type of enemy that shoots a machine gun 
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.items.MachineGun;
import com.aidenlauris.items.Shotgun;
import com.aidenlauris.render.SoundHelper;

public class MachineGunner extends Enemy {
	
	private long shootTime = Time.alert(20);
	private int bullets;
	private Particle part;

	public MachineGunner(float x, float y) {
		super(x, y, 50, 10, 3.5f);
		part = new Particle(x, y);
		part.setColor(Color.red);
		part.setSize(25);
		part.setSizeDecay(25);
		part.setFadeMinimum(255);
		part.setRotationSpeed(5);
		part.setLifeSpan(Integer.MAX_VALUE);
		part.init();
	}
	
	
	@Override
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));

		  
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random()*360));
			f.setId("Machine");
			f.setLifeSpan(60);
			f.setReduction(0f);
			if (getForceSet().getForce("Machine") == null){
			addForce(f);
			}
			
	}
	
	public void update() {
		part.x = x;
		part.y = y;

		tickUpdate();

		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		if (isStunned()) {
			return;
		}
		
		move();
		
		
		if (dist < 500 && Time.alertPassed(shootTime) && bullets < 4) {
			attack();
			bullets++;
			shootTime = Time.alert((long)7);
		}else if(bullets > 3 && dist < 500 && Time.alertPassed(shootTime)) {
			bullets = 0;
			shootTime = Time.alert((long) (150 + Math.random()*30));
		}


	}
	
	
	public void attack(){
		Bullet b = new Bullet(15);
		b.x = this.x;
		b.y = this.y;
		Player p = Player.getPlayer();
		b.setMoveSpeed(6);
		b.setLifeSpan(180f);
		b.setGunOffset(50);
		b.setKnockback(0);
		b.team = team.ENEMY;
		this.team = team.ENEMY;
		float theta = (float) Math.atan2(p.y - this.y, p.x - this.x);
		b.setTheta(theta);
		b.init();
			
		SoundHelper.makeSound("machine.wav");
		
	}
	
	@Override
	public void kill() {

		part.kill();
		super.kill();
	}

}
