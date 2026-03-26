import java.util.ArrayList;

public class Parental {

    private char[] masterPass;
    private ArrayList<Account> accounts;

    public Parental() {
        //initialize masterPass and accounts by getting them from the storage
        accounts = new ArrayList<Account>();
    }

    public void createAccount(String username, char[] pass) {
        Account newAccount = new Account(username, pass);
        accounts.add(newAccount);
    }

    private void resetPassword(String username, char[] pass) {
        // get the account from storage, and reset the password, then update storage
    }

    public void getStats() {
        // return a string representation of the stats?
    }

    private void resetStats() {}

    private void resetHighScores() {}

    public ArrayList<Account> getAccountsFromStorage() {
        
        // store list of accounts from json file
        return accounts;
    }
}
