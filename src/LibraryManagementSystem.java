import java.util.Scanner;
import java.util.InputMismatchException;

class Book {
    int id;
    String name;
    String author;
    int quantity;
    Book next;

    Book(int id, String name, String author, int quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
        this.next = null;
    }
}

class StudentNode {
    String name;
    StudentNode next;

    StudentNode(String name) {
        this.name = name;
        this.next = null;
    }
}

class LibraryManagementSystem {

    static Book head = null;
    static StudentNode front = null;
    static StudentNode rear = null;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n----- LIBRARY MENU -----");
            System.out.println("1. Add Book");
            System.out.println("2. Show Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                choice = 0;
            }

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    showBooks();
                    break;
                case 3:
                    issueBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Wrong choice!");
            }
        } while (choice != 5);
    }

    static void addBook() {
        try {
            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Book Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Author Name: ");
            String author = sc.nextLine();

            System.out.print("Enter Quantity: ");
            int quantity = sc.nextInt();

            Book newBook = new Book(id, name, author, quantity);

            if (head == null) {
                head = newBook;
            } else {
                Book temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = newBook;
            }

            System.out.println("Book added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! ID and Quantity must be integers.");
            sc.nextLine();
        }
    }

    static void showBooks() {
        if (head == null) {
            System.out.println("No books in library.");
            return;
        }

        Book temp = head;
        int i = 1;

        while (temp != null) {
            System.out.println("\nBook " + i);
            System.out.println("Book ID   : " + temp.id);
            System.out.println("Book Name : " + temp.name);
            System.out.println("Author    : " + temp.author);
            System.out.println("Quantity  : " + temp.quantity);
            temp = temp.next;
            i++;
        }
    }

    static void issueBook() {
        int id = -1;
        try {
            System.out.print("Enter Book ID to issue: ");
            id = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Book ID must be an integer.");
            sc.nextLine();
            return;
        }

        Book temp = head;

        while (temp != null) {
            if (temp.id == id) {

                System.out.print("Enter Student Name: ");
                String student = sc.nextLine();

                if (temp.quantity > 0) {
                    temp.quantity--;
                    System.out.println("Book issued to " + student);
                } else {
                    enqueue(student);
                    System.out.println("Book not available.");
                    System.out.println(student + " added to waiting queue.");
                }
                return;
            }
            temp = temp.next;
        }

        System.out.println("Book ID not found.");
    }

    static void returnBook() {
        int id = -1;
        try {
            System.out.print("Enter Book ID to return: ");
            id = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Book ID must be an integer.");
            sc.nextLine();
            return;
        }

        Book temp = head;

        while (temp != null) {
            if (temp.id == id) {

                if (!isQueueEmpty()) {
                    String nextStudent = dequeue();
                    System.out.println("Book issued to waiting student: " + nextStudent);
                } else {
                    temp.quantity++;
                    System.out.println("Book returned successfully.");
                }
                return;
            }
            temp = temp.next;
        }

        System.out.println("Book ID not found.");
    }

    static void enqueue(String student) {
        StudentNode newNode = new StudentNode(student);

        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    static String dequeue() {
        if (front == null) {
            return null;
        }

        String name = front.name;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return name;
    }

    static boolean isQueueEmpty() {
        return front == null;
    }
}