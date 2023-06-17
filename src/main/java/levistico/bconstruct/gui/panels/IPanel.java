package levistico.bconstruct.gui.panels;

public interface IPanel {
    void drawPanel(int screenWidth, int screenHeight, int mouseX, int mouseY);
    void tryDrawTooltip(int screenWidth, int screenHeight, int mouseX, int mouseY);
    void tryMouseClicked(int screenWidth, int screenHeight, int mouseX, int mouseY, int button);
    boolean keyTyped(char c, int i);
}
