package pl.oblivion.material;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class MaterialData {

    private static final Logger logger = Logger.getLogger(MaterialData.class.getName());

    private Map<MaterialDataType, MaterialParam> materialParams;

    public MaterialData() {
        this.materialParams = new HashMap<>();
    }

    public void addMaterialParam(MaterialDataType type, Object value) {
        materialParams.put(type, new MaterialParam(type, value));
        logger.info("Putting " + type + " to materialParams with value=" + value.toString());
    }

    public void addMaterialParam(MaterialParam materialParam) {
        materialParams.put(materialParam.getType(), materialParam);
        logger.info("Putting " + materialParam.getType() + " to materialParams with value=" + materialParam.getValue().toString());
    }

    public MaterialParam getMaterialParam(MaterialDataType type) {
        return materialParams.get(type);
    }

}
