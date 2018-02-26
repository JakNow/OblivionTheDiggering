package pl.oblivion.math;

import org.apache.log4j.Logger;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Utils {

    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    /*
    https://github.com/LWJGL/lwjgl3-demos/blob/master/src/org/lwjgl/demo/opengl/util/DemoUtils.java
     */
    public static ByteBuffer ioResourceToByteBuffer(String resoruce, int bufferSize) throws IOException {
        ByteBuffer buffer;
        URL url = Thread.currentThread().getContextClassLoader().getResource(resoruce);
        File file = new File(url.getFile());
        if(file.isFile()){
            FileInputStream inputStream = new FileInputStream(file);
            FileChannel channel = inputStream.getChannel();
            buffer = channel.map(FileChannel.MapMode.READ_ONLY,0,channel.size());
            channel.close();
            inputStream.close();
        }else{
            buffer = BufferUtils.createByteBuffer(bufferSize);
            InputStream inputStream = url.openStream();
            if(inputStream == null){
                logger.error("Couldn't find the file at path: "+resoruce+"\n", new FileNotFoundException());
            }
            try{
                ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
                try{
                    while(true){
                        int bytes = readableByteChannel.read(buffer);
                        if(bytes == -1){
                            break;
                        }
                        if(buffer.remaining() ==0){
                            buffer = resizeBuffer(buffer, buffer.capacity() *2);
                        }
                    }
                        buffer.flip();
                } finally {
                    readableByteChannel.close();
                }
            } finally {
                inputStream.close();
            }
        }
    return buffer;
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity){
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
}
