package net.chronos.cpd_stimulators.entity.custom;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.entity.ModEntities;
import net.chronos.cpd_stimulators.sound.ModSounds;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Sanitar extends Monster {
    public Sanitar(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setHealth(100f);
        setAbsorptionAmount(50f);
        setNoAi(false);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SANITAR.get(), Sanitar.createAttributes().build());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected boolean canPerformAttack(LivingEntity entity) {
                return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < (this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth()) && this.mob.getSensing().hasLineOfSight(entity);
            }
        });
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, .3f)
                .add(Attributes.MAX_HEALTH, 10f)
                .add(Attributes.ARMOR, 0f)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.FOLLOW_RANGE, 16f);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ModSounds.SANITAR_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return ModSounds.SANITAR_DEATH.get();
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        double rnd = Math.random();
        if (rnd < 0.25) {
            return ModSounds.SANITAR_MUTTER.get();
        }
        return null;
    }

    @Override
    public float getVoicePitch() {
        return 1f;
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    @Override
    public boolean shouldBeSaved() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public ItemStack getMainHandItem() {
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        return crossbow;
    }
}
