
import java.util.Collection;
import java.util.Arrays;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BasketTest {

	public static Collection<Object[]> BAGNUMS =
			Arrays.asList(new Object[][] { {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}, {12} });
	private int bagType;

	public BasketTest(int bagType) {
		super();
		this.bagType = bagType;
	}

	@Parameterized.Parameters(name = "Basket{index}")
	public static Collection<Object[]> bags() {
		return BAGNUMS;
	}
	
	private Basket makeBasket() {
		switch(this.bagType) {
			case 0: return new Basket0();
			case 1: return new Basket1();
			case 2: return new Basket2();
			case 3: return new Basket3();
			case 4: return new Basket4();
			case 5: return new Basket5();
			case 6: return new Basket6();
			case 7: return new Basket7();
			case 8: return new Basket8();
			case 9: return new Basket9();
			case 10: return new Basket10();
			case 11: return new Basket11();
			case 12: return new Basket12();
		}
		return null;
	}
	
	@Test
	public void addedHasCount1() {
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		assertEquals(1, basketToTest.count());
	}

	@Test
	public void largeArraySameItem() {
		Basket basketToTest = makeBasket();

		Item i = new Item("Pant",1);
		Item iCopy = new Item("Pant",1);

		for (int j = 0; j < 10000; j++) {
			basketToTest.addToBasket(i);
		}

		// Asserts

		// Count

		assertEquals(10000,basketToTest.count());

		// CountItem

		assertEquals(10000,basketToTest.countItem(i));
		assertEquals(10000,basketToTest.countItem(iCopy));
		assertEquals(0,basketToTest.countItem(null));

		// TotalCost / Remove

		assertEquals(10000,basketToTest.totalCost());
		assertEquals(true,basketToTest.removeFromBasket(i));
		assertEquals(true,basketToTest.removeFromBasket(iCopy));
		assertEquals(9998,basketToTest.totalCost());
		assertEquals(9998,basketToTest.count());
		assertEquals(9998,basketToTest.countItem(i));
		assertEquals(9998,basketToTest.countItem(iCopy));

		// Remove All From Basket

		assertEquals(true, basketToTest.removeAllFromBasket(i));
		assertEquals(false, basketToTest.removeAllFromBasket(i));
		assertEquals(0,basketToTest.count());

		for (int j = 0; j < 10000; j++) {
			basketToTest.addToBasket(i);
		}
		
		assertEquals(true, basketToTest.removeAllFromBasket(iCopy));
		assertEquals(false, basketToTest.removeAllFromBasket(iCopy));
		assertEquals(0,basketToTest.count());

		for (int j = 0; j < 10000; j++) {
			basketToTest.addToBasket(i);
		}

		basketToTest.empty();
		assertEquals(0,basketToTest.countItem(i));
		assertEquals(0,basketToTest.countItem(iCopy));
		assertEquals(0,basketToTest.countItem(null));
		assertEquals(0,basketToTest.count());

	}

	@Test
	public void largeArrayFiveItems() {
		Basket basketToTest = makeBasket();

		Item pant = new Item("Pant",1);
		Item pants = new Item("Pants",2);
		Item shorts = new Item("Shorts",1);
		Item socc = new Item("Socc",6);
		Item soccs = new Item("Soccs",3);

		int cost = 0;
		int count = 0;

		for (int i = 1; i <= 5; i++) {
			Item iterItem = null;
			switch(i) {
				case 1: iterItem = pant; break;		// 1000 pant
				case 2: iterItem = pants; break;	// 2000 pants
				case 3: iterItem = shorts; break;	// 3000 shorts
				case 4: iterItem = socc; break;		// 4000 socc
				case 5: iterItem = soccs; break;	// 5000 soccs
			}
			for (int j = 0; j < 1000*i; j++) {
				cost += iterItem.priceInCents;
				count++;
				basketToTest.addToBasket(iterItem);
			}
		}

		// Asserts

		// Count

		assertEquals(count,basketToTest.count());

		// CountItem

		assertEquals(1000,basketToTest.countItem(pant));
		assertEquals(2000,basketToTest.countItem(pants));
		assertEquals(3000,basketToTest.countItem(shorts));
		assertEquals(4000,basketToTest.countItem(socc));
		assertEquals(5000,basketToTest.countItem(soccs));
		assertEquals(0,basketToTest.countItem(null));

		// TotalCost / Remove

		assertEquals(cost,basketToTest.totalCost());
		assertEquals(true,basketToTest.removeFromBasket(pant));
		assertEquals(true,basketToTest.removeFromBasket(pants));
		assertEquals(true,basketToTest.removeFromBasket(shorts));
		assertEquals(true,basketToTest.removeFromBasket(socc));
		assertEquals(true,basketToTest.removeFromBasket(soccs));
		assertEquals(cost-13,basketToTest.totalCost());
		assertEquals(count-5,basketToTest.count());
		assertEquals(999,basketToTest.countItem(pant));
		assertEquals(3999,basketToTest.countItem(socc));

		// Remove All From Basket

		assertEquals(true, basketToTest.removeAllFromBasket(shorts));
		assertEquals(false, basketToTest.removeAllFromBasket(shorts));
		assertEquals(count-3004,basketToTest.count());
		assertEquals(0,basketToTest.countItem(null));

		basketToTest.empty();
		assertEquals(0,basketToTest.countItem(pant));
		assertEquals(0,basketToTest.countItem(socc));
		assertEquals(0,basketToTest.countItem(null));
		assertEquals(0,basketToTest.count());
		assertEquals(0,basketToTest.totalCost());

	}

	@Test
	public void largeArrayAllNull() {
		Basket basketToTest = makeBasket();

		for (int j = 0; j < 10000; j++) {
			basketToTest.addToBasket(null);
		}

		// Asserts

		// Count

		assertEquals(0,basketToTest.count());

		// CountItem

		assertEquals(0,basketToTest.countItem(null));

		// TotalCost / Remove

		assertEquals(0,basketToTest.totalCost());
		assertEquals(false,basketToTest.removeFromBasket(null));
		assertEquals(0,basketToTest.totalCost());
		assertEquals(0,basketToTest.count());

		// Remove All From Basket

		assertEquals(false, basketToTest.removeAllFromBasket(null));
		assertEquals(0,basketToTest.count());

		for (int j = 0; j < 10000; j++) {
			basketToTest.addToBasket(null);
		}
		
		assertEquals(false, basketToTest.removeAllFromBasket(null));
		assertEquals(0,basketToTest.count());

		for (int j = 0; j < 10000; j++) {
			basketToTest.addToBasket(null);
		}

		basketToTest.empty();
		assertEquals(0,basketToTest.countItem(null));
		assertEquals(0,basketToTest.count());

	}

	@Test
	public void largeArrayMixed() {
		Basket basketToTest = makeBasket();

		Item pant = new Item("Pant",1);
		Item pants = new Item("Pants",2);
		Item socc = new Item("Socc",6);
		Item soccs = new Item("Soccs",3);

		int cost = 0;
		int count = 0;

		for (int i = 1; i <= 5; i++) {
			Item iterItem = null;
			switch(i) {
				case 1: iterItem = pant; break;		// 1000 pant
				case 2: iterItem = pants; break;	// 2000 pants
				case 3: iterItem = null; break;		// 3000 null
				case 4: iterItem = socc; break;		// 4000 socc
				case 5: iterItem = soccs; break;	// 5000 soccs
			}
			for (int j = 0; j < 1000*i; j++) {
				if (iterItem != null) {
					cost += iterItem.priceInCents;
					count++;
				}
				basketToTest.addToBasket(iterItem);
			}
		}

		// Asserts

		// Count

		assertEquals(count,basketToTest.count());

		// CountItem

		assertEquals(1000,basketToTest.countItem(pant));
		assertEquals(2000,basketToTest.countItem(pants));
		assertEquals(4000,basketToTest.countItem(socc));
		assertEquals(5000,basketToTest.countItem(soccs));
		assertEquals(0,basketToTest.countItem(null));

		// TotalCost / Remove

		assertEquals(cost,basketToTest.totalCost());
		assertEquals(true,basketToTest.removeFromBasket(pant));
		assertEquals(true,basketToTest.removeFromBasket(pants));
		assertEquals(true,basketToTest.removeFromBasket(socc));
		assertEquals(true,basketToTest.removeFromBasket(soccs));
		assertEquals(cost-12,basketToTest.totalCost());
		assertEquals(count-4,basketToTest.count());
		assertEquals(999,basketToTest.countItem(pant));
		assertEquals(3999,basketToTest.countItem(socc));

		// Remove All From Basket

		assertEquals(true, basketToTest.removeAllFromBasket(soccs));
		assertEquals(false, basketToTest.removeAllFromBasket(soccs));
		assertEquals(count-5003,basketToTest.count());
		assertEquals(0,basketToTest.countItem(null));

		basketToTest.empty();
		assertEquals(0,basketToTest.countItem(pant));
		assertEquals(0,basketToTest.countItem(socc));
		assertEquals(0,basketToTest.countItem(null));
		assertEquals(0,basketToTest.count());
		assertEquals(0,basketToTest.totalCost());

	}

	@Test
	public void noContents() {
		Basket basketToTest = makeBasket();
		Item i = new Item("Pant",1);

		assertEquals(0,basketToTest.count());
		assertEquals(0,basketToTest.totalCost());
		assertEquals(0,basketToTest.countItem(i));
		assertEquals(0,basketToTest.countItem(null));
		basketToTest.empty();
		assertEquals(0,basketToTest.count());
		assertEquals(0,basketToTest.totalCost());
		assertEquals(0,basketToTest.countItem(i));
		assertEquals(0,basketToTest.countItem(null));

	}
}
