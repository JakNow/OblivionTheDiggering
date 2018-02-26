package pl.oblivion.scene;

import org.apache.log4j.Logger;
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
    protected Transform transform;
    protected Transform worldTransform;

    private GameObject parent;
    private List<GameObject> children = new ArrayList<>();

    private boolean requiresUpdates = true;

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

    private GameObject getParent() {
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
        if (this.transform!=null && gameObject.transform != null) {
            gameObject.transform.updateToParent(this);
        }
    }

    public int detachChild(GameObject gameObject) {
        if (gameObject.getParent() == this) {
            int index = children.lastIndexOf(gameObject);
            if (index != -1) {
                children.remove(index);
            }
            return index;
        }
        return -1;
    }


    public Class getClassType() {
        return classType;
    }

    public List<GameObject> getChildren() {
        return children;
    }

    public Transform getTransform(){
        return transform;
    }

    public Transform getWorldTransform(){
        return worldTransform;
    }
}
