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
@Table(name = "game_category")
public class GameCategory {

    @Column(name = "id", PK = true)
    String id;

    @Column(name = "game_id")
    String gameId;

    @Column(name = "category_id")
    String categoryId;

    @Column(name = "status")
    String status;

}
