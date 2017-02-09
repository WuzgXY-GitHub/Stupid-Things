package furgl.stupidThings.common.item;

import java.util.ArrayList;

import furgl.stupidThings.common.StupidThings;
import furgl.stupidThings.common.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("deprecation")
public class ModItems {
	public static ArrayList<Item> allItems;

	public static Item anvilBackpack;
	public static Item balloonDeflated;
	public static Item balloon;
	public static Item balloonWater;
	public static Item balloonLava;
	public static Item paperBagHat;

	public static void preInit() {		
		allItems = new ArrayList<Item>();

		anvilBackpack = registerItem(new ItemAnvilBackpack(), "anvil_backpack", true, true);
		paperBagHat = registerItem(new ItemPaperBagHat(), "paper_bag_hat", true, true);

		balloon = registerItem(new ItemBalloon(), "balloon", true, true);
		balloonDeflated = registerItem(new ItemBalloonDeflated(), "balloon_deflated", true, true);
		balloonWater = registerItem(new ItemBalloonLiquid.ItemBalloonWater(), "balloon_water", true, true);
		balloonLava = registerItem(new ItemBalloonLiquid.ItemBalloonLava(), "balloon_lava", true, true);
	}

	public static void registerRenders() {
		for (Item item : allItems)
			registerRender(item, 0);

		Item[] items = new Item[] {balloon, balloonDeflated, balloonWater, balloonLava};

		for (Item item : items) {
			if (item != null) {			
				for (int i=1; i<16; i++) 
					registerRender(item, i);

				Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
					@Override
					public int getColorFromItemstack(ItemStack stack, int tintIndex) {
						return EnumDyeColor.byMetadata(stack.getMetadata()).getMapColor().colorValue;
					}
				}, item);
			}
		}
	}

	public static Item registerItem(Item item, String unlocalizedName, boolean addToTab, boolean checkIfDisabled) {
		item.setUnlocalizedName(unlocalizedName);
		if (checkIfDisabled && !Config.isNameEnabled(I18n.translateToLocal("item."+unlocalizedName+".name").replace("White ", "")))
			return null;
		item.setRegistryName(StupidThings.MODID, unlocalizedName);
		if (addToTab) {
			item.getSubItems(item, StupidThings.tab, StupidThings.tab.orderedStacks);
			item.setCreativeTab(StupidThings.tab);
		}
		GameRegistry.register(item);
		allItems.add(item);
		return item;
	}

	private static void registerRender(Item item, int meta) {
		if (item != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, 
					new ModelResourceLocation(StupidThings.MODID+":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}