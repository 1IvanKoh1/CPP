import java.io.*;
import java.util.ArrayList;

class AuthorT2 implements Serializable {
    private transient String name;

    public AuthorT2(String name) {
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

class BookT2 implements Serializable {
    private transient String title;
    private transient ArrayList<AuthorT2> authors;

    public BookT2(String title, ArrayList<AuthorT2> authors) {
        this.title = title;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<AuthorT2> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Книга: " + title + ", Автори: " + authors;
    }
}

class BookshelfT2 implements Serializable {
    private ArrayList<BookT2> books;

    public BookshelfT2() {
        this.books = new ArrayList<>();
    }

    public void addBook(BookT2 book) {
        books.add(book);
    }

    public void displayBooks() {
        for (BookT2 book : books) {
            System.out.println(book);
        }
    }
}

class ReaderT2 implements Serializable {
    private transient String name;

    public ReaderT2(String name) {
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

class LibraryT2 implements Serializable {
    private ArrayList<ReaderT2> readers;
    private BookshelfT2 bookshelf;

    public LibraryT2() {
        this.readers = new ArrayList<>();
        this.bookshelf = new BookshelfT2();
    }

    public void addReader(ReaderT2 reader) {
        readers.add(reader);
    }

    public void displayReaders() {
        for (ReaderT2 reader : readers) {
            System.out.println(reader);
        }
    }

    public BookshelfT2 getBookshelf() {
        return bookshelf;
    }
}

public class Task2 {
    public static void main(String[] args) {
        LibraryT2 library = new LibraryT2();

        ReaderT2 reader1 = new ReaderT2("Іван");
        ReaderT2 reader2 = new ReaderT2("Ольга");
        library.addReader(reader1);
        library.addReader(reader2);

        ArrayList<AuthorT2> authors1 = new ArrayList<>();
        authors1.add(new AuthorT2("Стівен Кінг"));
        BookT2 book1 = new BookT2("Сяйво", authors1);

        ArrayList<AuthorT2> authors2 = new ArrayList<>();
        authors2.add(new AuthorT2("Вірджинії Вулф"));
        authors2.add(new AuthorT2("Ванесса Белл"));
        BookT2 book2 = new BookT2("Поміж актами", authors2);

        library.getBookshelf().addBook(book1);
        library.getBookshelf().addBook(book2);

        System.out.println("Читачі:");
        library.displayReaders();
        System.out.println("\nКниги:");
        library.getBookshelf().displayBooks();

        serializeObject("library.ser", library);

        LibraryT2 deserializedLibrary = (LibraryT2) deSerializeObject("library.ser");
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
