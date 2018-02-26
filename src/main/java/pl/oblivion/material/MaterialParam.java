package pl.oblivion.material;

import pl.oblivion.export.Saveable;

public class MaterialParam implements Cloneable, Saveable {

    protected MaterialDataType type;
    protected Object value;

    public MaterialParam(MaterialDataType tye, Object value) {
        this.type = type;
        this.value = value;
    }

    public MaterialDataType getType() {
        return type;
    }

    public void setType(MaterialDataType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
