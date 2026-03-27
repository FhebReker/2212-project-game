package ca.uwo.cs2212.group54.stayingalive.accounts;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parental {

    private char[] masterPass;
    private ArrayList<Account> accounts;

    public Parental() {
        //initialize masterPass and accounts by getting them from the storage
        ObjectMapper objectmapper = new ObjectMapper();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("data/players.json");

            if (file.exists() && file.length() > 0) {
                accounts = objectMapper.readValue(file, new TypeReference<ArrayList<Account>>() {});
            } else {
                accounts = new ArrayList<Account>();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAccount(String username, char[] pass) {
        Account newAccount = new Account(username, pass);
        accounts.add(newAccount);
        // not loading from storage since accounts array should already have all the relevant data.
        File file = new File("data/players.json");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, accounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetPassword(String username, char[] newPass) {
        for (Account account: accounts) {
            if (account.getUsername().equals(username)) {
                account.setPassword(newPass);
            }
        }
        saveAccountData();
    }

    public LevelStatistic getStats(String username) {
        // return the statistics for a specific player
        for (Account account: accounts) {
            if (account.getUsername().equals(username)) {
                return account.getStats();
            }
        }
        System.err.print("No stats found for " + username);
        return null;
    }

    private void resetStats() {
        // resets all player statistic data
        for (Account account: accounts) {
            account.getStats().clearStatistics();
            account.getProgress().clearProgress();
        }
        saveAccountData();
    }

    private void resetHighScores() {
        
    }

    private ArrayList<Account> getAccountsFromStorage() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("data/players.json");

            if (file.exists() && file.length() > 0) {
                accounts = objectMapper.readValue(file, new TypeReference<ArrayList<Account>>() {});
            } else {
                accounts = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private void saveAccountData() {
        File file = new File("data/players.json");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, accounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
