import java.lang.reflect.Method;

public class Task3 {
    public static void main(String[] args) {
        try {
            Class<?> clazz = MathOperations.class;
            Method method = clazz.getDeclaredMethod("calculate", double.class);
            MathOperations obj = new MathOperations();
            double result = (double) method.invoke(obj, 1.0);
            System.out.println("Результат виклику: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MathOperations {
    public double calculate(double a) {
        return Math.sin(a);
    }
}
