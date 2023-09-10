package levistico.bconstruct.utils;

import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.ChatLine;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.util.helper.ChatAllowedCharacters;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class Utils {
    public static Random random = new Random();


    public static <T> T orElse(T item, T def) {
        if(item == null) return def; else return item;
    }
    /*public static ItemStack[] emptyInventoryCrafting (InventoryCrafting inv) {
        for(Integer i : Utils.range(0, inv.getSizeInventory())) {
            inv.setInventorySlotContents(i, null);
        }
//        return new ItemStack[inv.getSizeInventory()];
        return null;
    }*/

    public static ItemStack[] decreaseAllInventoryBy(InventoryCrafting inv, int amount) {
        for(Integer i : Utils.range(0, inv.getSizeInventory())) {
            inv.decrStackSize(i, amount);
        }
//        return new ItemStack[inv.getSizeInventory()];
        return null;
    }

    public static String translateKey(Item item) {
        return I18n.getInstance().translateKey(item.getKey() + ".name");
    }
    public static String translateKey(IHasTranslateKey item) {
        return I18n.getInstance().translateKey(item.getTranslateKey() + ".name");
    }


    public static <T> T[] concatTwoArrays(T[] a1, T[] a2) {
        T[] result = Arrays.copyOf(a1, a1.length + a2.length);
        System.arraycopy(a2, 0, result, a1.length, a2.length);
        return result;
    }
    public static Integer sumInt(Stream<Integer> numbers) {
        return numbers.reduce(0, Integer::sum, Integer::sum);
    }
    public static Float sumFloat(Stream<Float> numbers) {
        return numbers.reduce(0f, Float::sum, Float::sum);
    }
    public static Float average(Stream<Float> numbers, Integer count) {
        return sumFloat(numbers) / count;
    }
    public static int clamp(int min, int value, int max) {
        if(value<min) return min;
        else return Math.min(value, max);
    }
    public static int round(float f) {
        int i = (int)f;
        if (f-i > 0.5f) return i+1; else return i;
    }
    public static int roundRandom(float f) {
        int i = (int)f;
        if(random.nextFloat() < f - i) return i + 1; else return i;
    }
    public static final float degToRad = 3.141593F / 180.0F;
    public static float yawFactor(float pitchSin) {
        return MathHelper.sqrt_float(1- pitchSin*pitchSin);
    }

    public static Color colorFromString(String hex) {
        java.awt.Color c = java.awt.Color.decode(hex);
        return new Color().setRGB(c.getRed(), c.getGreen(), c.getBlue());
    }

    public static int ubyteToInt(byte b) {
        if (b < 0) return 255+b;
        else return b;
    }

    public static Vec3 getBlockToSide(int x, int y, int z, Side side) {
        if (side.getId() == 0) {
            --y;
        } else if (side.getId() == 1) {
            ++y;
        } else if (side.getId() == 2) {
            --z;
        } else if (side.getId() == 3) {
            ++z;
        } else if (side.getId() == 4) {
            --x;
        } else if (side.getId() == 5) {
            ++x;
        }
        return new Vec3(x, y, z);
    }

    public static Supplier<Collection<Item>> lootTable(Collection<Pair<Float, Item>> probabilies) {
        return () -> {
            List<Item> result = new ArrayList<>();
            for(Pair<Float,Item> prob : probabilies) {
                if(random.nextFloat() <= prob.first)
                    result.add(prob.second);
            }
            return result;
        };
    }

    public static <T> T[] concatArrays(T[] ...materials) {
        int size = Arrays.stream(materials).reduce(0, (s, ts) -> s + ts.length, Integer::sum);
        T[] result = Arrays.copyOf(materials[0], size);
        int len = materials[0].length;
        for(Integer i : Utils.range(1, materials.length)) {
            System.arraycopy(materials[i], 0, result, len, materials[i].length);
            len += materials[i].length;
        }
        return result;
    }

    public static Iterable<Integer> range(Integer fromInclusive, Integer toExclusive) {
        final int[] element = {fromInclusive};
        return new Iterable<Integer>() {
            @NotNull
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return element[0] < toExclusive;
                    }

                    @Override
                    public Integer next() {
                        return element[0]++;
                    }
                };
            }
        };
    }

    public static <T> Function<Object, Stream<T>> instancesOf(Class<T> cls) {
        return o -> cls.isInstance(o) ? Stream.of(cls.cast(o)) : Stream.empty();
    }

    public static float reduceProduct(Stream<Float> stream) {
        return stream.reduce(1f, (f1, f2) -> f1*f2, (f1, f2) -> f1*f2);
    }

    public static <T> List<T> reverseList(List<T> insertList) {
        List<T> resultList = new ArrayList<>();
        for(int i = insertList.size()-1; i>=0; i--) {
            resultList.add(insertList.get(i));
        }
        return resultList;
    }

    public static <U, T extends U> T setAt(ArrayList<U> array, int index, T element) {
        int newSize = index+1;
        if (array.size() < newSize) {
            for(Integer i : Utils.range(array.size(), newSize)) {
                array.add(null);
            }
        }
        array.set(index, element);
        return element;
    }
    public static <U, T extends U> T add(ArrayList<U> array, T element) {
        array.add(element);
        return element;
    }
    public static Iterator<Tag<?>> steamTag(ListTag list) {
        int len = list.tagCount();
        final int[] i = {0};
        return new Iterator<Tag<?>>() {
            @Override
            public boolean hasNext() {
                return i[0] < len;
            }

            @Override
            public Tag next() {
                return list.tagAt(i[0]++);
            }
        };
    }

    public static boolean isStringEmpty(String s) {
        return s == null || s.equals("");
    }


    public static String getHarvestTier(int miningLevel) {
        String material = I18n.getInstance().translateKey("mininglevel."+miningLevel);
        switch (miningLevel) {
            case 0: return "" + TextFormatting.BROWN + material;
            case 1: return "" + TextFormatting.GRAY + material;
            case 2: return "" + TextFormatting.LIGHT_GRAY + material;
            case 3: return "" + TextFormatting.CYAN + material;
        }
        return null;
    }
}
