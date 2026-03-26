import java.awt.event.ActionListener;

public interface Screen extends ActionListener{
    void showScreen();
    Screen moveToNextScreen(String screenToMoveTo);
}