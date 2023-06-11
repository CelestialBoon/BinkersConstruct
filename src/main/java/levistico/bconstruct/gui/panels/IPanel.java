package levistico.bconstruct.gui.panels;

public interface IPanel {
    public void drawPanel(int screenWidth, int screenHeight, int mouseX, int mouseY);
    public void drawTooltip(int screenWidth, int screenHeight, int mouseX, int mouseY);

    void mouseClicked(int screenWidth, int screenHeight, int mouseX, int mouseY, int button);

    boolean keyTyped(char c, int i);
}
