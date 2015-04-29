package com.ClassicRockFan.ShockWave.engine.rendering;


import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.ARBFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.util.glu.GLU.gluErrorString;

public class Shadows {

    private int id;
    private int renderBuffer;

    public Shadows generateFBO(){
        final int MAX_RENDERBUFFER_SIZE = glGetInteger(GL_MAX_RENDERBUFFER_SIZE);
        final int MAX_TEXTURE_SIZE = glGetInteger(GL_MAX_TEXTURE_SIZE);

        int shadowMapWidth, shadowMapHeight;
        if (MAX_TEXTURE_SIZE > 1024) {
            if (MAX_RENDERBUFFER_SIZE < MAX_TEXTURE_SIZE) {
                shadowMapWidth = shadowMapHeight = MAX_RENDERBUFFER_SIZE;
            } else {
                shadowMapWidth = shadowMapHeight = 1024;
            }
        } else {
            shadowMapWidth = shadowMapHeight = MAX_TEXTURE_SIZE;
        }
        // Generate and bind a frame buffer.
        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        // Generate and bind a render buffer.
        renderBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
        // Set the internal storage format of the render buffer to a depth component of 32 bits (4 bytes).
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, shadowMapWidth, shadowMapHeight);
        // Attach the render buffer to the frame buffer as a depth attachment. This means that, if the frame buffer is
        // bound, any depth texture values will be copied to the render buffer object.
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, renderBuffer);
        // OpenGL shall make no amendment to the colour or multisample buffer.
        glDrawBuffer(GL_NONE);
        // Disable the colour buffer for pixel read operations (such as glReadPixels or glCopyTexImage2D).
        glReadBuffer(GL_NONE);
        // Check for frame buffer errors.
        int FBOStatus = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        if (FBOStatus != GL_FRAMEBUFFER_COMPLETE) {
            System.err.println("Framebuffer error: " + gluErrorString(glGetError()));
        }
        // Bind the default frame buffer, which is used for ordinary drawing.
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        return this;
    }

    public Shadows bind(int textureWidth, int textureHeight){
        EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, id);
        GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
        GL11.glViewport( 0, 0, textureWidth, textureHeight );
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        return this;
    }

}
