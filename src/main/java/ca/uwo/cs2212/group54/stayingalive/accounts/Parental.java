package ca.uwo.cs2212.group54.stayingalive.accounts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parental {
    @JsonProperty("master_pass")
    private String masterPass;
    private static ArrayList<Account> accounts;

    public Parental() {
        //initialize masterPass and accounts by getting them from the storage
        ObjectMapper objectmapper = new ObjectMapper();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // loading player accounts
            File file = new File("data/players.json");
            if (file.exists() && file.length() > 0) {
                accounts = objectMapper.readValue(file, new TypeReference<ArrayList<Account>>() {});
            } else {
                System.out.println("new array");
                accounts = new ArrayList<Account>();
            }
            // loading master password:
            File masterPassFile = new File("data/master.json");
            if (masterPassFile.exists() && masterPassFile.length() > 0) {
                Map<String,String> data1 = objectMapper.readValue(masterPassFile,Map.class);
                masterPass = data1.get("master_pass");
            } else {System.out.println("No data in " + masterPassFile.getName());}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createAccount(String username, String pass) {
        //true if account created, false if not created (account name already exists)
        if (Objects.nonNull(getAccount(username))) return false;
        int preCreation = accounts.size();

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
        int postCreation = accounts.size();
        if (accounts.size() == 1) System.out.println("first account created");
        return postCreation - preCreation == 1; // successful account creation and adding to array
    }

    private void resetPassword(String username, String newPass) {
        for (Account account: accounts) {
            if (account.getUsername().equals(username)) {
                account.setPassword(newPass);
            }
        }
        saveAccountData();
    }

    public LevelStatistic[] getStats(String username) {
        // return the statistics for a specific player
        for (Account account: accounts) {
            if (account.getUsername().equals(username)) {
                return account.getAllLevelStats();
            }
        }
        System.err.print("No stats found for " + username);
        return null;
    }

    private void resetStats() {
        // resets all statistic data for all players
        for (Account account: accounts) {
            account.clearStats();
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

    //will just update the account info in the arraylist
    //will not readjust the information on the levelstatistic
    //account is the updated account object
    public void updateAccountData(Account account) {
        int i = accounts.indexOf(account);
        accounts.set(i,account);
        saveAccountData();
    }

    public static void saveAccountData() {
        File file = new File("data/players.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, accounts);
        } catch (IOException e) {}
    }

    public Account getAccount(String username) {
        for (Account account: accounts) {
            if (account.getUsername().equals(username)) return account;
        }
        return null;
    }

    protected String getMasterPass() {
        return masterPass;
    }

    protected void deleteAllAccounts() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("data/players.json");

        try {
            // Write an empty list to the file
            objectMapper.writeValue(file, new ArrayList<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
        accounts.clear(); // clearing the list
    }

}
