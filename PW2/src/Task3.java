import java.io.*;
import java.util.ArrayList;

class AuthorT3 implements Externalizable {
    private String name;

    public AuthorT3() {}

    public AuthorT3(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
    }

    @Override
    public String toString() {
        return "" + name;
    }
}

class BookT3 implements Externalizable {
    private String title;
    private ArrayList<AuthorT3> authors;

    public BookT3() {}

    public BookT3(String title, ArrayList<AuthorT3> authors) {
        this.title = title;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<AuthorT3> getAuthors() {
        return authors;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(title);
        out.writeObject(authors);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = in.readUTF();
        authors = (ArrayList<AuthorT3>) in.readObject();
    }

    @Override
    public String toString() {
        return "Книга: " + title + ", Автори: " + authors;
    }
}

class BookshelfT3 implements Externalizable {
    private ArrayList<BookT3> books;

    public BookshelfT3() {
        this.books = new ArrayList<>();
    }

    public void addBook(BookT3 book) {
        books.add(book);
    }

    public void displayBooks() {
        for (BookT3 book : books) {
            System.out.println(book);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(books);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        books = (ArrayList<BookT3>) in.readObject();
    }
}

class ReaderT3 implements Externalizable {
    private String name;

    public ReaderT3() {}

    public ReaderT3(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
    }

    @Override
    public String toString() {
        return "Читач: " + name;
    }
}

class LibraryT3 implements Externalizable {
    private ArrayList<ReaderT3> readers;
    private BookshelfT3 bookshelf;

    public LibraryT3() {
        this.readers = new ArrayList<>();
        this.bookshelf = new BookshelfT3();
    }

    public void addReader(ReaderT3 reader) {
        readers.add(reader);
    }

    public void displayReaders() {
        for (ReaderT3 reader : readers) {
            System.out.println(reader);
        }
    }

    public BookshelfT3 getBookshelf() {
        return bookshelf;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(readers);
        out.writeObject(bookshelf);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        readers = (ArrayList<ReaderT3>) in.readObject();
        bookshelf = (BookshelfT3) in.readObject();
    }
}

public class Task3 {
    public static void main(String[] args) {
        LibraryT3 library = new LibraryT3();

        ReaderT3 reader1 = new ReaderT3("Іван");
        ReaderT3 reader2 = new ReaderT3("Ольга");
        library.addReader(reader1);
        library.addReader(reader2);

        ArrayList<AuthorT3> authors1 = new ArrayList<>();
        authors1.add(new AuthorT3("Стівен Кінг"));
        BookT3 book1 = new BookT3("Сяйво", authors1);

        ArrayList<AuthorT3> authors2 = new ArrayList<>();
        authors2.add(new AuthorT3("Вірджинії Вулф"));
        authors2.add(new AuthorT3("Ванесса Белл"));
        BookT3 book2 = new BookT3("Поміж актами", authors2);

        library.getBookshelf().addBook(book1);
        library.getBookshelf().addBook(book2);

        System.out.println("Читачі:");
        library.displayReaders();
        System.out.println("\nКниги:");
        library.getBookshelf().displayBooks();

        serializeObject("library.ser", library);

        LibraryT3 deserializedLibrary = (LibraryT3) deSerializeObject("library.ser");
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
