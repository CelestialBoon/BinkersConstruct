package levistico.bconstruct.gui.panels;

public interface IPanel {
    void drawPanel(int screenWidth, int screenHeight, int mouseX, int mouseY);
    void tryDrawTooltip(int screenWidth, int screenHeight, int mouseX, int mouseY);
    boolean tryMouseClicked(int screenWidth, int screenHeight, int mouseX, int mouseY, int button);
    boolean tryKeyTyped(char c, int i, int width, int height, int mouseX, int mouseY);
    boolean tryMouseMovedOrUp(int width, int height, int mouseX, int mouseY, int mouseButton);
}
