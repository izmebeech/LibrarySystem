import java.util.Scanner;

public class LibrarySystem {
    static void main() {

        Library library = new Library();

        //Initial books
        library.addBook(new Book("Twilight","Stephenie Meyer","9781904233657"));
        library.addBook(new Book("The Return of the King","J.R.R Tolkien","9780008376147"));
        library.addBook(new Book("The Trial","Franz Kafka","9780805210408"));
        library.addBook(new Book("Farlig Midsommar","Tove Jansson","9789129687378"));
        library.addBook(new Book("The Alchemist","Paolo Coelho","9780722532935"));


        while (true) {
            IO.println("Bibliotekshanteraren");
            IO.println("====================");
            IO.println("1. Lägg till bok");
            IO.println("2. Registrera medlem");
            IO.println("3. Låna bok");
            IO.println("4. Lämna tillbaka bok");
            IO.println("5. Sök bok");
            IO.println("6. Visa alla böcker och status");
            IO.println("7. Utlåningsstatistik");
            IO.println("e. Avsluta");

            String choice = IO.readln();

            if (choice.equals("e")) {
                break;
            }
            switch (choice) {

                // main menu functions
                case "1":
                    addBook(library);
                    break;

                case "2":
                    IO.println("Ange namn:");
                    String name = IO.readln();

                    Member member = library.registerMember(name);

                    IO.println("Medlem registrerad. Ditt medlems-ID är:  " + member.getId());
                    break;

                case "3":
                    IO.println("Ange ISBN:");
                    String isbnToBorrow = IO.readln();

                    if (!library.bookExists(isbnToBorrow)) {
                        IO.println("Boken hittades inte.");
                        break;
                    }

                    if (library.bookIsBorrowed(isbnToBorrow)) {
                        IO.println("Boken är redan utlånad.");
                        break;
                    }

                    IO.println("Ange medlems-ID:");
                    // Make sure program doesn't crash if user input = letters
                    try {
                        int memberIdToBorrow = Integer.parseInt(IO.readln());
                        if (!library.memberExists(memberIdToBorrow)) {
                            IO.println("Medlemmen hittades inte.");
                            break;
                        }
                        boolean borrowed = library.borrowBook(isbnToBorrow, memberIdToBorrow);

                        if (borrowed) {
                            IO.println("Boken lånad!");
                        } else {
                            IO.println("Kunde inte låna boken.");
                        }

                    } catch (NumberFormatException e) {
                        IO.println("Medlems-ID får bara innehålla siffror.");
                    }
                    break;

                case "4":
                    IO.println("Ange ISBN:");
                    String isbnToReturn = IO.readln();

                    if (!library.bookExists(isbnToReturn)) {
                        IO.println("Boken hittades inte.");
                        break;
                    }

                    IO.println("Ange medlems-ID:");

                    // make sure program doesn't crash if user input = letters
                    try {
                        int memberIdToReturn = Integer.parseInt(IO.readln());
                        if (!library.memberExists(memberIdToReturn)) {
                            IO.println("Medlemmen hittades inte.");
                            break;
                        }

                        boolean returned = library.returnBook(isbnToReturn, memberIdToReturn);

                        if (returned) {
                            IO.println("Boken är tillbakalämnad.");
                        } else {
                            IO.println("Kunde inte lämna tillbaka boken.");
                        }
                    } catch (NumberFormatException e) {
                        IO.println("Medlems-ID får bara innehålla siffror.");
                    }
                    break;

                case "5":
                    IO.println("Ange titel eller författare:");
                    String searchTerm = IO.readln();

                    library.bookSearch(searchTerm);
                    break;

                case "6":
                    library.showAllBooks();
                    break;


                case "7":
                    Member memberWithMostLoans = library.memberWithMostLoans();

                    if (memberWithMostLoans == null) {
                        IO.println("Det finns inga utlånade böcker just nu.");
                        break;
                    }
                    IO.println("Medlem med flest aktiva lån:");
                    IO.println(memberWithMostLoans.getName()
                            + " (medlems-ID: "
                            + memberWithMostLoans.getId() + ")");
                    IO.println("Antal aktiva lån: "
                            + memberWithMostLoans.getActiveLoans());
                    break;

                default:
                    IO.println("Ogiltigt menyval.");
                    break;
            }
        }

    }

    private static void addBook(Library library) {
        IO.println("Ange titel:");
        String title = IO.readln();

        IO.println("Ange författare:");
        String author = IO.readln();

        IO.println("Ange ISBN:");
        String isbn = IO.readln();

        //Make sure every char in the input is a number
        boolean onlyNumbers = true;

        for (int i = 0; i < isbn.length(); i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                onlyNumbers = false;
                break;
            }
        }
        if (!onlyNumbers) {
            IO.println("ISBN får bara innehålla siffror.");
            return;
        }

        if (library.bookExists(isbn)) {
            IO.println("En bok med detta ISBN finns redan i registret.");
            return;
        }

        library.addBook(new Book(title, author, isbn));

        IO.println("Boken har lagts till.");
    }
}
