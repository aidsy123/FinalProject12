package com.aidenlauris.render;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;

public class PaintHelper {
	public static Font font;

	public static float x(double d) {
		return WorldMap.getCamera().relX(d);
	}

	public static float y(double d) {
		return WorldMap.getCamera().relY(d);
	}

	public static Graphics2D drawCollisionBox(Graphics2D g2d, CollisionBox box) {


		float drawX, drawY;
		drawX = PaintHelper.x(box.getLeft());
		drawY = PaintHelper.y(box.getTop());
		if (box instanceof HurtBox) {
			g2d.setColor(Color.blue);
		}
		if (box instanceof HitBox) {
			g2d.setColor(Color.darkGray);
		}
		Shape s = new Rectangle2D.Float(drawX, drawY, box.wid, box.len);
		g2d.draw(s);
		return g2d;
	}

	public static Graphics2D drawCollisionBox(Graphics2D g2d, ArrayList<CollisionBox> boxes) {
		for(CollisionBox box : boxes) {
			g2d = drawCollisionBox(g2d, box);
		}
		return g2d;
	}
	
	public static Graphics2D drawCollisionBox(Graphics2D g2d, GameObject obj) {
		return drawCollisionBox(g2d, obj.getCollisionBoxes());
	}
	
	public static void initFont() {
		try {
			// create the font to use. Specify the size!
			File[] f = new File("./Press_Start_2P").listFiles();
			System.out.println(Arrays.toString(f));
			font = Font.createFont(Font.TRUETYPE_FONT, new File(".\\Press_Start_2P\\VT323-Regular.ttf"))
					.deriveFont(24f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(font);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	}

}
