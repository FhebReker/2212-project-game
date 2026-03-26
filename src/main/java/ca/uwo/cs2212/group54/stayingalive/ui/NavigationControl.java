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

    // Screen Dimensions
    public static int screenW = 800; //sets the screen width
    public static int screenH = 450; //sets the screen height

    /**
     * Updates the current screen by showing it and setting its size to defined dimensions
     * @author Fardin Abbassi
     */
    private static void updateScreen() {
        currentScreen.showScreen();
        currentScreen.getFrame().setSize(screenW, screenH);
    }
    /**
     * Sets current screen to the screen at the index of the list of screens and updates screen to match.
     * Disposes of the previous screen to prevent memory leak.
     * @author Fardin Abbassi
     * @param int screenToSet
     */
    public static void setCurrentScreen(int screenToSet) {
        if (currentScreen != null || screenToSet == 1) currentScreen.getFrame().dispose();
        currentScreen = listOfScreens[screenToSet];
        updateScreen();
    }
    
    /**
     * Constructor for NavigationControl. Initializes screens, then starts at the main menu screen.
     * @author Fardin Abbassi
     */
    public NavigationControl() {
        // Initialize screens (add as screens are implemented)
        MainMenuScreen mainMenu = new MainMenuScreen();
        LoginScreen loginScreen = new LoginScreen();

        // Add screens to list of screens (add as screens are implemented)
        listOfScreens[0] = mainMenu;
        listOfScreens[1] = loginScreen;

        // Start at main menu
        setCurrentScreen(0);
    }

    // Use as driver for application
    public static void main(String[] args) {
        new NavigationControl();
    }
}