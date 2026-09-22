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




