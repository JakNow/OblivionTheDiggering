package pl.oblivion.math;

import org.joml.Quaternionf;

public class Quaternion extends Quaternionf {

    private final float min = 0;
    private final float max = (float) (2*Math.PI);
    public Quaternion(float x, float y, float z){
        super((float)Math.toRadians(x),(float)Math.toRadians(y),(float)Math.toRadians(z));
    }

    public Quaternion(){
        super(0,0,0,1.0F);
    }



    public Quaternion(Quaternion quaternion){
        super(quaternion);
    }

    public void add(float dx, float dy, float dz){
        addX(dx);
        addY(dy);
        addZ(dz);
    }

    public void addX(float dx){
        this.x += (float) Math.toRadians(dx);
        this.x = Maths.keepValue(this.x,min,max);
    }

    public void addY(float dy){
        this.y += (float) Math.toRadians(dy);
        this.y = Maths.keepValue(this.y,min,max);
    }

    public void addZ(float dz){
        this.z += (float) Math.toRadians(dz);
        this.z = Maths.keepValue(this.z,min,max);
    }

    public void add(Quaternion quaternion){
        this.x += quaternion.x;
        this.x = Maths.keepValue(this.x,min,max);

        this.y += quaternion.y;
        this.y = Maths.keepValue(this.y,min,max);

        this.z += quaternion.z;
        this.z = Maths.keepValue(this.z,min,max);
    }


    public Quaternion set(float dx, float dy, float dz){
        this.x = (float) Math.toRadians(dx);
        this.y = (float) Math.toRadians(dy);
        this.z = (float) Math.toRadians(dz);
        return this;
    }

    public Quaternion set(float dx, float dy, float dz, float dw){
        this.x = (float) Math.toRadians(dx);
        this.y = (float) Math.toRadians(dy);
        this.z = (float) Math.toRadians(dz);
        this.w = dw;
        return this;
    }

    public Quaternion set(Quaternion quaternion){
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;

        return this;
    }
}
