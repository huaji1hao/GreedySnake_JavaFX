package com.megasnake;

import java.lang.reflect.Field;

public class TestHelperFunction {
    // Helper method to set private fields using reflection
    public static void setPrivateField(Object obj, String fieldName, Object value){
        try {
            Field field = findField(obj.getClass(), fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get private fields using reflection
    public static Object getPrivateField(Object obj, String fieldName){
        try{
            Field field = findField(obj.getClass(), fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    // Helper method to find a field in the class hierarchy
    private static Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + fieldName + " not found in " + clazz);
    }
}
