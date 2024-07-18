package net.chronos.cpd_stimulators.entity.custom;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.entity.ModEntities;
import net.chronos.cpd_stimulators.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Sanitar extends Monster implements CrossbowAttackMob {
    private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(Sanitar.class, EntityDataSerializers.BOOLEAN);
    private UUID leadingEntity;

    public Sanitar(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SANITAR.get(), Sanitar.createAttributes().build());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 1, true, false, living -> living.hasEffect(MobEffects.BAD_OMEN)));
        this.goalSelector.addGoal(2, new RangedCrossbowAttackGoal<>(this, 5.0d, 16.0f));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FloatGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, .3f)
                .add(Attributes.MAX_HEALTH, 20f)
                .add(Attributes.ARMOR, 0f)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.FOLLOW_RANGE, 16f);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ModSounds.SANITAR_HURT.get();
    }

    @Override
    public Pose getPose() {
        return Pose.SHOOTING;
    }

    @Override
    public void setPose(Pose pose) {
        super.setPose(Pose.SHOOTING);
    }

    @Override
    public SoundEvent getDeathSound() {
        return ModSounds.SANITAR_DEATH.get();
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        double rnd = Math.random();
        if (rnd >= 0.25) return null;
        return ModSounds.SANITAR_MUTTER.get();
    }

    @Override
    public float getVoicePitch() {
        return 1f;
    }

    @Override
    protected float getSoundVolume() {
        return 1f;
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public Level level() {
        return super.level();
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);

        RegistryAccess registryAccess = level().registryAccess();
        HolderLookup.RegistryLookup<Enchantment> lookup = registryAccess.lookupOrThrow(Registries.ENCHANTMENT);
        Holder.Reference<Enchantment> multishot = lookup.get(Enchantments.MULTISHOT).get();
        Holder.Reference<Enchantment> quick_charge = lookup.get(Enchantments.QUICK_CHARGE).get();

        crossbow.enchant(multishot ,1);
        crossbow.enchant(quick_charge ,5);

        this.setItemInHand(InteractionHand.MAIN_HAND, crossbow);

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public void onCrossbowAttackPerformed() {
        CPDStimulators.LOGGER.info("shot_1");
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float p_82196_2_) {
        performCrossbowAttack(this, 2.0f);
        CPDStimulators.LOGGER.info("shot_2");
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder){
        super.defineSynchedData(pBuilder);
        pBuilder.define(IS_CHARGING_CROSSBOW, false);
    }

    public boolean isChargingCrossbow() {
        return entityData.get(IS_CHARGING_CROSSBOW);
    }

    @Override
    public void setChargingCrossbow(boolean p_213671_1_){
        entityData.set(IS_CHARGING_CROSSBOW, p_213671_1_);
    }
}
