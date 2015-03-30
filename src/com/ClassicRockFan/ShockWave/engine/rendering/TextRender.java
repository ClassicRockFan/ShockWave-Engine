package com.ClassicRockFan.ShockWave.engine.rendering;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class TextRender {

    private int x, y;
    private TrueTypeFont font;
    private Color color;

    public TextRender(int x, int y, Color color) {
        this(x, y, color, new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), false));
    }

    public TextRender(int x, int y, Color color, TrueTypeFont font) {
        this.x = x;
        this.x = y;
        this.font = font;
        this.color = color;
    }

    public void drawText(String text){
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        font.drawString(x, y, text, color);

        glDisable(GL_BLEND);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
