package pl.oblivion.material;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static java.lang.Math.round;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBImageResize.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.memAlloc;
import static org.lwjgl.system.MemoryUtil.memFree;
import static pl.oblivion.math.Utils.ioResourceToByteBuffer;


public class Texture {
    private static final Logger logger = Logger.getLogger(Texture.class.getName());

    private final int id;
    private final String name;

    public Texture(String name, String path) {
        this(createTexture(path), name);
    }


    private Texture(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private static int createTexture(String imagePath) {
        ByteBuffer image;
         final int width;
         final int height;
         final int composition;

        ByteBuffer imageBuffer = null;
        try {
            imageBuffer = ioResourceToByteBuffer(imagePath, 8 * 1024);
        } catch (IOException e) {
            logger.error(new RuntimeException());
        }

        try(MemoryStack stack = stackPush()){
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            if(!stbi_info_from_memory(Objects.requireNonNull(imageBuffer),w,h,comp)){
                logger.error("Failed to read image information: "+stbi_failure_reason()+"\n",new RuntimeException());
            }

            image = stbi_load_from_memory(imageBuffer,w,h,comp,0);
            if(image == null){
                logger.error("Failed to load image: "+stbi_failure_reason()+"\n",new RuntimeException());
            }

            width = w.get(0);
            height = h.get(0);
            composition = comp.get(0);
        }


        int texID = glGenTextures();


        glBindTexture(GL_TEXTURE_2D, texID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        int format = GL_RGB;

        glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, image);

        ByteBuffer input_pixel = image;

        int input_w = width;
        int input_h = height;
        int mipmapLevel = 0;
        while(1 < input_w || 1 < input_h){
            int output_w = Math.max(1,input_w>>1);
            int output_h = Math.max(1,input_h>>1);

            ByteBuffer output_pixels = memAlloc(output_w*output_h*composition);
            stbir_resize_uint8_generic(
                    Objects.requireNonNull(input_pixel),input_w,input_h,input_w*composition,
                    output_pixels,output_w,output_h,output_w*composition,
                    composition,composition == 4 ? 3 : STBIR_ALPHA_CHANNEL_NONE, STBIR_FLAG_ALPHA_PREMULTIPLIED,
                    STBIR_EDGE_CLAMP,
                    STBIR_FILTER_MITCHELL,STBIR_COLORSPACE_SRGB
            );

            if(mipmapLevel == 0){
                stbi_image_free(Objects.requireNonNull(image));
            } else{
                memFree(input_pixel);
            }

            glTexImage2D(GL_TEXTURE_2D,++mipmapLevel,format,output_w,output_h,0,format,GL_UNSIGNED_BYTE,output_pixels);

            input_pixel = output_pixels;
            input_w = output_w;
            input_h = output_h;
        }

        if(mipmapLevel == 0){
            stbi_image_free(Objects.requireNonNull(image));
        }else{
            memFree(input_pixel);
        }

        return texID;



    }

    public void bind(int unit) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void delete() {
        GL11.glDeleteTextures(id);
    }

    @Override
    public String toString() {
        return "ID=" + id + ", name=" + name;
    }



}
