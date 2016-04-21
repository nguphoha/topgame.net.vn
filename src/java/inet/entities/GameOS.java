/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;

/**
 *
 * @author Admin
 */
@Table(name = "game_os")
public class GameOS {

    @Column(name = "id", PK = true)
    String id;

    @Column(name = "os_id")
    String osId;

    @Column(name = "game_id")
    String gameId;

    @Column(name = "url")
    String url;

    @Column(name = "status")
    String status;

    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
