/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;
import inet.dao.ModuleDao;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import inet.util.Constants;

/**
 *
 * @author Admin
 */
@Table(name = "module")
public class Module {

    @Column(name = "id", PK = true)
    private String id;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "status")
    private int status = 1;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "priority")
    private String priority;

    private Module parent;
    private List<Module> children = new ArrayList<Module>();
    
    public void insert() throws Exception {
        ModuleDao moduleDao = new ModuleDao();
        String id = moduleDao.getSequenceValue("modules", Constants.DATABASE);
        this.id = id;
        moduleDao.insert(this);
    }

    public void update() throws Exception {
        ModuleDao moduleDao = new ModuleDao();
        moduleDao.update(this);
    }
    
    public void addChild(Module child) {
        children.add(child);
    }
    
    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Module getParent() {
        return parent;
    }

    public void setParent(Module parent) {
        this.parent = parent;
    }

    public List<Module> getChildren() {
        return children;
    }

    public void setChildren(List<Module> children) {
        this.children = children;
    }
    
}
