/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cache;

import inet.cache.management.Cache;
import java.util.Map;
import inet.dao.CategoryDAO;
import inet.entities.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author TUTL
 */
public class CategoryCache extends Cache {

    private Map<String, Category> datas = new ConcurrentHashMap<String, Category>();
    private List<Category> categories = new ArrayList();

    public Category getByCode(String code) throws Exception {
        Category category = datas.get(code);
        synchronized (datas) {
            if (category == null) {
                CategoryDAO dao = new CategoryDAO();
                category = dao.getByCode(code);
                if (category != null) {
                    datas.put(code + "", category);
                }
            }
            return category;
        }
    }

    public Category getById(int id) throws Exception {
        Category category = datas.get(id + "");
        synchronized (datas) {
            if (category == null) {
                CategoryDAO dao = new CategoryDAO();
                category = dao.getById(id);
                if (category != null) {
                    datas.put(id + "", category);
                }
            }
            return category;
        }
    }

    public List<Category> getAll() throws Exception {
        synchronized (categories) {
            if (categories.isEmpty()) {
                CategoryDAO dao = new CategoryDAO();
                categories = dao.find();
                if (!categories.isEmpty()) {
                    for (Category item : categories) {
                        datas.put(String.valueOf(item.getId()), item);
                        datas.put(item.getCode(), item);
                    }
                }
            }
        }
        return categories;
    }

    @Override
    public void clearCache() {
        synchronized (this) {
            datas.clear();
            categories.clear();
        }
    }

}
