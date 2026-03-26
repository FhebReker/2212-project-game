import java.awt.event.*;
import javax.swing.*;

public interface Screen extends ActionListener{
    void showScreen();
    void moveToNextScreen(String screenToMoveTo);
    JFrame getFrame();
}