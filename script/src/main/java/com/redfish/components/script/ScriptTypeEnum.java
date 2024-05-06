package com.redfish.components.script;

/**
 * script类型
 */
public enum ScriptTypeEnum {

    /**
     * python
     */
    PYTHON("python","python"),

    JAVA_SCRIPT("javascript","javascript"),

    GROOVY("groovy","groovy")
    ;

    /**
     * 编码
     */
    private final String type;

    /**
     * 名称
     */
    private final String engineName;

    ScriptTypeEnum(String type, String engineName) {
        this.type = type;
        this.engineName = engineName;
    }

    public String getType() {
        return type;
    }

    public String getEngineName() {
        return engineName;
    }
}
