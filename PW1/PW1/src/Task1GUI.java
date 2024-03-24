import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.*;

public class Task1GUI extends JFrame {
    private JTextField classNameField;
    private JTextArea classInfoArea;
    private JButton analyzeButton, clearButton, finishButton;

    public Task1GUI() {
        classNameField = new JTextField(20);
        classInfoArea = new JTextArea(10, 30);
        analyzeButton = new JButton("Аналіз");
        clearButton = new JButton("Очистити");
        finishButton = new JButton("Завершити");

        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        northPanel.add(new JLabel("Введіть повне ім'я класу:"));
        northPanel.add(classNameField);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(new JScrollPane(classInfoArea), BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());
        southPanel.add(analyzeButton);
        southPanel.add(clearButton);
        southPanel.add(finishButton);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String className = classNameField.getText();
                String classInfo = printClassInfo(className);
                classInfoArea.setText(classInfo);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classNameField.setText("");
                classInfoArea.setText("");
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static String printClassInfo(String className) {
        StringBuilder classInfo = new StringBuilder();
        try {
            Class<?> clazz = Class.forName(className);
            classInfo.append("Type Java class full name (for example " + className + ")\n");
            classInfo.append("-> " + clazz.getName() + "\n");

            Package packageInfo = clazz.getPackage();
            classInfo.append("package " + packageInfo.getName() + ", " + packageInfo.getSpecificationTitle() +
                    ", version " + packageInfo.getSpecificationVersion() + "\n");

            int modifiers = clazz.getModifiers();
            classInfo.append(Modifier.toString(modifiers) + " class " + clazz.getSimpleName());

            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                classInfo.append(" implements ");
                for (int i = 0; i < interfaces.length; i++) {
                    classInfo.append(interfaces[i].getSimpleName());
                    if (i < interfaces.length - 1) {
                        classInfo.append(", ");
                    }
                }
            }
            classInfo.append(" {\n");

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                classInfo.append(Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() +
                        " " + field.getName() + ";\n");
            }

            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                classInfo.append("\n" + Modifier.toString(constructor.getModifiers()) + " " +
                        clazz.getSimpleName() + "(");
                Parameter[] parameters = constructor.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    classInfo.append(parameters[i].getType().getSimpleName() + " " + parameters[i].getName());
                    if (i < parameters.length - 1) {
                        classInfo.append(", ");
                    }
                }
                classInfo.append(");\n");
            }

            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                classInfo.append("\n" + Modifier.toString(method.getModifiers()) + " " +
                        method.getReturnType().getSimpleName() + " " + method.getName() + "(");
                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    classInfo.append(parameters[i].getType().getSimpleName() + " " + parameters[i].getName());
                    if (i < parameters.length - 1) {
                        classInfo.append(", ");
                    }
                }
                classInfo.append(");\n");
            }

            classInfo.append("\n}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classInfo.toString();
    }

    public static void main(String[] args) {
        Task1GUI gui = new Task1GUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.pack();
        gui.setVisible(true);
    }
}
