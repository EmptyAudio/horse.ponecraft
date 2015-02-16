package horse.ponecraft.earth;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCooktop extends BlockContainer
{
	private IIcon topIcon;
	private IIcon bottomIcon;
	private IIcon frontIcon;
	private IIcon backIcon;
	private IIcon sideIcon;
	
	public BlockCooktop(String unlocalizedName)
	{
		super(Material.iron);
		
		setHardness(5.0F);
		setStepSound(Block.soundTypeMetal);
		setBlockName(unlocalizedName);
		setCreativeTab(CreativeTabs.tabFood);
		setBlockTextureName(Earth.MODID + ":" + unlocalizedName);
		setResistance(30.0F);
		setHarvestLevel("pickaxe", 1);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		this.topIcon = reg.registerIcon(this.textureName + "_top");
		this.bottomIcon = reg.registerIcon(this.textureName + "_bottom");
		this.frontIcon = reg.registerIcon(this.textureName + "_front");
		this.backIcon = reg.registerIcon(this.textureName + "_back");
		this.sideIcon = reg.registerIcon(this.textureName + "_side");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
	{
		switch (side)
		{
		case 0:
			return this.bottomIcon;
		case 1:
			return this.topIcon;
		}
		
		switch (((TileEntityCooktop)blockAccess.getTileEntity(x, y, z)).getFacingDireciton())
		{
		case 2:
			switch (side)
			{
			case 2:
				return this.frontIcon;
			case 3:
				return this.backIcon;
			}
			break;
		case 3:
			switch (side)
			{
			case 3:
				return this.frontIcon;
			case 2:
				return this.backIcon;
			}
			break;
		case 4:
			switch (side)
			{
			case 4:
				return this.frontIcon;
			case 5:
				return this.backIcon;
			}
			break;
		case 5:
			switch (side)
			{
			case 5:
				return this.frontIcon;
			case 4:
				return this.backIcon;
			}
			break;
		}
		
		return this.sideIcon;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityCooktop();
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
	{
        int side = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        switch (side)
        {
		case 0:
        	((TileEntityCooktop)world.getTileEntity(x, y, z)).setFacingDireciton(2);
        	break;
		case 1:
        	((TileEntityCooktop)world.getTileEntity(x, y, z)).setFacingDireciton(5);
        	break;
		case 2:
        	((TileEntityCooktop)world.getTileEntity(x, y, z)).setFacingDireciton(3);
        	break;
		case 3:
        	((TileEntityCooktop)world.getTileEntity(x, y, z)).setFacingDireciton(4);
        	break;
        }
        
        world.markBlockForUpdate(x, y, z);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		Random rng = new Random();
        TileEntityCooktop tileentity = (TileEntityCooktop)world.getTileEntity(x, y, z);

        if (tileentity != null)
        {
            for (int i = 0; i < tileentity.getSizeInventory(); ++i)
            {
                ItemStack itemstack = tileentity.getStackInSlot(i);

                if (itemstack != null)
                {
                    float f = rng.nextFloat() * 0.8F + 0.1F;
                    float f1 = rng.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = rng.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
                    {
                        int j1 = rng.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)
                        {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        
                        entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        
                        float f3 = 0.05F;
                        
                        entityitem.motionX = (double)((float)rng.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)rng.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)rng.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are)
	{
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        
        if (tileEntity == null || player.isSneaking())
        {
            return false;
        }

        player.openGui(Earth.instance, 0, world, x, y, z);
        
        return true;
    }
}
