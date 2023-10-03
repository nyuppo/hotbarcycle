package com.github.nyuppo;

import static com.github.nyuppo.HotbarCycleClient.isRowEnabled;
import static com.github.nyuppo.HotbarCycleClient.isColumnEnabled;

public class SwapMap 
{
	/**
	 * Simulates the cycling of a slot by 1 space up.
	 * @param src The slot's row index.
	 * @return The next enabled row, i.e the resulting row after a single cycle.
	 * Or `src` if the row is disabled
	 */
	static public int	GetRollover(int src){
		if (!isRowEnabled(src))
			return src;

		for (int offset=1; offset<4; ++offset) {
			int dst = (src + offset) % 4;
			if (isRowEnabled(dst))
				return dst;
		}

		return src;
	}

	/**
	 * Simulates the cycling of a column by 1 space up.
	 * @return For each row, points to next enabled row.  Disabled rows point to
	 * themselves.
	 */
	static public int[]	GetRolloverMap(){
		int [] rolloverMap = new int[4];

		for (int src=0; src<4; ++src)
			rolloverMap[src] = GetRollover(src);

		return rolloverMap;
	}


	/**
	 * Simulates the cycling of any column by an arbitrary amount.
	 * @param direction The amount  of  cycles.  Positive  values  cycle upward,
	 * negative values cycle downward.
	 * @return For each row index,  points to  the resulting row.  Disabled rows
	 * point to themselve.
	 */
	static public int[]	GetRowSwapMap(int direction){
		int[] rolloverMap = GetRolloverMap();
		int[] swapMap = new int[4];

		int swapableRowCount = 0;
		for (int i=0; i<4; ++i)
			if (isRowEnabled(i))
				swapableRowCount++;
		direction %= swapableRowCount;
		if (direction < 0)
			direction += swapableRowCount;

		for (int i=0; i<4; ++i){
			swapMap[i] = i;
			for (int n=0; n<direction; ++n)
				swapMap[i] = rolloverMap[swapMap[i]];
		}

		return swapMap;
	}

	/**
	 * Simulates the cycling of a whole inventory by an arbitrary amount.
	 * @param direction The amount  of  cycles.  Positive  values  cycle upward,
	 * negative values cycle downward.
	 * @return For each slot index, points to the resulting slot. Disabled slots
	 * point to themselves.
	 */
	static public int[]	GetInventorySwapMap(int direction){
		int[] swapMap = new int[4*9];
		int[] rowSwap = GetRowSwapMap(direction);

		for (int i=0; i<swapMap.length; ++i){
			int x = i % 9;
			int y = i / 9;

			if (!isColumnEnabled(x) || !isRowEnabled(y))
				swapMap[i] = i;
			else 
				swapMap[i] = (rowSwap[y] * 9) + x;
		}

		return swapMap;
	}
}
