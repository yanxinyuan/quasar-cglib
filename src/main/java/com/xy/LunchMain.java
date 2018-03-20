package com.xy;

import java.lang.reflect.Method;

import com.xy.util.CglibUtil;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;

public class LunchMain {

	public static void main(String[] args) throws SuspendExecution, InterruptedException, Exception {
		final Object obj = CglibUtil.generate(IXxx.class);
		Fiber<Void> fiber = new Fiber<Void>() {
			
			private static final long serialVersionUID = -4354858673853940421L;

			@Override
			protected Void run() throws SuspendExecution, InterruptedException {
				try {
					Method method = obj.getClass().getMethod("yyy");
					method.setAccessible(true);
					method.invoke(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
		        return null;
			}
			
		}.start();
		
		while(!fiber.isDone()) {
			Strand.sleep(2000);
		    fiber.unpark();
		}
	}

}
