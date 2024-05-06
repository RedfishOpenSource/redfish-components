package com.redfish.components.script.demo;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class DemoTest {


    @Test
    public void demo() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        scriptEngine.eval("print('Hello ,Scripting World!')");

    }

}

