package net.gobbob.mobends.core.client.event;

import net.gobbob.mobends.core.animatedentity.AnimatedEntity;
import net.gobbob.mobends.core.animatedentity.AnimatedEntityRegistry;
import net.gobbob.mobends.core.data.LivingEntityData;
import net.gobbob.mobends.core.mutators.Mutator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityRenderHandler
{

    @SubscribeEvent
    public void beforeLivingRender(RenderLivingEvent.Pre<? extends EntityLivingBase> event)
    {
        final EntityLivingBase living = event.getEntity();
        final AnimatedEntity<EntityLivingBase> animatedEntity = AnimatedEntityRegistry.instance.getForEntity(living);

        if (animatedEntity == null)
            return;

        final RenderLivingBase<?> renderer = event.getRenderer();
        final float pt = event.getPartialRenderTick();

        GlStateManager.pushMatrix();

        if (animatedEntity.isAnimated())
        {
            if (animatedEntity.applyMutation(renderer, living, pt))
            {
                Mutator mutator = animatedEntity.getMutator(renderer);
                LivingEntityData<EntityLivingBase> data = mutator.getData(living);
                animatedEntity.beforeRender(data, living, pt);
            }
        }
        else
        {
            animatedEntity.deapplyMutation(event.getRenderer(), living);
        }
    }

    @SubscribeEvent
    public void afterLivingRender(RenderLivingEvent.Post<? extends EntityLivingBase> event)
    {
        final AnimatedEntity<EntityLivingBase> animatedEntity = AnimatedEntityRegistry.instance.getForEntity(event.getEntity());

        if (animatedEntity == null)
            return;

        animatedEntity.afterRender(event.getEntity(), event.getPartialRenderTick());

        GlStateManager.popMatrix();
    }

}
