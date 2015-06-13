package com.maxwell.zombie.client.render.entity;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;
import com.maxwell.zombie.client.model.ModelMaxZombie;
import com.maxwell.zombie.entity.EntityMaxZombie;

@SideOnly(Side.CLIENT)
public class RenderMaxZombie extends RenderBiped
{
	private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
	private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
	private final ModelBiped currentModel;
	private final ModelZombieVillager zombieVillager;
	private final List list1;
	private final List list2;

	public RenderMaxZombie(RenderManager rm)
	{
		super(rm, new ModelMaxZombie(), 0.5F, 1.0F);
		LayerRenderer layerrenderer = (LayerRenderer) this.layerRenderers.get(0);
		currentModel = modelBipedMain;
		zombieVillager = new ModelZombieVillager();
		addLayer(new LayerHeldItem(this));
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
		{
			@Override
			protected void func_177177_a()
			{
				this.field_177189_c = new ModelMaxZombie(0.5F, true);
				this.field_177186_d = new ModelMaxZombie(1.0F, true);
			}
		};
		addLayer(layerbipedarmor);
		list1 = Lists.newArrayList(this.layerRenderers);

		if (layerrenderer instanceof LayerCustomHead)
		{
			removeLayer(layerrenderer);
			addLayer(new LayerCustomHead(zombieVillager.bipedHead));
		}

		removeLayer(layerbipedarmor);
		addLayer(new LayerVillagerArmor(this));
		list2 = Lists.newArrayList(this.layerRenderers);
	}

	public void func_180579_a(EntityMaxZombie zombie, double x, double y, double z, float facing, float partialTicks)
	{
		this.getRenderLayer(zombie);
		super.doRender(zombie, x, y, z, facing, partialTicks);
	}

	protected ResourceLocation func_180578_a(EntityMaxZombie p_180578_1_)
	{
		return zombieTextures;
	}

	private void getRenderLayer(EntityMaxZombie z)
	{
		this.mainModel = this.currentModel;
		this.layerRenderers = this.list2;

		this.modelBipedMain = (ModelBiped) this.mainModel;
	}

	protected void rotateCorpse(EntityMaxZombie z, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		super.rotateCorpse(z, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return this.func_180578_a((EntityMaxZombie) entity);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity>) and this method has signature
	 * public void func_76986_a(T entity, double d, double d1, double d2, float
	 * f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		this.func_180579_a((EntityMaxZombie) entity, x, y, z, p_76986_8_, partialTicks);
	}

	protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		this.rotateCorpse((EntityMaxZombie) p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity>) and this method has signature
	 * public void func_76986_a(T entity, double d, double d1, double d2, float
	 * f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		this.func_180579_a((EntityMaxZombie) entity, x, y, z, p_76986_8_, partialTicks);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.func_180578_a((EntityMaxZombie) entity);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity>) and this method has signature
	 * public void func_76986_a(T entity, double d, double d1, double d2, float
	 * f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(EntityMaxZombie entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		this.func_180579_a((EntityMaxZombie) entity, x, y, z, p_76986_8_, partialTicks);
	}
}