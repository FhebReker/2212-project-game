package accounts;

public class Parental {
    private char[] masterPass;
    private ac[] accounts;

    public Parental() {
        //initialize masterPass and accounts by getting them from the storage
    }

    public void createAccount(String username, char[] pass) {
        ac newAccount = new ac(username, pass);
        // test push
        // test push #2 
    }

    private void resetPassword(String username, char[] pass) {
        // get the account from storage, and reset the password, then update storage
    }

    public void getStats() {
        // return a string representation of the stats?
    }

    private void resetStats() {}

    private void resetHighScores() {}

    public ac[] getAccounts() {
        ac[] accounts;
        // store list of accounts from json file
        return accounts;
    }
 
}