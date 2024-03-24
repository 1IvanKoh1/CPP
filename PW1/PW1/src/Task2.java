import java.lang.reflect.*;
import java.util.*;

class ObjectHandler {
    public static String objectToString(Object obj) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isPublic(field.getModifiers())) {
                continue;
            }
            Object value = field.get(obj);
            sb.append(field.getName()).append(" = ").append(value.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void stringToObject(String str, Object obj) throws Exception {
        Class<?> objClass = obj.getClass();
        String[] lines = str.split("\n");
        for (String line : lines) {
            String[] parts = line.split(" = ");
            Field field = objClass.getField(parts[0]);
            if (field.getType() == double.class) {
                field.setDouble(obj, Double.parseDouble(parts[1]));
            } else if (field.getType() == int.class) {
                field.setInt(obj, Integer.parseInt(parts[1]));
            }
        }
    }

    public static int printMethods(Object obj) {
        Class<?> objClass = obj.getClass();
        Method[] methods = objClass.getMethods();
        int count = 0;
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && Modifier.isPublic(method.getModifiers())) {
                count++;
                System.out.println(count + "). " + method);
                if (count == 6) {
                    break;
                }
            }
        }
        return count;
    }

    public static void invokeMethod(Object obj, int methodNumber) throws Exception {
        Class<?> objClass = obj.getClass();
        Method[] methods = objClass.getMethods();
        int count = 0;
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && Modifier.isPublic(method.getModifiers())) {
                count++;
                if (count == methodNumber) {
                    Object result = method.invoke(obj);
                    System.out.println("Результат виклику методу: " + result);
                    break;
                }
            }
        }
    }
}

class TestClass {
    public double x = 3.0;
    public double y = 4.0;

    public double Dist() {
        return Math.sqrt(x * x + y * y);
    }

    public void setRandomData() {
        Random rand = new Random();
        x = rand.nextDouble() * 10;
        y = rand.nextDouble() * 10;
    }

    public void setData(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Створення об'єкту...");
            System.out.println("Стан об'єкту:");
            System.out.println(ObjectHandler.objectToString(obj));
            System.out.println("Виклик методу...");
            System.out.println("Список відкритих методів:");
            int methodCount = ObjectHandler.printMethods(obj);
            System.out.println("Введіть порядковий номер методу [1 ," + methodCount + "]:");
            int methodNumber = scanner.nextInt();
            ObjectHandler.invokeMethod(obj, methodNumber);
        }
    }
}
