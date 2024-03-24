import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Evaluatable {
    double eval(double x);
}

class Function1 implements Evaluatable {
    @Override
    public double eval(double x) {
        return Math.exp(-Math.abs(2.5 * x)) * Math.sin(x);
    }
}

class Function2 implements Evaluatable {
    @Override
    public double eval(double x) {
        return x * x;
    }
}

class ProfilingHandler implements InvocationHandler {
    private final Evaluatable target;

    ProfilingHandler(Evaluatable target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println(method.getName() + " took " + elapsedTime + " ns");
        return result;
    }
}

class TracingHandler implements InvocationHandler {
    private final Evaluatable target;

    TracingHandler(Evaluatable target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print("[" + method.getName());
        if (args != null) {
            for (Object arg : args) {
                System.out.print(", " + arg);
            }
        }
        System.out.print("]");
        Object result = method.invoke(target, args);
        System.out.println(" = " + result);
        return result;
    }
}

public class Task5 {
    public static void main(String[] args) {
        Evaluatable f1 = new Function1();
        Evaluatable f2 = new Function2();

        Evaluatable f1ProfilingProxy = (Evaluatable) Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new ProfilingHandler(f1)
        );

        Evaluatable f2ProfilingProxy = (Evaluatable) Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new ProfilingHandler(f2)
        );

        Evaluatable f1TracingProxy = (Evaluatable) Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new TracingHandler(f1)
        );

        Evaluatable f2TracingProxy = (Evaluatable) Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new TracingHandler(f2)
        );

        double x = 1.0;

        System.out.println("F1: " + f1ProfilingProxy.eval(x));
        System.out.println("F2: " + f2ProfilingProxy.eval(x));
        System.out.println("F1: " + f1TracingProxy.eval(x));
        System.out.println("F2: " + f2TracingProxy.eval(x));
    }
}
