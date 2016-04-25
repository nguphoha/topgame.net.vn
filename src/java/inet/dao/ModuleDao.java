/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.entities.Module;
import inet.util.Constants;
import inet.util.StringUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Admin
 */
public class ModuleDao extends AbstractDAO {

    private RowMapper<Module> rowMapper;

    public ModuleDao() throws Exception {

        super(Constants.DATABASE);

        rowMapper = new RowMapper<Module>() {

            Module module;

            @Override
            public Module map(ResultSet rs) throws Exception {
                module = new Module();
                module.setId(rs.getString("id"));
                module.setName(rs.getString("name"));
                module.setParentId(rs.getString("parent_id"));
                module.setStatus(rs.getInt("status"));
                module.setCreatedDate(rs.getTimestamp("date_create"));
                module.setUrl(rs.getString("url"));
                module.setPriority(rs.getString("priority"));
                return module;
            }
        };
    }

    public List<Module> getByAdminId(String adminID) throws Exception {
        String sql = "  SELECT  "
                + "             a.* "
                + "     FROM "
                + "             module a, "
                + "             admin_module b "
                + "     WHERE "
                + "             a.id = b.module_id AND b.admin_id = ? "
                + "     ORDER BY PRIORITY ASC";
        List params = new ArrayList();
        params.add(adminID);
        return find(sql, params, rowMapper);
    }

    public TreeNode getTree(String name, String status) throws Exception {

        String sql = "SELECT *"
                + "   FROM  module "
                + "   WHERE 1 = 1 "
                + "     AND parent_id is not null";

        List params = new ArrayList();

        if (!StringUtil.nvl(name, "").equals("")) {
            sql += " name LIKE CONCAT(?,'%')";
            params.add(name);
        }

        if (!StringUtil.nvl(status, "").equals("")) {
            sql += " AND status = ?";
            params.add(status);
        }

        sql += " UNION ALL SELECT * FROM module WHERE parent_id IS NULL ";

        List<Module> results = find(sql, params, rowMapper);
        TreeNode root = new DefaultTreeNode(new Module(), null);
        root.setExpanded(true);

        for (Module item : results) {
            if (StringUtil.nvl(item.getParentId(), "").equals("")) {
                TreeNode node = new DefaultTreeNode(item, root);
                node.setExpanded(true);
                buildChild(node, results);
            }
        }

        return root;
    }

    private void buildChild(TreeNode node, List<Module> datas) {
        Module parent = (Module) node.getData();
        for (Module item : datas) {
            if (!StringUtil.nvl(item.getParentId(), "").equals("") && item.getParentId().equals(parent.getId())) {
                TreeNode leaf = new DefaultTreeNode(item, node);
                leaf.setExpanded(true);
                buildChild(leaf, datas);
            }
        }
    }

    public List<Module> getDatas(String name, String status) throws Exception {

        String sql = "SELECT *"
                + "   FROM  module "
                + "   WHERE 1 = 1";

        List params = new ArrayList();

        if (!StringUtil.nvl(name, "").equals("")) {
            sql += " AND upper(name) LIKE CONCAT(upper(?),'%')";
            params.add(name);
        }

        if (!StringUtil.nvl(status, "").equals("")) {
            sql += " AND status = ?";
            params.add(name);
        }

        List<Module> results = find(sql, params, rowMapper);
        List<Module> parents = getParents(results);
        for (Module item : parents) {
            buildChild(item, results);
        }
        return parents;
    }

    private List<Module> getParents(List<Module> modules) {
        List<Module> parents = new ArrayList<Module>();
        for (Module item : modules) {
            if (StringUtil.nvl(item.getParentId(), "").equals("")) {
                parents.add(item);
            }
        }
        return parents;
    }

    private void buildChild(Module parent, List<Module> datas) {
        for (Module item : datas) {
            if (!StringUtil.nvl(item.getParentId(), "").equals("") && item.getParentId().equals(parent.getId())) {
                item.setParent(parent);
                buildChild(item, datas);
                parent.addChild(item);
            }
        }
    }

}
