package levistico.bconstruct.utils;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class AcceptRule {

    public static AcceptRule acceptsOnlyIds(Integer... ids) {
        return new AcceptRule(stack -> {
           return Arrays.asList(ids).contains(stack.itemID);
        });
    }
    public static AcceptRule acceptsOnlyClasses(Class... classes) {
        return new AcceptRule(stack -> {
            Item item = stack.getItem();
            return Arrays.stream(classes).anyMatch(aClass -> aClass.isInstance(item));
        });
    }

    private final Function<ItemStack, Boolean> rule;

    private AcceptRule(Function<ItemStack, Boolean> rule) {
        this.rule = rule;
    }

    public boolean accepts(ItemStack itemstack) {
        return rule.apply(itemstack);
    }
}
