import java.lang.reflect.*;

public class Task1 {

    public static void printClassInfo(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            System.out.println("Type Java class full name (for example " + className + ")");
            System.out.println("-> " + clazz.getName());
            Package packageInfo = clazz.getPackage();
            System.out.println("package " + packageInfo.getName() + ", " + packageInfo.getSpecificationTitle() +
                    ", version " + packageInfo.getSpecificationVersion());
            int modifiers = clazz.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " class " + clazz.getSimpleName());

            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                System.out.print(" implements ");
                for (int i = 0; i < interfaces.length; i++) {
                    System.out.print(interfaces[i].getSimpleName());
                    if (i < interfaces.length - 1) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println(" {\n");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() +
                        " " + field.getName() + ";");
            }
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                System.out.println("\n" + Modifier.toString(constructor.getModifiers()) + " " +
                        clazz.getSimpleName() + "(");
                Parameter[] parameters = constructor.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    System.out.print(parameters[i].getType().getSimpleName() + " " + parameters[i].getName());
                    if (i < parameters.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println(");");
            }
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("\n" + Modifier.toString(method.getModifiers()) + " " +
                        method.getReturnType().getSimpleName() + " " + method.getName() + "(");
                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    System.out.print(parameters[i].getType().getSimpleName() + " " + parameters[i].getName());
                    if (i < parameters.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println(");");
            }

            System.out.println("\n}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        printClassInfo("java.lang.String");
    }
}
