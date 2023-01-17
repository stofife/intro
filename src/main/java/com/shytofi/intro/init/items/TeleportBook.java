package com.shytofi.intro.init.items;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.crafting.conditions.TrueCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.List;
import java.util.Random;

public class TeleportBook extends Item {
    Random rand = new Random();
    public TeleportBook(Properties properties) {
        super(properties);
    }
    protected static BlockHitResult rayTrace(Level world, Player player, ClipContext.Fluid fluidMode) {
        double range = 15;

        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vector3d = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vector3d1 = vector3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return world.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.OUTLINE, fluidMode, player));
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player.experienceLevel >= 1) {
            player.giveExperienceLevels(-1);
            BlockHitResult ray = rayTrace(world, player, ClipContext.Fluid.NONE);
            BlockPos lookPos = ray.getBlockPos().relative(ray.getDirection());
            player.setPos(lookPos.getX(), lookPos.getY(), lookPos.getZ());
            player.getCooldowns().addCooldown(this, 80);
            player.fallDistance = 0F;
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
            for (int i = 0;i<21;i++){
                world.addParticle(ParticleTypes.SOUL, player.getX() + rand.nextDouble(), player.getY(), player.getZ() + rand.nextDouble(), 0d, 0.06d, 0d);
            }
        }

        return super.use(world, player, hand);
    }
    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(new TextComponent("§3Warps through space in the direction the user is looking in\n§6Costs 1 lvl to use"));

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public @NotNull Rarity getRarity(ItemStack stack) {
        return stack.isEnchanted() ? Rarity.EPIC: Rarity.UNCOMMON;
    }
}