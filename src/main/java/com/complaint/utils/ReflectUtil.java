package com.complaint.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {

	/**
	 * 传入方法名称，操作的对象，操作的参数 通过反射找到该方法后 用反射执行
	 * 
	 * @param methodName
	 * @param obj
	 * @param parameterTypes
	 * @return
	 */
	public static Object invokeMethodName(String methodName, Object obj,
			Object parameterTypes) {
		Object returnobj = null;
		try {
			//如果传入的参数书null 那么执行的方法没有需要传入的参数
			if (parameterTypes == null) {
				Method method = obj.getClass().getDeclaredMethod(methodName);
				returnobj = method.invoke(obj);
			} else {
				Method method = obj.getClass().getDeclaredMethod(methodName,
						parameterTypes.getClass());
				returnobj = method.invoke(obj, parameterTypes);
			}

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnobj;
	}
}
