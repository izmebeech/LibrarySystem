public class LibrarySystem {
    static void main() {

        Library library = new Library();


        //Member-list
        Member member1 = library.registerMember("Jennifer");
        Member member2 = library.registerMember("Anders");
        Member member3 = library.registerMember("Aron");
        Member member4 = library.registerMember("Fatima");
        Member member5 = library.registerMember("Daniel");

        //Book-list
        library.addBook(new Book("Twilight","Stephenie Meyer","9781904233657"));
        library.addBook(new Book("The Return of the King","J.R.R Tolkien","9780008376147"));
        library.addBook(new Book("The Trial","Franz Kafka","9780805210408"));
        library.addBook(new Book("Farlig Midsommar","Tove Jansson","9789129687378"));
        library.addBook(new Book("The ALchemist","Paolo Coelho","9780722532935"));

        //Testing to see if borrowBook method works
        boolean borrowed = library.borrowBook("9780722532935", member4.getId());
        IO.println(borrowed);

        boolean borrowedAgain = library.borrowBook("9780722532935", member3.getId());
        IO.println(borrowedAgain);



    }
}
