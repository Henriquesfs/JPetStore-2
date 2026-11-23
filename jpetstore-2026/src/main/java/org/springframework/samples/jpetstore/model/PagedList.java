package org.springframework.samples.jpetstore.model;

import java.io.Serializable;
import java.util.List;

public class PagedList<E> implements Serializable {

	private final List<E> source;

	private final int pageSize;

	private int page;

	public PagedList(List<E> source, int pageSize) {
		this.source = source;
		this.pageSize = pageSize;
	}

	public List<E> getSource() {
		return source;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getNrOfElements() {
		return source.size();
	}

	public int getPageCount() {
		return Math.max(1, (getNrOfElements() + pageSize - 1) / pageSize);
	}

	public int getPage() {
		page = Math.min(page, getPageCount() - 1);
		return page;
	}

	public boolean isFirstPage() {
		return getPage() == 0;
	}

	public boolean isLastPage() {
		return getPage() == getPageCount() - 1;
	}

	public void nextPage() {
		if (!isLastPage()) {
			page++;
		}
	}

	public void previousPage() {
		if (!isFirstPage()) {
			page--;
		}
	}

	public List<E> getPageList() {
		int first = getPage() * pageSize;
		return source.subList(first, Math.min(first + pageSize, getNrOfElements()));
	}
}
