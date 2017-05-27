package furgl.stupidThings.common.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nullable;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;

public class ShapelessMatchingRecipe extends ShapelessRecipes 
{
	/** Is the ItemStack that you get when you craft the recipe. */
	private ItemStack recipeOutput;

	public ShapelessMatchingRecipe(ItemStack output, ItemStack... inputList) {
		super(output, new ArrayList<ItemStack>(Arrays.asList(inputList)));
		this.recipeOutput = output;
	}

	@Override
	@Nullable
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack result = this.recipeOutput.copy();
		for (int i = 0; i < inv.getHeight(); ++i)
			for (int j = 0; j < inv.getWidth(); ++j) {
				ItemStack stack = inv.getStackInRowAndColumn(j, i);
				if (stack != null && stack.getItem() instanceof ItemArmor) {
					result.setTagCompound(stack.getTagCompound());
					result.setItemDamage(stack.getItemDamage());
				}
			}
		return result;
	}
}
