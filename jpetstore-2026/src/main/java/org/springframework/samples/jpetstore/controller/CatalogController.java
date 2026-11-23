package org.springframework.samples.jpetstore.controller;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.samples.jpetstore.model.Category;
import org.springframework.samples.jpetstore.model.Item;
import org.springframework.samples.jpetstore.model.PagedList;
import org.springframework.samples.jpetstore.model.Product;
import org.springframework.samples.jpetstore.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CatalogController {

	private static final String CATEGORY = "catalogCategory";

	private static final String PRODUCT_LIST = "catalogProductList";

	private static final String PRODUCT = "catalogProduct";

	private static final String ITEM_LIST = "catalogItemList";

	private static final String SEARCH_RESULT = "searchProductList";

	private final CatalogService catalog;

	@RequestMapping("/shop/index.do")
	public String index() {
		return "index";
	}

	@RequestMapping("/shop/viewCategory.do")
	public String viewCategory(@RequestParam(required = false) String categoryId,
			@RequestParam(required = false) String page, HttpSession session, Model model) {
		if (categoryId != null) {
			Category category = catalog.getCategory(categoryId);
			PagedList<Product> productList = ViewSupport.pagedList(catalog.getProductListByCategory(categoryId));
			session.setAttribute(CATEGORY, category);
			session.setAttribute(PRODUCT_LIST, productList);
			return showCategory(category, productList, model);
		}
		Category category = (Category) session.getAttribute(CATEGORY);
		PagedList<?> productList = (PagedList<?>) session.getAttribute(PRODUCT_LIST);
		if (category == null || productList == null) {
			throw new IllegalStateException("Cannot find pre-loaded category and product list");
		}
		ViewSupport.turnPage(productList, page);
		return showCategory(category, productList, model);
	}

	private static String showCategory(Category category, PagedList<?> productList, Model model) {
		model.addAttribute("category", category);
		model.addAttribute("productList", productList);
		return "Category";
	}

	@RequestMapping("/shop/viewProduct.do")
	public String viewProduct(@RequestParam(required = false) String productId,
			@RequestParam(required = false) String page, HttpSession session, Model model) {
		if (productId != null) {
			session.setAttribute(ITEM_LIST, ViewSupport.pagedList(catalog.getItemListByProduct(productId)));
			session.setAttribute(PRODUCT, catalog.getProduct(productId));
		}
		PagedList<?> itemList = (PagedList<?>) session.getAttribute(ITEM_LIST);
		if (productId == null) {
			ViewSupport.turnPage(itemList, page);
		}
		model.addAttribute("itemList", itemList);
		model.addAttribute("product", session.getAttribute(PRODUCT));
		return "Product";
	}

	@RequestMapping("/shop/viewItem.do")
	public String viewItem(@RequestParam String itemId, Model model) {
		Item item = catalog.getItem(itemId);
		model.addAttribute("item", item);
		model.addAttribute("product", item.getProduct());
		return "Item";
	}

	@RequestMapping("/shop/searchProducts.do")
	public String searchProducts(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String page, HttpSession session, Model model) {
		if (keyword != null) {
			if (!StringUtils.hasLength(keyword)) {
				return ViewSupport.error(model, "Please enter a keyword to search for, then press the search button.");
			}
			session.setAttribute(SEARCH_RESULT, ViewSupport.pagedList(catalog.searchProductList(keyword.toLowerCase())));
		}
		PagedList<?> productList = (PagedList<?>) session.getAttribute(SEARCH_RESULT);
		if (productList == null) {
			return ViewSupport.error(model, "Your session has timed out. Please start over again.");
		}
		if (keyword == null) {
			ViewSupport.turnPage(productList, page);
		}
		model.addAttribute("productList", productList);
		return "SearchProducts";
	}
}
