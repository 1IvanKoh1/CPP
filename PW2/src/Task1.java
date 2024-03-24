import java.io.*;
import java.util.ArrayList;

class Author implements Serializable {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "" + name;
    }
}

class Book implements Serializable {
    private String title;
    private ArrayList<Author> authors;

    public Book(String title, ArrayList<Author> authors) {
        this.title = title;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Книга: " + title + ", Автори: " + authors;
    }
}

class Bookshelf implements Serializable {
    private ArrayList<Book> books;

    public Bookshelf() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }
}

class Reader implements Serializable {
    private String name;

    public Reader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Читач: " + name;
    }
}

class Library implements Serializable {
    private ArrayList<Reader> readers;
    private Bookshelf bookshelf;

    public Library() {
        this.readers = new ArrayList<>();
        this.bookshelf = new Bookshelf();
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    public void displayReaders() {
        for (Reader reader : readers) {
            System.out.println(reader);
        }
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }
}

public class Task1 {
    public static void main(String[] args) {
        Library library = new Library();

        Reader reader1 = new Reader("Іван");
        Reader reader2 = new Reader("Ольга");
        library.addReader(reader1);
        library.addReader(reader2);

        ArrayList<Author> authors1 = new ArrayList<>();
        authors1.add(new Author("Стівен Кінг"));
        Book book1 = new Book("Сяйво", authors1);

        ArrayList<Author> authors2 = new ArrayList<>();
        authors2.add(new Author("Вірджинії Вулф"));
        authors2.add(new Author("Ванесса Белл"));
        Book book2 = new Book("Поміж актами", authors2);

        library.getBookshelf().addBook(book1);
        library.getBookshelf().addBook(book2);

        System.out.println("Читачі:");
        library.displayReaders();
        System.out.println("\nКниги:");
        library.getBookshelf().displayBooks();

        serializeObject("library.ser", library);

        Library deserializedLibrary = (Library) deSerializeObject("library.ser");
        System.out.println("\nДесеріалізовані читачі:");
        deserializedLibrary.displayReaders();
        System.out.println("\nДесеріалізовані книги:");
        deserializedLibrary.getBookshelf().displayBooks();
    }

    public static void serializeObject(String fileName, Object obj) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
            os.writeObject(obj);
            os.close();
            System.out.println("Серіалізація успішна.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deSerializeObject(String fileName) {
        Object obj = null;
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
            obj = is.readObject();
            is.close();
            System.out.println("Десеріалізація успішна.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
