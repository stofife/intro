package com.shytofi.intro.init;

import com.shytofi.intro.IntroMain;
import com.shytofi.intro.init.items.TeleportBook;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static class ModCreativeTab extends CreativeModeTab {
        private ModCreativeTab(int index, String label) {
            super(index, label);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(TELEPORT_BOOK.get());
        }
        public static final ModCreativeTab instance = new ModCreativeTab(CreativeModeTab.TABS.length, "intro");
    }


    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IntroMain.MOD_ID);
    public static final RegistryObject<Item> CRYSTAL = ITEMS.register("crystal",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.instance).stacksTo(16)));
    public static final RegistryObject<Item> CRYSTAL_BERRIES = ITEMS.register("crystal_berries",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.instance).food(
                    new FoodProperties.Builder().nutrition(6).saturationMod(4).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 100, 1), 0.75F).alwaysEat().build())));

    public static final RegistryObject<Item> TELEPORT_BOOK = ITEMS.register("teleport_book",
            () -> new TeleportBook(new Item.Properties().tab(ModCreativeTab.instance).stacksTo(1)));
}
