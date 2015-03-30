package com.ClassicRockFan.ShockWave.engine.rendering;

import com.base.engine.core.Util;
import com.base.engine.rendering.resourceManagement.TextureResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.*;

public class Texture {

    private static HashMap<String, TextureResource> loadedTextures = new HashMap<String, TextureResource>();
    private static int lastWriteBind = 0;
    private TextureResource resource;
    private String fileName;
    private int width, height;
    private int frameBuffer;

    public Texture(String fileName) {
        this.fileName = fileName;
        TextureResource oldResource = loadedTextures.get(fileName);
        if (oldResource != null) {
            resource = oldResource;
            resource.getReferenceCounter().addReference();
        } else {
            resource = loadTexture(fileName);
            loadedTextures.put(fileName, resource);
        }

    }

//    public Texture(String name, int width, int height, char[] data, int filter, int attachment) {
//        this.fileName = name;
//        initRenderTarget(attachment, true);
//
//        ByteBuffer buffer = Util.createByteBuffer(height * width * 4000);
//        //boolean hasAlpha = image.getColorModel().hasAlpha();
//        //int[] pixels = data.getRGB(0, 0, width, height, null, 0, width);
//
//
//
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
////                int pixel = pixels[y * width + x];
////
//                buffer.put((byte) ((data >> 16) & 0xFF));
//                buffer.put((byte) ((data >> 8) & 0xFF));
//                buffer.put((byte) ((data) & 0xFF));
////                if (hasAlpha)
////                    buffer.put((byte) ((pixel >> 24) & 0xFF));
////                else
////                    buffer.put((byte) (0xFF));
//
//            }
//        }
//        buffer.flip();
//
//
//        resource = new TextureResource(width, height, buffer, filter, attachment);
//    }

    public static void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static void unbindRenderTarget() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, Window.getWidth(), Window.getHeight());
    }

    private void initRenderTarget(int attachment, boolean bind) {
        int FramebufferName = 0;
        glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, FramebufferName);

        if (attachment == GL_DEPTH_ATTACHMENT)
            glDrawBuffer(GL_NONE);
        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            System.err.println("Shadow framebuffer creation has failed");
            new Exception().printStackTrace();
            System.exit(1);
        }
        if (bind) {
            lastWriteBind = frameBuffer;
            glViewport(0, 0, width, height);
        } else
            glBindFramebuffer(GL_FRAMEBUFFER, lastWriteBind);
    }

    public void bindAsRenderTarget() {
        resource.bindAsRenderTarget();
    }

    private TextureResource loadTexture(String fileName) {
        try {
            BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

            ByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);
            boolean hasAlpha = image.getColorModel().hasAlpha();

            this.width = image.getWidth();
            this.height = image.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = pixels[y * image.getWidth() + x];

                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) ((pixel) & 0xFF));
                    if (hasAlpha)
                        buffer.put((byte) ((pixel >> 24) & 0xFF));
                    else
                        buffer.put((byte) (0xFF));

                }
            }
            buffer.flip();

            TextureResource res = new TextureResource(image.getWidth(), image.getHeight(), buffer, GL_LINEAR, GL_COLOR_ATTACHMENT0);

//            glBindTexture(GL_TEXTURE_2D, res.getID());
//            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
//
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
//
//            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//
//            glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
//
//            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);


            return res;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    @Override
    protected void finalize() {
        if (resource.getReferenceCounter().removeReference() && !fileName.isEmpty()) {
            loadedTextures.remove(fileName);
        }
    }

    public void bind(int samplerSlot) {
        assert (samplerSlot >= 0 && samplerSlot <= 31);
        glActiveTexture(GL_TEXTURE0 + samplerSlot);
        glBindTexture(GL_TEXTURE_2D, resource.getID());
    }

    public int getID() {
        return resource.getID();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
