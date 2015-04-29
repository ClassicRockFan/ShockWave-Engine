package com.ClassicRockFan.ShockWave.engine.rendering;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;

public class FBOUtil{

    private int FBO;
    private int color_texture;
    private int depth_texture;

    public FBOUtil(int width, int height) {
        generateFBO(width, height);
    }

    private void generateColorTexture( int width,  int height){
        color_texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, color_texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
    }



    //generate an empty depth texture with 1 depth channel using bilinear filtering
    private void generateDepthTexture( int width,  int height){
        depth_texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, depth_texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT24, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);

    }

    public void generateFBO(int width, int height){
        FBO = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, FBO);

        generateColorTexture(width, height);
        generateDepthTexture(width, height);

        int attachment_index_color_texture = 0;
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + attachment_index_color_texture, color_texture, color_texture, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depth_texture, depth_texture, 0);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE){
            System.out.println("FrameBuffer " + depth_texture + " not complete!");
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    //return color texture from the framebuffer
     public int getColorTexture(){
        return color_texture;
    }

    //return depth texture from the framebuffer
     public int getDepthTexture(){
        return depth_texture;
    }

    //bind framebuffer to pipeline. We will  call this method in the render loop
    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER, FBO);
    }

    void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

}
