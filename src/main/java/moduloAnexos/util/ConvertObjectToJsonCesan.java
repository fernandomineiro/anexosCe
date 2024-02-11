package moduloAnexos.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import moduloAnexos.util.customAnnotation.DateTimeFormatCesan;
import moduloAnexos.util.customAnnotation.JsonCesanNotSerializable;



public class ConvertObjectToJsonCesan {

public static String execute(Object obj) {
		
		Class<?> c = obj.getClass();
		
		Field[] fields = c.getDeclaredFields();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("{");
		
		for (Field field : fields) {
			
			if(!field.isAnnotationPresent(JsonCesanNotSerializable.class)) {
				
				try {
					
					if(stringBuilder.length() > 1) {
						stringBuilder.append(", ");
					}
					
					field.setAccessible(true);
					
					Object fieldValue = field.get(obj);
					
					if(field.isAnnotationPresent(DateTimeFormatCesan.class) && fieldValue != null) {
						
						DateTimeFormatCesan f = field.getAnnotation(DateTimeFormatCesan.class);
						
						SimpleDateFormat sdf = new SimpleDateFormat(f.formatString()); 
						
						fieldValue = sdf.format(fieldValue);
					}
					
					stringBuilder.append("\""+field.getName()+"\":" + (fieldValue != null ? "\""+fieldValue+"\"" : "\"\""));
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		stringBuilder.append("}");
		
		return stringBuilder.toString();		
	}
}
