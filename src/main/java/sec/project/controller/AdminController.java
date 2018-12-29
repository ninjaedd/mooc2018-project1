package sec.project.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import sec.project.domain.Signup;
import org.springframework.ui.Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

@Controller
public class AdminController {

    @RequestMapping(value = "/Admin", method = RequestMethod.GET)
    public String list() {
        return "admin";
    }
    
    @RequestMapping(value = "/adminmenu/adminmenu.html", method = RequestMethod.GET)
    public String adminMenu() {
        return "./adminmenu/adminmenu";
    }
    
    @RequestMapping(value = "/Admin", method = RequestMethod.POST)
    public String listFilter(Model model, @RequestParam("filter") String filter) throws Exception {
        if (filter.trim().length() > 0)
        {
            String databaseAddress = "jdbc:h2:file:./SignupDB";
            Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");
            
            String query = "SELECT * FROM signup WHERE name like '%" + filter + "%'"; 
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Signup> signups = new ArrayList<>();
            while (resultSet.next()) {
            Signup signup = new Signup();
            signup.setName(resultSet.getString("name"));
            signup.setAddress(resultSet.getString("address"));
            signups.add(signup);
            }
            
            model.addAttribute("signups", signups);
        }
        return "admin";
    }
    
}
