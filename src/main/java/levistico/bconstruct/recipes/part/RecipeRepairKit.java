package levistico.bconstruct.recipes.part;

/*public final class RecipeRepairKit extends BPartRecipe {
    protected Pair<Boolean, ItemStack> tryCrafting(InventoryCrafting inv) {
        Pair<Boolean, ItemStack> falseResult = new Pair<>(false, null);

        boolean empty = true;
        for (int i = 1; i < inv.getSizeInventory(); i++) {
            if (inv.getStackInSlot(i) != null) {
                empty = false;
                break;
            }
        }
        if (empty && inv.getStackInSlot(0) != null && inv.getStackInSlot(0).getItem() == Item.flint) {
            ItemStack kit = new ItemStack(BConstruct.repairKit);
            kit.tag.setInteger(RepairKit.MATERIAL, BToolMaterial.matToInt.get(BToolMaterial.flint));
            return new Pair<>(true, kit);
        }
        return falseResult;
    }
}*/
