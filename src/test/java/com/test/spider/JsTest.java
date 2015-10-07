package com.test.spider;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Test;

public class JsTest {
	
	/**
	 * 执行本地js中的函数
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine engine = scriptEngineManager.getEngineByExtension("js");
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("d:\\test.js")));
		engine.eval(bufferedReader);
		
		Invocable invocable = (Invocable)engine;
		Object result = invocable.invokeFunction("getNum", "3");
		System.out.println(result);
	}
	
	/**
	 * 执行在线js中方法
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine engine = scriptEngineManager.getEngineByExtension("js");
		
		URL url = new URL("http://aaa.com/a.js");
		InputStream inputStream = url.openStream();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		engine.eval(bufferedReader);
		
		Invocable invocable = (Invocable)engine;
		Object result = invocable.invokeFunction("getNum", "3");
		System.out.println(result);
		
	}

}
