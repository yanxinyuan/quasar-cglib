package com.xy.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

public class CglibUtil {
	
	public static Object generate(Class<?> iClass) throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(iClass);
		enhancer.setCallbackFilter(new CallbackFilter () {
			@Override
			public int accept(Method method) {
				List<Method> methods = Arrays.asList(iClass.getDeclaredMethods());
				int num = methods.contains(method) ? 0 : 1;
				return num;
			}
			
		});
		enhancer.setCallbacks(new Callback[] { new MethodInterceptor () {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws SuspendExecution {
				for (int i = 0; i < 10; i++) {
					System.out.println(i);
					Fiber.park();
				}
				return null;
			}
			
		}, NoOp.INSTANCE });
		Object obj = enhancer.create();
		return obj;
	}
	
}
