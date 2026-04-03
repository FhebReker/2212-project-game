package ca.uwo.cs2212.group54.stayingalive.accounts;

import java.util.Objects;

public class AccountManagement {
    Parental parental;
    private static Account currentAccount; // To check which account is currently logged in

    public static void main(String[] args) {
        System.out.println("================");
        Parental parental = new Parental();
        AccountManagement accMng = new AccountManagement(parental);
        System.out.println(accMng.checkMasterPass("gaming")); // works
        //test 1: account 1
        String user1 = "dogman"; String pass1 = "testing";
        boolean created = parental.createAccount(user1, pass1);
        System.out.println("exists: "+accMng.checkUserLogin(user1,pass1));
        //test 2: account 2
        String user2 = "secondAcc"; String pass2 = "secondTest";
        parental.createAccount(user2, pass2);
        System.out.println("exists2: "+accMng.checkUserLogin(user2, pass2));
        //test 3: get stats
        System.out.println("get avg_wpm:" + parental.getAccount(user2).getLevelStat(1).avgWPM); // works
        //test 4: wrong password
        System.out.println("check pass: " + accMng.checkUserLogin(user1, pass2));
        //test 5: no username exists
        System.out.println("check username exists: " + accMng.checkUserLogin("osman", pass2));
        //test 6: update/save stats
        /* 
        LevelStatistic stats = new LevelStatistic(new LevelData(1,1));
        stats.avgWPM = 40;
        stats.peakWPM = 30;
        parental.getAccount(user2).setStats();
        Parental.saveAccountData();
        */
    }

    public AccountManagement(Parental parental) {
        this.parental = parental;
    }

    public boolean checkUserLogin(String username, String password) {
        // NOTE: DO NOT TURN getAccount() TO STATIC
        // MAKE A PARENTAL OBJECT IN THE MAIN CLASS OF THE FILE.
        Account acc = parental.getAccount(username);
        if (Objects.isNull(acc)) return false;
        return acc.getPassword().equals(password);
    }

    public boolean checkMasterPass(String masterPass) {
        String pass = new String(parental.getMasterPass());
        return pass.equals(masterPass);
    }

    public Parental getParental() {
        return parental;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account currentAccount) {
        AccountManagement.currentAccount = currentAccount;
    }

}
