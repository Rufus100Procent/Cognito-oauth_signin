package se.distansakademin.oauth_0.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.distansakademin.oauth_0.Cognito.Cognito;

@Controller
public class UserController {

    @GetMapping("/auth")
    public String GetHome(Model model){

        if (Cognito.loggedInUser==null){
            return "redirect:/register";
        }else {
            model.addAttribute("username",Cognito.loggedInUser.username);
            return "redirect:/home";
        }
    }
    @GetMapping("/home")
    public String home(){
        return "homepage";
    }

    @GetMapping("/register")
    public String GetRegister(){

        return "auth/registration";
    }

    @PostMapping("/register")
    public String PostRegister(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes){

        System.out.println(username);
        System.out.println(email);
        System.out.println(password);

        if (Cognito.Register( username,password,email)){

            redirectAttributes.addAttribute("email",email);
            redirectAttributes.addAttribute("username",username);
            return "redirect:/verify";

        }else {
            return "auth/registration";
        }
    }

    @GetMapping("/verify")
    public String GetVerify(@RequestParam("email") String email,
                            @RequestParam("username") String username,
                            Model model
                            ){

        model.addAttribute("email",email);
        model.addAttribute("username",username);
        return "auth/verify";
    }

    @PostMapping("/verify")
    public String PostVerify(@RequestParam("username") String username,
                             @RequestParam("confirmationCode") String confirmationCode
                             ){

        if (Cognito.ConfirmUser(username,confirmationCode)){

            return "redirect:/login";

        }else{
            return "redirect:/home";
        }
    }


    @GetMapping("/login")
    public String GetLogin(){

        return "auth/login";
    }

    @PostMapping("/login")
    public String PostLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes){

        System.out.println(username);
        System.out.println(password);

        if (Cognito.Login(username,password)){

            return "redirect:/home";

        }else {
            return "auth/login";
        }
    }

    @PostMapping("/logout")
    public String PostLogout(){
        Cognito.Logout();
        return "redirect:/login";
    }

    @PostMapping("/changePassword")
    public String postChangePassword(@RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword) {
        System.out.println("Changed password from " + oldPassword + " to " + newPassword);
        Cognito.ChangePassword(oldPassword, newPassword);

            return "auth/login";
    }

    @DeleteMapping("/users/{username}")
    public String deleteUser(@PathVariable String username) {
        boolean deletionResult = Cognito.DeleteUser(username);
            return "redirect:/register";

    }

}
