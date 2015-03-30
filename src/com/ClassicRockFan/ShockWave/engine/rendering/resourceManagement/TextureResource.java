package com.ClassicRockFan.ShockWave.engine.rendering.resourceManagement;


import com.ClassicRockFan.ShockWave.engine.administrative.ReferenceCounter;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class TextureResource {

    private int id = -1;
    private ReferenceCounter refCount;
    private int width;
    private int height;
    private int frameBuffer = -1;

//    public TextureResource(int width, int height, char[] buffer, int filter, int attachment) {
//        this.refCount = 1;
//        this.width = width;
//        this.height = height;
//
//        initTextures(buffer, filter, 1);
//        initRenderTargets(attachment);
//    }

    public TextureResource(int width, int height, ByteBuffer buffer, int filter, int attachment) {
        this.refCount = new ReferenceCounter();
        this.width = width;
        this.height = height;

        initTextures(buffer, filter, 1);
        initRenderTargets(attachment);
    }

    @Override
    protected void finalize() {
        if (id != -1) {
            glDeleteTextures(id);
            glDeleteBuffers(id);
        }
        if (id != -1)
            glDeleteBuffers(frameBuffer);
    }

    public void initRenderTargets(int attachment) {
        if (attachment == 0)
            return;
        if (frameBuffer == -1) {
            frameBuffer = glGenFramebuffers();
            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, frameBuffer);
        }
        glFramebufferTexture(GL_DRAW_FRAMEBUFFER, attachment, id, 0);

        if (attachment == GL_DEPTH_ATTACHMENT)
            glDrawBuffer(GL_NONE);
        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            System.err.println("Framebuffer creation has failed");
            new Exception().printStackTrace();
            System.exit(1);
        }
        glViewport(0, 0, width, height);
        glDrawBuffer(GL_FRAMEBUFFER);
    }

    private void initTextures(ByteBuffer data, int filter, int wrapMode) {
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapMode);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapMode);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

        this.id = textureID;
    }

//    private void initTextures(char[] data, int filter, int wrapMode) {
//        int textureID = glGenTextures();
//
//            glBindTexture(GL_TEXTURE_2D, textureID);
//
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapMode);
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapMode);
//
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
//
//            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, );
//
//
//        int depthRenderBuffer = glGenRenderbuffers();
//        glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBuffer);
//        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
//        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderBuffer);
//
//        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, textureID, 0);
//        int[] DrawBuffer = new int[]{};
//        DrawBuffer[1] = GL_COLOR_ATTACHMENT0;
//
//        glDrawBuffer(DrawBuffer[1]);
//
//        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE)
//            return;
//
//        bindAsRenderTarget();
//
//        this.id = textureID;
//    }

    public ReferenceCounter getReferenceCounter(){return this.refCount;}

    public void bindAsRenderTarget() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, frameBuffer);
        glViewport(0, 0, width, height);
    }


    public int getID() {
        return id;
    }
}
