/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cache;

import inet.cache.management.Cache;
import java.util.HashMap;
import java.util.Map;
import inet.dao.CategoryDAO;
import inet.dao.GameDAO;
import inet.entities.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TUTL
 */
public class CategoryCache extends Cache {

    private Map<String, Category> categoryCache = new HashMap<String, Category>();
    //private Map<String,List<Category>> cacheCategories = new HashMap<String, List<Category>>();
    private List<Category> categories = new ArrayList();

    public Category getByCode(String code) throws Exception {
        Category category = categoryCache.get(code);
        synchronized (categoryCache) {
            if (category == null) {
                category = CategoryDAO.getInstance().getByCode(code);
                if (category != null) {
                    category.setTotalGame(GameDAO.getInstance().countGameByCategory(category.getId(), ""));
                    categoryCache.put(code, category);
                }

            }
            return category;
        }
    }

    public Category getById(int id) {
        //String key = "categoryId_"+id;
        Category category = categoryCache.get(id + "");
        synchronized (categoryCache) {
            if (category == null) {
                try {
                    category = CategoryDAO.getInstance().getById(id);
                    if (category != null) {
                        categoryCache.put(id + "", category);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CategoryCache.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return category;
        }
    }

    public List<Category> getAll() throws Exception {
        synchronized (categories) {
            if (categories.isEmpty()) {
                categories = CategoryDAO.getInstance().find(Category.ACTIVE);
                if (categories != null && !categories.isEmpty()) {
                    for (Category c : categories) {
                        c.setTotalGame(GameDAO.getInstance().countGameByCategory(c.getId(), ""));
                        if (!categoryCache.containsKey(c.getId() + "")) {
                            categoryCache.put(c.getId() + "", c);
                        }
                        if (!categoryCache.containsKey(c.getCode())) {
                            categoryCache.put(c.getCode(), c);
                        }
                    }
                }
            }
        }
        return categories;
    }

    @Override
    public void clearCache() {
        synchronized (this) {
            categoryCache.clear();
            categories.clear();
        }
    }

}
