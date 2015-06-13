package com.maxwell.zombie.entity.ai;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class EntityAIDig extends EntityAIBase
{

	private static final boolean PLAYER_ONLY = false;
	private static final boolean LEAVE_DROPS = false;
	private static final boolean NEEDS_TOOL = false;
	protected EntityLiving theEntity;
	private double startX;
	private double startY;
	private double startZ;
	private int blockX;
	private int blockY;
	private int blockZ;
	private Block targetBlock;
	private float blockDamage;

	public EntityAIDig(EntityLiving entity)
	{
		this.theEntity = entity;
		setMutexBits(8);
	}

	public EntityLivingBase target()
	{
		return this.theEntity.getAttackTarget();
	}

	public Random random()
	{
		return this.theEntity.getRNG();
	}

	public double distance()
	{
		EntityLivingBase target = target();
		return target == null ? Double.POSITIVE_INFINITY : this.theEntity.getDistanceSqToEntity(target);
	}

	public boolean shouldExecute()
	{
		EntityLivingBase target = target();
		if ((this.theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) && (target != null))
		{
			return targetHighestObstruction(target);
		}
		return false;
	}

	public boolean continueExecuting()
	{
		return ((this.theEntity.worldObj.getBlockState(new BlockPos(this.blockX, this.blockY, this.blockZ)).getBlock() == this.targetBlock) && (this.theEntity.getDistanceSq(this.startX, this.startY,
				this.startZ) <= 4.0D));
	}

	public void startExecuting()
	{
		this.startX = this.theEntity.posX;
		this.startY = this.theEntity.posY;
		this.startZ = this.theEntity.posZ;
		this.blockDamage = 0.0F;
	}

	public void updateTask()
	{
		this.theEntity.getLookHelper().setLookPosition(this.blockX, this.blockY, this.blockZ, 30.0F, 30.0F);
		BlockPos pos = new BlockPos(this.blockX, this.blockY, this.blockZ);
		if (random().nextInt(20) == 0)
		{
			this.theEntity.worldObj.playAuxSFX(1010, pos, 0);
			this.theEntity.swingItem();
			this.blockDamage += 0.1;// BlockHelper.getDamageAmount(this.targetBlock,
									// this.theEntity, this.theEntity.worldObj,
									// this.blockX, this.blockY, this.blockZ);
		}

		if (this.blockDamage >= 1.0F)
		{
			this.theEntity.worldObj.setBlockToAir(pos);
			this.theEntity.worldObj.playAuxSFX(2001, pos, Block.getIdFromBlock(this.targetBlock));
			this.theEntity.swingItem();
			this.blockDamage = 0.0F;
		}

		this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), pos, (int) (this.blockDamage * 10.0F) - 1);
	}

	public void resetTask()
	{
		this.blockDamage = 0.0F;
		this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), new BlockPos(this.blockX, this.blockY, this.blockZ), -1);
	}

	private boolean targetHighestObstruction(EntityLivingBase target)
	{
		PathEntity path = this.theEntity.getNavigator().getPath();

		if ((path == null) || (!path.isFinished()))
			return false;

		AxisAlignedBB boundingBox = this.theEntity.getEntityBoundingBox();

		double dX = target.posX - this.theEntity.posX;
		double dY = target.posY - this.theEntity.posY;
		double dZ = target.posZ - this.theEntity.posZ;
		double v = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
		boundingBox = boundingBox.addCoord(dX / v, dY / v, dZ / v);

		ArrayList<AxisAlignedBB> bbList = new ArrayList();
		int minX = MathHelper.floor_double(boundingBox.minX);
		int maxX = MathHelper.floor_double(boundingBox.maxX);
		int minY = MathHelper.floor_double(boundingBox.minY);
		int maxY = MathHelper.floor_double(boundingBox.maxY);
		int minZ = MathHelper.floor_double(boundingBox.minZ);
		int maxZ = MathHelper.floor_double(boundingBox.maxZ);

		if (this.theEntity.worldObj.isAreaLoaded(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ)))
		{

			for (int y = maxY; y >= minY; y--)
			{
				for (int x = minX; x <= maxX; x++)
				{
					for (int z = minZ; z <= maxZ; z++)
					{
						Block block = this.theEntity.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock();
						if (block != null)
						{
							block.addCollisionBoxesToList(this.theEntity.worldObj, new BlockPos(x, y, z), null, boundingBox, bbList, this.theEntity);
							if (!bbList.isEmpty())
							{
								AxisAlignedBB test = bbList.get(random().nextInt(bbList.size()));
								int testX = MathHelper.floor_double((test.maxX + test.minX)/2);
								int testY = MathHelper.floor_double((test.maxY + test.minY)/2);
								int testZ = MathHelper.floor_double((test.maxZ + test.minZ)/2);
								
								
								if (tryTargetBlock(block, testX, testY, testZ))
								{
									return true;
								}
								bbList.clear();
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean tryTargetBlock(Block block, int x, int y, int z)
	{
		if (block != net.minecraft.init.Blocks.bedrock && block != net.minecraft.init.Blocks.air)
		// && (BlockHelper.shouldDamage(block, this.theEntity, false,
		// this.theEntity.worldObj, x, y, z)))
		{
			this.blockX = x;
			this.blockY = y;
			this.blockZ = z;
			this.targetBlock = block;
			return true;
		}
		return false;
	}

}
