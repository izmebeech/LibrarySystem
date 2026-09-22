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
    //add book
    public void addBook(Book book) {
        if (bookCount == books.length) {
            expandBooks();
        }
        books[bookCount] = book;
        bookCount++;
    }
    //if array is full
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
    }
