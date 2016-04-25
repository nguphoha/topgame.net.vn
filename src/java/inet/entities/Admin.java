/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;
import inet.dao.AdminDao;
import inet.util.Constants;
import inet.util.StringUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
@Table(name = "admin")
public class Admin {

    @Column(name = "id", PK = true)
    String id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "name")
    String name;

    @Column(name = "mobile")
    String mobile;

    @Column(name = "email")
    String email;

    @Column(name = "status")
    String status;

    @Column(name = "date_create")
    Timestamp dateCreate;

    private List<Module> modules = new ArrayList<Module>();
    private Map<String, Module> mModules = new HashMap<String, Module>();
    private List<Module> modulesTree;
    
    public void insert() throws Exception {
        this.username = username.trim();
        this.password = password.trim();
        AdminDao adminDao = new AdminDao();
        String id = adminDao.getSequenceValue("admin", Constants.DATABASE);
        this.id = id;
        adminDao.insert(this);
    }

    public void update() throws Exception {
        AdminDao adminDao = new AdminDao();
        adminDao.update(this);
    }

    public List<Module> getModuleTree() {
        List<Module> parents = getRoots(modules);
        for (Module item : parents) {
            getChild(item, modules);
        }
        return parents;
    }

    private List<Module> getRoots(List<Module> modules) {
        List<Module> parents = new ArrayList<Module>();
        for (Module item : modules) {
            if (StringUtil.nvl(item.getParentId(), "").equals("")) {
                parents.add(item);
            }
        }
        return parents;
    }

    private void getChild(Module parent, List<Module> datas) {
        for (Module item : datas) {
            if (!StringUtil.nvl(item.getParentId(), "").equals("") && item.getParentId().equals(parent.getId())) {
                item.setParent(parent);
                getChild(item, datas);
                parent.addChild(item);
            }
        }
    }

    public List<Module> getModulesTree() {
        if (modulesTree == null) {
            modulesTree = getModuleTree();
        }
        return modulesTree;
    }

    public void setModulesTree(List<Module> modulesTree) {
        this.modulesTree = modulesTree;
    }
    
    @Override
    public Admin clone() throws CloneNotSupportedException {
        return (Admin) super.clone();
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    public Module getModule(String url) {
        return mModules.get(url);
    }
    public Map<String, Module> getmModules() {
        return mModules;
    }

    public void setmModules(Map<String, Module> mModules) {
        this.mModules = mModules;
    }

    
}
