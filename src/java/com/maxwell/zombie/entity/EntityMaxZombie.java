package com.maxwell.zombie.entity;

import java.util.Calendar;

import com.maxwell.zombie.entity.ai.EntityAIDig;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMaxZombie extends EntityMob
{

	/**
	 * The attribute which determines the chance that this mob will spawn
	 * reinforcements
	 */
	private final EntityAIBreakDoor breakDoor = new EntityAIBreakDoor(this);

	private boolean field_146076_bu = false;
	/** The width of the entity */
	private float zombieWidth = -1.0F;
	/** The height of the the entity. */
	private float zombieHeight;

	public EntityMaxZombie(World worldIn)
	{
		super(worldIn);
		// TODO Auto-generated constructor stub
		((PathNavigateGround) this.getNavigator()).func_179688_b(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, this.breakDoor);
		this.tasks.addTask(1, new EntityAIDig(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(2, this.field_175455_a);		
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.applyEntityAI();
		this.setSize(0.6F, 1.95F);
	}

	protected void applyEntityAI()
	{
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0D, true));
		this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.getDataWatcher().addObject(12, Byte.valueOf((byte) 0));
		this.getDataWatcher().addObject(13, Byte.valueOf((byte) 0));
		this.getDataWatcher().addObject(14, Byte.valueOf((byte) 0));
	}

	/**
	 * Returns the current armor value as determined by a call to
	 * InventoryPlayer.getTotalArmorValue
	 */
	public int getTotalArmorValue()
	{
		int i = super.getTotalArmorValue() + 2;

		if (i > 20)
		{
			i = 20;
		}

		return i;
	}

	public boolean func_146072_bX()
	{
		return this.field_146076_bu;
	}

	public void func_146070_a(boolean p_146070_1_)
	{
		if (this.field_146076_bu != p_146070_1_)
		{
			this.field_146076_bu = p_146070_1_;

			if (p_146070_1_)
			{
				this.tasks.addTask(1, this.breakDoor);
			} else
			{
				this.tasks.removeTask(this.breakDoor);
			}
		}
	}

	/**
	 * If Animal, checks if the age timer is negative
	 */
	public boolean isChild()
	{
		return this.getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	protected int getExperiencePoints(EntityPlayer player)
	{
		if (this.isChild())
		{
			this.experienceValue = (int) ((float) this.experienceValue * 2.5F);
		}

		return super.getExperiencePoints(player);
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	public void onLivingUpdate()
	{
		// if (this.worldObj.isDaytime() && !this.worldObj.isRemote &&
		// !this.isChild())
		// {
		// float f = this.getBrightness(1.0F);
		// BlockPos blockpos = new BlockPos(this.posX,
		// (double) Math.round(this.posY), this.posZ);
		//
		// if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F &&
		// this.worldObj.canSeeSky(blockpos))
		// {
		// boolean flag = true;
		// ItemStack itemstack = this.getEquipmentInSlot(4);
		//
		// if (itemstack != null)
		// {
		// if (itemstack.isItemStackDamageable())
		// {
		// itemstack.setItemDamage(itemstack.getItemDamage() +
		// this.rand.nextInt(2));
		//
		// if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
		// {
		// this.renderBrokenItemStack(itemstack);
		// this.setCurrentItemOrArmor(4, (ItemStack) null);
		// }
		// }
		//
		// flag = false;
		// }
		//
		// if (flag)
		// {
		// this.setFire(8);
		// }
		// }
		// }

		if (this.isRiding() && this.getAttackTarget() != null && this.ridingEntity instanceof EntityChicken)
		{
			((EntityLiving) this.ridingEntity).getNavigator().setPath(this.getNavigator().getPath(), 1.5D);
		}

		super.onLivingUpdate();
	}

	/**
	 * Called when the entity is attacked.
	 */
//	public boolean attackEntityFrom(DamageSource source, float amount)
//	{
//		if (super.attackEntityFrom(source, amount))
//		{
//			EntityLivingBase target = this.getAttackTarget();
//
//			if (target == null && source.getEntity() instanceof EntityLivingBase)
//			{
//				target = (EntityLivingBase) source.getEntity();
//			}
//
//			int i = MathHelper.floor_double(this.posX);
//			int j = MathHelper.floor_double(this.posY);
//			int k = MathHelper.floor_double(this.posZ);
//
//			if (target != null && this.worldObj.getDifficulty() == EnumDifficulty.HARD
//					&& (double) this.rand.nextFloat() < this.getEntityAttribute(reinforcementChance).getAttributeValue())
//			{
//
//				EntityMaxZombie newZombie = new EntityMaxZombie(this.worldObj);
//
//				for (int l = 0; l < 50; ++l)
//				{
//					int x = i + MathHelper.getRandomIntegerInRange(rand, 7, 40) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
//					int y = j + MathHelper.getRandomIntegerInRange(rand, 7, 40) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
//					int z = k + MathHelper.getRandomIntegerInRange(rand, 7, 40) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
//
//					BlockPos bp = new BlockPos(x, y - 1, z);
//					if (World.doesBlockHaveSolidTopSurface(worldObj, bp))
//					{
//						newZombie.setPosition((double) x, (double) y, (double) z);
//
//						if (worldObj.checkNoEntityCollision(newZombie.getEntityBoundingBox())
//								&& worldObj.getCollidingBoundingBoxes(newZombie, newZombie.getEntityBoundingBox()).isEmpty()
//								&& !worldObj.isAnyLiquid(newZombie.getEntityBoundingBox()))
//						{
//							worldObj.spawnEntityInWorld(newZombie);
//							newZombie.setAttackTarget(target);
//							break;
//						}
//					}
//				}
//			}
//			return true;
//		}
//		return false;
//	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		super.onUpdate();
	}

	public boolean attackEntityAsMob(Entity entity_)
	{
		boolean flag = super.attackEntityAsMob(entity_);

		if (flag)
		{
			int i = this.worldObj.getDifficulty().getDifficultyId();

			if (this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < (float) i * 0.3F)
			{
				entity_.setFire(2 * i);
			}
		}

		return flag;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound()
	{
		return "mob.zombie.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.zombie.death";
	}

	protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
	{
		this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	protected Item getDropItem()
	{
		// returns the held item or armor
		ItemStack heldItem = getHeldItem();
		if (heldItem != null)
		{
			return heldItem.getItem();
		} else
		{
			return Items.rotten_flesh;
		}
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	/**
	 * Makes entity wear random armor based on difficulty
	 */
	protected void addRandomArmor()
	{
		switch (this.rand.nextInt(3))
		{
		case 0:
			this.dropItem(Items.iron_ingot, 1);
			break;
		case 1:
			this.dropItem(Items.carrot, 1);
			break;
		case 2:
			this.dropItem(Items.potato, 1);
		}
	}

	protected void func_180481_a(DifficultyInstance p_180481_1_)
	{
		super.func_180481_a(p_180481_1_);

		if (this.rand.nextFloat() < (this.worldObj.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F))
		{
			int i = this.rand.nextInt(3);

			if (i == 0)
			{
				this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
			} else
			{
				this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
			}
		}
	}

	/**
	 * This method gets called when the entity kills another one.
	 */
	// public void onKillEntity(EntityLivingBase entityLivingIn)
	// {
	// super.onKillEntity(entityLivingIn);
	//
	// if ((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL ||
	// this.worldObj.getDifficulty() == EnumDifficulty.HARD)
	// && entityLivingIn instanceof EntityVillager)
	// {
	// if (this.worldObj.getDifficulty() != EnumDifficulty.HARD &&
	// this.rand.nextBoolean())
	// {
	// return;
	// }
	//
	// EntityMaxZombie MaxZombie = new EntityMaxZombie(this.worldObj);
	// MaxZombie.copyLocationAndAnglesFrom(entityLivingIn);
	// this.worldObj.removeEntity(entityLivingIn);
	// MaxZombie.func_180482_a(this.worldObj.getDifficultyForLocation(new
	// BlockPos(MaxZombie)), (IEntityLivingData) null);
	// MaxZombie.setVillager(true);
	//
	// if (entityLivingIn.isChild())
	// {
	// MaxZombie.setChild(true);
	// }
	//
	// this.worldObj.spawnEntityInWorld(MaxZombie);
	// this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016, new
	// BlockPos((int) this.posX, (int) this.posY, (int) this.posZ), 0);
	// }
	// }

	public float getEyeHeight()
	{
		float f = 1.74F;

		if (this.isChild())
		{
			f = (float) ((double) f - 0.81D);
		}

		return f;
	}

	protected boolean func_175448_a(ItemStack p_175448_1_)
	{
		return p_175448_1_.getItem() == Items.egg && this.isChild() && this.isRiding() ? false : super.func_175448_a(p_175448_1_);
	}

	public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
	{
		Object zombieGroupData = super.func_180482_a(p_180482_1_, p_180482_2_);
		float f = p_180482_1_.getClampedAdditionalDifficulty();
		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);

		this.func_146070_a(this.rand.nextFloat() < f * 0.1F);
		this.func_180481_a(p_180482_1_);
		this.func_180483_b(p_180482_1_);

		if (this.getEquipmentInSlot(4) == null)
		{
			Calendar calendar = this.worldObj.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
			{
				this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				this.equipmentDropChances[4] = 0.0F;
			}
		}

		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(
				new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
		double d0 = this.rand.nextDouble() * 1.5D * (double) f;

		if (d0 > 1.0D)
		{
			this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
		}

		return (IEntityLivingData) zombieGroupData;
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte p_70103_1_)
	{
		if (p_70103_1_ == 16)
		{
			if (!this.isSilent())
			{
				this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(),
						this.rand.nextFloat() * 0.7F + 0.3F, false);
			}
		} else
		{
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	protected boolean canDespawn()
	{
		return true;
	}


	/**
	 * Sets the width and height of the entity. Args: width, height
	 */
	protected final void setSize(float width, float height)
	{
		boolean flag = this.zombieWidth > 0.0F && this.zombieHeight > 0.0F;
		this.zombieWidth = width;
		this.zombieHeight = height;

		if (!flag)
		{
			this.multiplySize(1.0F);
		}
	}

	/**
	 * Multiplies the height and width by the provided float.
	 * 
	 * @param size
	 *            The size to multiply the height and width of the entity by.
	 */
	protected final void multiplySize(float size)
	{
		super.setSize(this.zombieWidth * size, this.zombieHeight * size);
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	public double getYOffset()
	{
		return super.getYOffset() - 0.5D;
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);

		if (cause.getEntity() instanceof EntityCreeper && ((EntityCreeper) cause.getEntity()).getPowered() && ((EntityCreeper) cause.getEntity()).isAIEnabled())
		{
			((EntityCreeper) cause.getEntity()).func_175493_co();
			this.entityDropItem(new ItemStack(Items.skull, 1, 2), 0.0F);
		}
	}

	class GroupData implements IEntityLivingData
	{
		public boolean isChild;
		public boolean isVillager;

		private GroupData(boolean setChild, boolean setVillager)
		{
			this.isChild = false;
			this.isVillager = false;
			this.isChild = setChild;
			this.isVillager = setVillager;
		}

		GroupData(boolean setChild, boolean setVillager, Object object)
		{
			this(setChild, setVillager);
		}
	}
}
