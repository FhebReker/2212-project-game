package ca.uwo.cs2212.group54.stayingalive.accounts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountManagement {
    public AccountManagement() {}

    public boolean checkUserLogin(String username, String password) {
        char[] charPass = password.toCharArray();

        // NOTE: DO NOT TURN getAccount() TO STATIC
        // MAKE A PARENTAL OBJECT IN THE MAIN CLASS OF THE FILE.
        Account acc = Parental.getAccount(username);
        return acc.getPassword().equals(password);
    }

    public boolean checkMasterPass(String masterPass) {
        String pass = new String(Parental.getMasterPass());
        return pass.equals(masterPass);
    }


}
