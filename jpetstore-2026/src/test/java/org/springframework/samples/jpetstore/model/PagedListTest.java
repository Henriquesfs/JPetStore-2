package org.springframework.samples.jpetstore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PagedListTest {

	@Test
	public void emptyListHasOneEmptyPage() {
		PagedList<String> list = new PagedList<>(new ArrayList<>(), 4);
		assertEquals(1, list.getPageCount());
		assertTrue(list.isFirstPage());
		assertTrue(list.isLastPage());
		assertTrue(list.getPageList().isEmpty());
	}

	@Test
	public void navigatesBetweenPagesWithinBounds() {
		PagedList<Integer> list = new PagedList<>(Arrays.asList(1, 2, 3, 4, 5, 6), 4);
		assertEquals(2, list.getPageCount());
		list.previousPage();
		assertEquals(0, list.getPage());
		list.nextPage();
		list.nextPage();
		assertEquals(1, list.getPage());
		assertEquals(Arrays.asList(5, 6), list.getPageList());
		assertFalse(list.isFirstPage());
		assertTrue(list.isLastPage());
	}

	@Test
	public void pageIsClampedWhenSourceShrinks() {
		List<Integer> source = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
		PagedList<Integer> list = new PagedList<>(source, 4);
		list.nextPage();
		source.remove(4);
		assertEquals(0, list.getPage());
		assertEquals(4, list.getPageList().size());
	}
}
