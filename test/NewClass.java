
import inet.common.database.pool.DBPool;
import inet.dao.CategoryDAO;
import inet.dao.GameDAO;
import inet.entities.Category;
import inet.entities.Game;
import inet.util.Encrypter;
import java.sql.Connection;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class NewClass {
    public static void main(String[] args) throws Exception {
//        System.out.println(Encrypter.encrypt("topgame!@#456#@!"));
        DBPool.loadConfiguration(NewClass.class.getResourceAsStream("/inet/dbpool.properties"));
//        Connection con = DBPool.getInstance("topgame").getConnection();
//           if(con == null)  {
//               System.out.println("fail");
//           } else {
//               System.out.println("success");
//           }
        GameDAO dao = GameDAO.getInstance();
//        List<Game> categories = dao.findByName("","ios");
//        for(Game game : categories){  
//            System.out.println("=="+game.getId() +"|name "+game.getName()+"|category " + game.getCategoryName() );
//        }
        Game game = dao.findById(8);
        System.out.println("==desc "+game.getDescription());
//        System.out.println("===============Next");
//        categories = dao.findGameHot(0,2,6);
//        for(Game game : categories){  
//            System.out.println("=="+game.getId() +"|name "+game.getName()+"|category" + game.getCategoryName());
//        }
        //System.out.println("=====count "+dao.countGameByCategory(1, "android"));
    }
}
