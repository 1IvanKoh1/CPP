import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.IOException;

public class ChatGUI {
    private JFrame frame;
    private JTextField textFieldMsg;
    private JTextArea textArea;
    private Messanger messanger;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                String name = JOptionPane.showInputDialog(null, "Введіть ваше ім'я користувача:", "Введення імені", JOptionPane.PLAIN_MESSAGE);
                if (name == null || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ім'я користувача не може бути порожнім.");
                    System.exit(1);
                }
                ChatGUI window = new ChatGUI(name);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ChatGUI(String name) {
        initialize(name);
    }

    private void initialize(String name) {
        frame = new JFrame("Multicast Chat");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        textFieldMsg = new JTextField();
        frame.getContentPane().add(textFieldMsg, BorderLayout.SOUTH);
        textFieldMsg.setColumns(10);

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);

        textFieldMsg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                messanger.send();
            }
        });

        try {
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            int port = 3456;
            UITasks ui = (UITasks) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{UITasks.class}, new EDTInvocationHandler(new UITasksImpl()));
            messanger = new MessangerImpl(addr, port, name, ui);
            messanger.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class UITasksImpl implements UITasks {
        public String getMessage() {
            String res = textFieldMsg.getText();
            textFieldMsg.setText("");
            return res;
        }

        public void setText(String txt) {
            textArea.append(txt + "\n");
        }
    }

    public interface Messanger {
        void start();
        void stop();
        void send();
    }

    public interface UITasks {
        String getMessage();
        void setText(String txt);
    }

    public class EDTInvocationHandler implements InvocationHandler {
        private Object invocationResult = null;
        private UITasks ui;

        public EDTInvocationHandler(UITasks ui) {
            this.ui = ui;
        }

        @Override
        public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
            if (SwingUtilities.isEventDispatchThread()) {
                invocationResult = method.invoke(ui, args);
            } else {
                Runnable shell = new Runnable() {
                    public void run() {
                        try {
                            invocationResult = method.invoke(ui, args);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                };
                SwingUtilities.invokeAndWait(shell);
            }
            return invocationResult;
        }
    }

    public class MessangerImpl implements Messanger {
        private UITasks ui;
        private MulticastSocket group;
        private InetAddress addr;
        private int port;
        private String name;
        private boolean canceled = false;

        public MessangerImpl(InetAddress addr, int port, String name, UITasks ui) {
            this.name = name;
            this.ui = ui;
            this.addr = addr;
            this.port = port;
            try {
                group = new MulticastSocket(port);
                group.setTimeToLive(2);
                group.joinGroup(addr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void start() {
            Thread t = new Receiver();
            t.start();
        }

        @Override
        public void stop() {
            cancel();
            try {
                group.leaveGroup(addr);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Помилка від'єднання\n" + e.getMessage());
            } finally {
                group.close();
            }
        }

        @Override
        public void send() {
            new Sender().start();
        }

        private class Sender extends Thread {
            public void run() {
                try {
                    String msg = name + ": " + ui.getMessage();
                    byte[] out = msg.getBytes();
                    DatagramPacket pkt = new DatagramPacket(out, out.length, addr, port);
                    group.send(pkt);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Помилка відправлення\n" + e.getMessage());
                }
            }
        }

        private class Receiver extends Thread {
            public void run() {
                try {
                    byte[] in = new byte[512];
                    DatagramPacket pkt = new DatagramPacket(in, in.length);
                    while (!isCanceled()) {
                        group.receive(pkt);
                        ui.setText(new String(pkt.getData(), 0, pkt.getLength()));
                    }
                } catch (IOException e) {
                    if (isCanceled()) {
                        JOptionPane.showMessageDialog(null, "З'єднання завершено");
                    } else {
                        JOptionPane.showMessageDialog(null, "Помилка прийому\n" + e.getMessage());
                    }
                }
            }
        }

        private synchronized boolean isCanceled() {
            return canceled;
        }

        public synchronized void cancel() {
            canceled = true;
        }
    }
}
