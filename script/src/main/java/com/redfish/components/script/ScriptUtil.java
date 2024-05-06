package com.redfish.components.script;

import javax.script.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 该ScriptUtil中的所有相关的Script类均为单例，共享使用。
 * 共享Script单例类不会更改里面的属性，也不会设置共享变量。
 */
public class ScriptUtil {

    private static final ScriptEngineManager SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();

    private static final Map<String,ScriptEngine> TYPE_2_ENGINE = new ConcurrentHashMap<>();

    private static final Map<String, CompiledScript> SCRIPT_2_COMPILED = new ConcurrentHashMap<>();

    private static ScriptEngine getEngine(ScriptTypeEnum scriptType){
        if (null == scriptType){
            return null;
        }
        ScriptEngine scriptEngine = TYPE_2_ENGINE.get(scriptType.getEngineName());
        if (null == scriptEngine){
            scriptEngine = SCRIPT_ENGINE_MANAGER.getEngineByName(scriptType.getEngineName());
            TYPE_2_ENGINE.put(scriptType.getEngineName(), scriptEngine);
        }

        return scriptEngine;
    }


    private static CompiledScript getCompiledScript(ScriptTypeEnum scripType,String script){
        if (null == script){
            return null;
        }
        ScriptEngine scriptEngine = getEngine(scripType);
        if (null == scriptEngine){
            return null;
        }
        if (!(scriptEngine instanceof Compilable)){
            return null;
        }

        CompiledScript compiledScript = SCRIPT_2_COMPILED.get(script);
        if (null == compiledScript){
            try {
                compiledScript = ((Compilable) scriptEngine).compile(script);
                SCRIPT_2_COMPILED.put(script, compiledScript);
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        }
        return compiledScript;
    }


    public static Object runScript(ScriptTypeEnum scriptType,String script,Map<String,Object> context){
        ScriptEngine scriptEngine = getEngine(scriptType);
        if (null == scriptEngine){
            return null;
        }


        Bindings bindings = scriptEngine.createBindings();
        bindings.putAll(context);

        // 如果是可编译脚本，则直接运行
        CompiledScript compiledScript = getCompiledScript(scriptType,script);
        if (null != compiledScript){
            try {
                return compiledScript.eval(bindings);
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        }else {
            // 如果是不可编译脚本，则直接执行
            try {
                return scriptEngine.eval(script,bindings);
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        }


    }


}
