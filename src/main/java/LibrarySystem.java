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
            IO.println("====================");
            IO.println("Bibliotekshanteraren");
            IO.println("====================");
            IO.println("1. 📕 Lägg till bok");
            IO.println("2. 👤 Registrera medlem");
            IO.println("3. 📗 Låna bok");
            IO.println("4. 📘 Lämna tillbaka bok");
            IO.println("5. 🔍 Sök bok");
            IO.println("6. 📚 Visa alla böcker och status");
            IO.println("7. 🧾 Utlåningsstatistik");
            IO.println("0. 🚪 Avsluta");
            IO.println("🔙Tryck ENTER för att gå tillbaka i menyn");
            IO.println();
            IO.print("Välj ett alternativ: ");
            String choice = IO.readln();

            if (choice.equals("0")) {
                break;
            }
            switch (choice) {
                // main menu functions
                case "1":
                    addBook(library);
                    break;

                case "2":
                    registerMember(library);
                    break;

                case "3":
                    borrowBook(library);
                    break;

                case "4":
                    returnBook(library);
                    break;

                case "5":
                    searchBook(library);
                    break;

                case "6":
                    library.showAllBooks();
                    break;


                case "7":
                    borrowingStatistics(library);
                    break;

                default:
                    IO.println("Ogiltigt menyval.");
                    break;
            }
        }

    }

    private static void borrowingStatistics(Library library) {
        Member memberWithMostLoans = library.memberWithMostLoans();

        if (memberWithMostLoans == null) {
            IO.println("Det finns inga utlånade böcker just nu.");
            return;
        }

        IO.println();
        IO.println("Medlem med flest aktiva lån:");
        IO.println(memberWithMostLoans.getName()
                + " (medlems-ID: "
                + memberWithMostLoans.getId() + ")");
        IO.println("Antal aktiva lån: "
                + memberWithMostLoans.getActiveLoans());
        IO.println();

        IO.println("Tryck ENTER för att gå tillbaka till menyn.");
        IO.readln();
    }


    private static void searchBook(Library library) {
        IO.println("Ange titel eller författare:");
        String searchTerm = IO.readln();
        if (searchTerm.isEmpty()) {
            return;
        }

        library.bookSearch(searchTerm);
        // prevent immediately looping back to main menu upon receiving results
        IO.println();
        IO.println("Tryck ENTER för att gå tillbaka till menyn.");
        IO.readln();
    }

    private static void returnBook(Library library) {
        IO.println();
        IO.println("Ange ISBN:");
        String isbnToReturn = IO.readln();

        if (isbnToReturn.isEmpty()) {
            return;
        }

        if (!library.bookExists(isbnToReturn)) {
            IO.println("Boken hittades inte.");
            return;
        }

        IO.println("Ange medlems-ID:");

        //make sure program doesn't crash if user input isn't a number
        try {
            int memberIdToReturn = Integer.parseInt(IO.readln());

            if (!library.memberExists(memberIdToReturn)) {
                IO.println("Medlemmen hittades inte.");
                return;
            }
            boolean returned = library.returnBook(isbnToReturn, memberIdToReturn);

            IO.println();

            if (returned) {
                IO.println("Boken är tillbakalämnad.");
            } else {
                IO.println("Kunde inte lämna tillbaka boken.");
            }

            IO.println();

        } catch (NumberFormatException e) {
            IO.println("Medlems-ID får bara innehålla siffror.");
        }
    }

    private static void borrowBook(Library library) {
        IO.println();
        IO.println("Ange ISBN:");
        String isbnToBorrow = IO.readln();

        if (isbnToBorrow.isEmpty()) {
            return;
        }

        if (!library.bookExists(isbnToBorrow)) {
            IO.println("Boken hittades inte.");
            return;
        }
        if (library.bookIsBorrowed(isbnToBorrow)) {
            IO.println("Boken är redan utlånad.");
            return;
        }
        IO.println("Ange ditt medlems-ID:");

        //make sure program doesn't crash if user input isn't a number
        try {int memberIdToBorrow = Integer.parseInt(IO.readln());

            if (!library.memberExists(memberIdToBorrow)) {
                IO.println("Medlemmen hittades inte.");
                return;
            }
            boolean borrowed = library.borrowBook(isbnToBorrow, memberIdToBorrow);

            IO.println();

            if (borrowed) {
                IO.println("Boken lånad!");
            } else {
                IO.println("Kunde inte låna boken.");
            }
            IO.println();

        } catch (NumberFormatException e) {
            IO.println("Medlems-ID får bara innehålla siffror.");
        }
    }

    private static void registerMember(Library library) {
        IO.println();
        IO.println("Ange namn:");
        String name = IO.readln();

        if (name.isEmpty()) {
            return;
        }
        Member member = library.registerMember(name);

        IO.println();
        IO.println("Medlem registrerad. Ditt medlems-ID är: " + member.getId());
        IO.println();
    }

    private static void addBook(Library library) {
        IO.println("Ange titel:");
        String title = IO.readln();
        if (title.isEmpty()) {
            return;
        }

        IO.println("Ange författare:");
        String author = IO.readln();

        IO.println("Ange ISBN:");
        String isbn = IO.readln();

        while (!isbn.isEmpty()) {

            //Make sure every char in the input is a number
            boolean onlyNumbers = true;

            for (int i = 0; i < isbn.length(); i++) {
                if (!Character.isDigit(isbn.charAt(i))) {
                    onlyNumbers = false;
                    break;
                }
            }

            if (!onlyNumbers) {
                IO.println("ISBN får bara innehålla siffror, försök igen");
                isbn = IO.readln();
            } else if (library.bookExists(isbn)) {
                IO.println("En bok med detta ISBN finns redan i registret, försök igen.");
                isbn = IO.readln();
            } else {
                break;
            }
        }
        if (isbn.isEmpty()) {
            return;
        }
        library.addBook(new Book(title, author, isbn));

        IO.println("Boken har lagts till.");
    }
}
