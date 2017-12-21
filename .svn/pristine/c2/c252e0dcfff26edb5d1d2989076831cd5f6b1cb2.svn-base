package com.smht.service.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数注解
 *
 * @author fanhaoyu
 * @since 2015年8月6日
 */
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {

	/**
	 * 参数名，不可为空
	 * 
	 * @return
	 */
	String name();

	/**
	 * 参数中Date类型数据的格式，默认为空串，代表毫秒
	 * 
	 * @return
	 */
	String dateFormat() default "";
}
