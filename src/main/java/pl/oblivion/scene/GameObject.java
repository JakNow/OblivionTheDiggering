package pl.oblivion.scene;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.oblivion.collision.Collidable;
import pl.oblivion.export.Saveable;
import pl.oblivion.math.Transform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class GameObject implements Cloneable, Saveable, Collidable {
    private static final Logger logger = Logger.getLogger(GameObject.class.getName());
    private final Class classType;

    protected String name;
    public Transform transform;

    private GameObject parent;

    public List<GameObject> children = new ArrayList<>();


    protected GameObject(String name, Class classType) {
        this.classType = classType;
        this.name = name;
        logger.info("Created Structure \'" + name + "\'.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameObject getParent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public void addChild(GameObject gameObject) {
        if (children == null) {
            children = new LinkedList<>();
        }
        children.add(gameObject);
        gameObject.setParent(this);
        gameObject.transform.transform(this.transform);
    }

    public void detachChild(GameObject gameObject) {
        if(gameObject.getParent()==this){
            children.remove(gameObject);
            gameObject.transform.transform(this.transform.negate());
            gameObject.setParent(null);

        }
    }

    public Class getClassType() {
        return classType;
    }

    public List<GameObject> getChildren() {
        return children;
    }

    public abstract void update(float delta);

    public void updateTree(float delta){
        if(this.getParent() !=null)
        this.transform.transform(this.getParent().transform);
        update(delta);
        for(GameObject child : children){
            child.updateTree(delta);
        }
    }
}
