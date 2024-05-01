import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "metro_cards.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MetroCardBank bank = new MetroCardBank();

        loadMetroCardsFromFile(bank);

        while (true) {
            System.out.println("Оберіть операцію:");
            System.out.println("1. Додати нову картку");
            System.out.println("2. Отримати інформацію про картку");
            System.out.println("3. Поповнити рахунок");
            System.out.println("4. Оплатити поїздку");
            System.out.println("5. Отримати залишок коштів на картці");
            System.out.println("6. Вийти з програми");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Очистка буфера

            switch (choice) {
                case 1:
                    addNewCard(scanner, bank);
                    break;
                case 2:
                    getInfo(scanner, bank);
                    break;
                case 3:
                    addMoney(scanner, bank);
                    break;
                case 4:
                    payForTrip(scanner, bank);
                    break;
                case 5:
                    getBalance(scanner, bank);
                    break;
                case 6:
                    saveMetroCardsToFile(bank);
                    System.out.println("Програма завершує роботу.");
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void loadMetroCardsFromFile(MetroCardBank bank) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String serNum = parts[0];
                String name = parts[1];
                String surName = parts[2];
                String sex = parts[3];
                String birthdayString = parts[4];
                String college = parts[5];
                double balance = Double.parseDouble(parts[6]);
                User user = new User(name, surName, sex, birthdayString);
                MetroCard card = new MetroCard(serNum, user, college, balance);
                bank.addCard(card);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    private static void saveMetroCardsToFile(MetroCardBank bank) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (MetroCard card : bank.getStore()) {
                String birthdayString = new SimpleDateFormat("dd.MM.yyyy").format(card.getUser().getBirthday());
                writer.println(card.getSerNum() + "," + card.getUser().getName() + "," + card.getUser().getSurName() +
                        "," + card.getUser().getSex() + "," + birthdayString + "," + card.getCollege() +
                        "," + card.getBalance());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addNewCard(Scanner scanner, MetroCardBank bank) {
        System.out.println("Введіть серійний номер картки:");
        String serNum = scanner.nextLine();

        System.out.println("Введіть ім'я власника:");
        String name = scanner.nextLine();

        System.out.println("Введіть прізвище власника:");
        String surName = scanner.nextLine();

        System.out.println("Введіть стать власника:");
        String sex = scanner.nextLine();

        System.out.println("Введіть дату народження власника у форматі dd.MM.yyyy:");
        String birthday = scanner.nextLine();

        System.out.println("Введіть назву навчального закладу, який видав картку:");
        String college = scanner.nextLine();

        System.out.println("Введіть початковий баланс картки:");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        User user = new User(name, surName, sex, birthday);
        MetroCard card = new MetroCard(serNum, user, college, balance);

        bank.addCard(card);
        System.out.println("Картка успішно додана!");
        saveMetroCardsToFile(bank);
    }

    private static void getInfo(Scanner scanner, MetroCardBank bank) {
        System.out.println("Введіть серійний номер картки:");
        String serNum = scanner.nextLine();

        int index = bank.findMetroCard(serNum);
        if (index != -1) {
            System.out.println(bank.getStore().get(index));
        } else {
            System.out.println("Картку з таким серійним номером не знайдено.");
        }
    }

    private static void addMoney(Scanner scanner, MetroCardBank bank) {
        System.out.println("Введіть серійний номер картки:");
        String serNum = scanner.nextLine();

        System.out.println("Введіть суму для поповнення:");
        double money = scanner.nextDouble();
        scanner.nextLine();

        if (bank.addMoney(serNum, money)) {
            System.out.println("Рахунок успішно поповнено!");
        } else {
            System.out.println("Не вдалося поповнити рахунок. Перевірте серійний номер картки.");
        }
    }

    private static void payForTrip(Scanner scanner, MetroCardBank bank) {
        System.out.println("Введіть серійний номер картки:");
        String serNum = scanner.nextLine();

        System.out.println("Введіть суму для оплати поїздки:");
        double money = scanner.nextDouble();
        scanner.nextLine();

        if (bank.getMoney(serNum, money)) {
            System.out.println("Поїздка успішно оплачена!");
        } else {
            System.out.println("Не вдалося оплатити поїздку. Перевірте наявність коштів на картці.");
        }
    }

    private static void getBalance(Scanner scanner, MetroCardBank bank) {
        System.out.println("Введіть серійний номер картки:");
        String serNum = scanner.nextLine();

        int index = bank.findMetroCard(serNum);
        if (index != -1) {
            System.out.println("Залишок коштів на картці: " + bank.getStore().get(index).getBalance());
        } else {
            System.out.println("Картку з таким серійним номером не знайдено.");
        }
    }
}
