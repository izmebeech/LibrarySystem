public class Library {

    private Book[] books;
    private Member[] members;
    private Member[] borrowedBy;

    private int bookCount;
    private int memberCount;
    private int nextMemberId;

    //constructor
    public Library() {
        this.books = new Book[5];
        this.members = new Member[5];
        this.borrowedBy = new Member[5];

        this.bookCount = 0;
        this.memberCount = 0;
        this.nextMemberId = 1;
    }
    //add book + expander if array is full
    public void addBook(Book book) {
        if (bookCount == books.length) {
            expandBooks();
        }
        books[bookCount] = book;
        bookCount++;
    }
    private void expandBooks() {
        Book[] newBooks = new Book[books.length * 2];
        Member[] newBorrowedBy = new Member[borrowedBy.length * 2];

        for (int i = 0; i < books.length; i++) {
            newBooks[i] = books[i];
            newBorrowedBy[i] = borrowedBy[i];
        }
        books = newBooks;
        borrowedBy = newBorrowedBy;
    }
    //New member + expander if array is full
    public Member registerMember(String name) {
        if (memberCount == members.length) {
            expandMembers();
        }
        Member member = new Member(nextMemberId, name);
        members[memberCount] = member;
        memberCount++;
        nextMemberId++;
        return member;
    }

    private void expandMembers() {
        Member[] newMembers = new Member[members.length * 2];

        for (int i = 0; i < members.length; i++) {
            newMembers[i] = members[i];
        }
        members = newMembers;
        }

        // Show all books, sorted
        public void showAllBooks() {
            Book[] sort = new Book[bookCount];
            Member[] sortedBorrowedBy = new Member[bookCount];

            for (int i = 0; i < bookCount; i++) {
                sort[i] = books[i];
                sortedBorrowedBy[i] = borrowedBy[i];
            }

            for (int i = 0; i < bookCount - 1; i++) {
                for (int j = 0; j < bookCount - 1; j++) {
                    if (sort[j].title().compareTo(sort[j + 1].title()) > 0) {

                        Book temporary = sort[j];
                        sort[j] = sort[j + 1];
                        sort[j + 1] = temporary;

                        Member temporaryMember = sortedBorrowedBy[j];
                        sortedBorrowedBy[j] = sortedBorrowedBy[j + 1];
                        sortedBorrowedBy[j + 1] = temporaryMember;
                    }
                }
            }
            //Show which member currently loaned the books
            for (int i = 0; i < bookCount; i++) {
                IO.println("Titel: " + sort[i].title());
                IO.println("Författare: " + sort[i].author());
                IO.println("ISBN: " + sort[i].isbn());

                if (sortedBorrowedBy[i] == null) {
                    IO.println("Status: Tillgänglig");
                } else {
                    IO.println("Status: Utlånad till "
                            + sortedBorrowedBy[i].getName()
                            + " (medlems-ID: "
                            + sortedBorrowedBy[i].getId() + ")");
                }
                //Add a space between each book in the sorted library
                IO.println();
            }
        }

        //Check if member exists
        public boolean memberExists(int memberId) {
            for (int i = 0; i < memberCount; i++) {
                if (members[i].getId() == memberId) {
                    return true;
                }
            }
            return false;
        }

        // Checks every registered member for who has the most loans.
        public Member memberWithMostLoans() {
            Member memberWithMostLoans = null;

            for (int i = 0; i < memberCount; i++) {
                if (members[i].getActiveLoans() > 0
                        && (memberWithMostLoans == null
                        || members[i].getActiveLoans() > memberWithMostLoans.getActiveLoans())) {
                    memberWithMostLoans = members[i];
                }
            }

            return memberWithMostLoans;
        }

        // Check if book exists in library
        public boolean bookExists(String isbn) {
            for (int i = 0; i < bookCount; i++) {
                if (books[i].isbn().equals(isbn)) {
                    return true;
              }
         }
            return false;
        }


        // Check if book is already borrowed
        public boolean bookIsBorrowed(String isbn) {
            for (int i = 0; i < bookCount; i++) {
                if (books[i].isbn().equals(isbn)) {
                    return borrowedBy[i] != null;
            }
        }
        return false;
        }


        //Search function for books via author or title
        public void bookSearch(String searchTerm) {
            for (int i = 0; i < bookCount; i++) {

                if (books[i].title().toLowerCase().contains(searchTerm.toLowerCase())
                        || books[i].author().toLowerCase().contains(searchTerm.toLowerCase())) {

                    IO.println();
                    IO.println("Titel: " + books[i].title());
                    IO.println("Författare: " + books[i].author());
                    IO.println("ISBN: " + books[i].isbn());
                    IO.println();
                }
            }
        }

        //Borrowing book
        public boolean borrowBook(String isbn, int memberId) {
            for (int i = 0; i < bookCount; i++) {
                if (books[i].isbn().equals(isbn)) {

                    if (borrowedBy[i] != null) {
                        return false;
                    }

                    for (int j = 0; j < memberCount; j++) {
                        if (members[j].getId() == memberId) {

                            if (members[j].canBorrowMoreBooks()) {
                                borrowedBy[i] = members[j];
                                members[j].setActiveLoans(
                                        members[j].getActiveLoans() + 1
                                );
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        //returning book
    public boolean returnBook(String isbn, int memberId) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isbn().equals(isbn)) {
                if (borrowedBy[i] != null) {
                    if (borrowedBy[i].getId() == memberId) {
                        borrowedBy[i].setActiveLoans(
                                borrowedBy[i].getActiveLoans() - 1);
                        borrowedBy[i] = null;
                        return true;
                    }
                }
            }
        }
        return false;
    }


}




