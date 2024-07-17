package net.chronos.cpd_stimulators.client.renderer;

import net.chronos.cpd_stimulators.entity.custom.Sanitar;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SanitarRenderer extends HumanoidMobRenderer<Sanitar, HumanoidModel<Sanitar>> {
    public SanitarRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(Sanitar entity) {
        return ResourceLocation.fromNamespaceAndPath("cpd_stimulators", "textures/entity/sanitar.png");
    }
}
