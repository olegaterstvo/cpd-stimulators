package net.chronos.cpd_stimulators.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.chronos.cpd_stimulators.entity.custom.Sanitar;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

//public class SanitarRenderer extends IllagerRenderer<Pillager> {
//    public SanitarRenderer(EntityRendererProvider.Context context) {
//        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.PILLAGER)), 0.5F);
//        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
//    }
//
//    public ResourceLocation getTextureLocation(Pillager p_115720_) {
//        return ResourceLocation.fromNamespaceAndPath("cpd_stimulators", "textures/entity/sanitar.png");
//    }
//}

public class SanitarRenderer extends HumanoidMobRenderer<Sanitar, HumanoidModel<Sanitar>> {
    public SanitarRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(Sanitar entity) {
        return ResourceLocation.fromNamespaceAndPath("cpd_stimulators", "textures/entity/sanitar.png");
    }

	@Override
	public void render(Sanitar entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		setModelStates(entity);
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	private void setModelStates(Sanitar entity) {
		model.setAllVisible(true);

		model.hat.visible = false;
		model.crouching = entity.isCrouching();
		HumanoidModel.ArmPose mainHandArmPose = getArmPose(entity, InteractionHand.MAIN_HAND);
		HumanoidModel.ArmPose offhandArmPose = getArmPose(entity, InteractionHand.OFF_HAND);

		if (mainHandArmPose.isTwoHanded())
			offhandArmPose = entity.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;

		if (entity.getMainArm() == HumanoidArm.RIGHT) {
			model.rightArmPose = mainHandArmPose;
			model.leftArmPose = offhandArmPose;
		}
		else {
			model.rightArmPose = offhandArmPose;
			model.leftArmPose = mainHandArmPose;
		}
	}

	private static HumanoidModel.ArmPose getArmPose(Sanitar entity, InteractionHand hand) {
		ItemStack stack = entity.getItemInHand(hand);

		if (stack.isEmpty())
			return HumanoidModel.ArmPose.EMPTY;

		if (entity.getUsedItemHand() == hand && entity.getUseItemRemainingTicks() > 0) {
			UseAnim useaction = stack.getUseAnimation();

			if (useaction == UseAnim.BLOCK)
				return HumanoidModel.ArmPose.BLOCK;

			if (useaction == UseAnim.BOW)
				return HumanoidModel.ArmPose.BOW_AND_ARROW;

			if (useaction == UseAnim.SPEAR)
				return HumanoidModel.ArmPose.THROW_SPEAR;

			if (useaction == UseAnim.CROSSBOW && hand == entity.getUsedItemHand())
				return HumanoidModel.ArmPose.CROSSBOW_CHARGE;

		}
		else if (!entity.swinging && stack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(stack)) {
			return HumanoidModel.ArmPose.CROSSBOW_HOLD;
		}

		return HumanoidModel.ArmPose.ITEM;
	}
}
