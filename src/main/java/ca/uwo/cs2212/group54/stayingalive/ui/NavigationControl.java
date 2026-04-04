package ca.uwo.cs2212.group54.stayingalive.ui;

import ca.uwo.cs2212.group54.stayingalive.accounts.*;

/**
 * NavigationControl class both controls the screen navigation and the origin point of the application.
 * <p>
 * NavigationControl initializes all screens, then starts at the main menu screen. 
 * It also has a method to update the current screen, 
 * and a method to set the current screen to another and update.
 * 
 * @author Fardin Abbassi
 */

public class NavigationControl {
    // Screen Control
    private static Screen[] listOfScreens = new Screen[8];
    private static Screen currentScreen;
    private static int currentScreenIndex  = 0;
    private static int previousScreenIndex = 0;

    // Account Control
    private static AccountManagement accountManager;

    // Screen Dimensions
    public final static int screenW = 800; //sets the screen width
    public final static int screenH = 450; //sets the screen height

    // Other
    private static long startTime; // for checking (start - end) time to update player playtime
    private static long endTime;

    /**
     * Updates the current screen by showing it and setting its size to defined dimensions
     * @author Fardin Abbassi
     */
    private static void updateScreen() {
        currentScreen.showScreen();
        if (!currentScreen.getClass().toString().equals("class ca.uwo.cs2212.group54.stayingalive.ui.LoginScreen")){
             currentScreen.getFrame().setSize(screenW, screenH);
             System.out.println(currentScreen.getClass().toString());   // debug
        }
        currentScreen.getFrame().setLocationRelativeTo(null);
    }
    
    /**
     * Sets current screen to the screen at the index of the list of screens and updates screen to match.
     * Disposes of the previous screen to prevent memory leak.
     * @author Fardin Abbassi
     * @param int screenToSet
     */
    public static void setCurrentScreen(int screenToSet) {
        if (currentScreen != null && screenToSet != 1) currentScreen.getFrame().dispose();
        if (currentScreenIndex != 1) previousScreenIndex = currentScreenIndex;
        currentScreenIndex  = screenToSet;
        currentScreen = listOfScreens[screenToSet];
        updateScreen();
    }

    /**
     * Navigates back to the previous screen.
     * @author Omar Soliman
     */
    public static void goBack() {
        System.err.println("prev index = " + previousScreenIndex);
        setCurrentScreen(previousScreenIndex);
        System.err.println("prev index = " + previousScreenIndex);
    }
    /**
     * Get screen at the index of the screen list.
     */
    public static Screen getScreen(int index) {return listOfScreens[index];}
    /**
     * Get account management object.
     */
    public static AccountManagement getAccountManager() {return accountManager;}

    
    /**
     * Constructor for NavigationControl. Initializes screens, then starts at the main menu screen.
     * @author Fardin Abbassi
     */
    public NavigationControl() {
        // Initialize screens (add as screens are implemented)
        MainMenuScreen mainMenu = new MainMenuScreen();
        LoginScreen loginScreen = new LoginScreen();
        TutorialScreen tutorialScreen = new TutorialScreen();
        PlayerScreen playerScreen = new PlayerScreen(); // TODO: replace placeholder constructor
        StatsScreen statsScreen = new StatsScreen(); // TODO: Replace placeholder constructor
        GameStoreScreen gameStoreScreen = new GameStoreScreen();        // GameStoreScreen gameStoreScreen = new GameStoreScreen(null);
        ParentalControls parentalControls = new ParentalControls();

        // Add screens to list of screens (add as screens are implemented)
        listOfScreens[0] = mainMenu;
        listOfScreens[1] = loginScreen;         // might not be necessary due to implementation as a pop-up
        listOfScreens[2] = tutorialScreen;      // tutorial
        listOfScreens[3] = playerScreen;        // player
        listOfScreens[4] = statsScreen;         // stats
        listOfScreens[5] = gameStoreScreen;     // game store           //listOfScreens[5] = gameStoreScreen;     // game store
        listOfScreens[6] = parentalControls;    // parental control
        listOfScreens[7] = null;                // gameplay
        

        // Add account manager and start at main menu
        accountManager = new AccountManagement(new Parental());
        setCurrentScreen(0);
    }

    /**
     * This method is exclusively used when a player logs in. It serves the purpose of
     *  recording the total duration of playtime (for each player).
     */
    public static void startTimer() {
        startTime = System.currentTimeMillis();
    }

    /**
     * This method is exclusively used when a player logs in. It serves the purpose of
     *  recording the total duration of playtime (for each player).
     */
    public static void endTimer() {
        endTime = System.currentTimeMillis();
    }

    // Getters
    public static long getStartTime() { return startTime; }
    public static long getEndTime() { return endTime; }

    // Use as driver for application
    public static void main(String[] args) {
        new NavigationControl();
    }
}