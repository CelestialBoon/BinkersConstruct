package levistico.bconstruct.tools.actions;

import levistico.bconstruct.utils.Utils;

import java.util.ArrayList;

public class ToolActions {
    public static final HarvestAction NONE = new HarvestAction();
    public static final HarvestAction megaChop = new HarvestAction();
    public static final HarvestAction veinMine = new HarvestAction();
    public static final HarvestAction mine = new HarvestAction();
    public static final HarvestAction pickadzeMine = new PickadzeHarvestAction();

    public static final AttackAction attack = new AttackAction();
    public static final AttackAction aoeAttack = new AttackAction();
}
