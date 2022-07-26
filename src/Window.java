import javax.swing.*;

public class Window extends JFrame {
    MainView mainView;

    Window() {
        mainView = new MainView();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainView);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
