package levistico.bconstruct.utils;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public final class Utils {

    public static final float degToRad = 3.141593F / 180.0F;
    public static float yawFactor(float pitchSin) {
        return MathHelper.sqrt_float(1- pitchSin*pitchSin);
    }
    public static ItemStack[] emptyInventoryCrafting (InventoryCrafting inv) {
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            inv.setInventorySlotContents(i, null);
        }
//        return new ItemStack[inv.getSizeInventory()];
        return null;
    }

    public static ItemStack[] decreaseAllInventoryBy(InventoryCrafting inv, int amount) {
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            inv.decrStackSize(i, amount);
        }
//        return new ItemStack[inv.getSizeInventory()];
        return null;
    }

    public static String translateItemName(Item item) {
        return StringTranslate.getInstance().translateKey(item.getItemName() + ".name");
    }

    public static <T> T[] concatTwoArrays(T[] a1, T[] a2) {
        T[] result = Arrays.copyOf(a1, a1.length + a2.length);
        System.arraycopy(a2, 0, result, a1.length, a2.length);
        return result;
    }

    public static Integer sumInt(Stream<Integer> numbers) {
        return numbers.reduce(0, (i1, acc) -> i1 + acc, (i1, i2) -> i1 + i2);
    }

    public static Float sumFloat(Stream<Float> numbers) {
        return numbers.reduce(0f, (i1, acc) -> i1 + acc, (i1, i2) -> i1 + i2);
    }

    public static Float average(Stream<Float> numbers, Integer count) {
        return sumFloat(numbers) / count;
    }

    public static net.minecraft.src.helper.Color colorFromString(String hex) {
        java.awt.Color c = java.awt.Color.decode(hex);
        return new net.minecraft.src.helper.Color().setRGB(c.getRed(), c.getGreen(), c.getBlue());
    }

    public static int byteToInt(byte b) {
        if (b < 0) return 255+b;
        else return b;
    }

    public static <T> void setAt(ArrayList<T> array, int index, T element) {
        int newSize = index+1;
        if (array.size() < newSize) {
            for(int i = array.size(); i<newSize; i++) {
                array.add(null);
            }
        }
        array.set(index, element);
    }

    public static boolean isStringEmpty(String s) {
        return s == null || s.equals("");
    }
}
