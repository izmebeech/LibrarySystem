public class Member {

        private int id;
        private String name;
        private int activeLoans;

        public Member(int id, String name) {
            this.id = id;
            this.name = name;
            this.activeLoans = 0;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getActiveLoans() {
            return activeLoans;
        }

        public void setActiveLoans(int activeLoans) {
            this.activeLoans = activeLoans;
        }

        public boolean canBorrowMoreBooks() {
            if (this.activeLoans >= 5) {
                return false;
            } else {
                return true;
            }
        }
    }


