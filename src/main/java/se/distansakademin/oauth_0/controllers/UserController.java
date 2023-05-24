package se.distansakademin.oauth_0.controllers;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.distansakademin.oauth_0.Cognito.Cognito;
import se.distansakademin.oauth_0.models.User;

@Controller
public class UserController {
    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/register";
    }

    @GetMapping("/features")
    public String showFeatures(@AuthenticationPrincipal User user) {
        return "home/features";
    }

    @GetMapping("/future")
    public String showFuture() {
        return "home/future";
    }

    @GetMapping("/history")
    public String showHistory() {
        return "home/history";
    }

    @GetMapping("/auth")
    public String GetHome(Model model){

        if (Cognito.loggedInUser==null){
            return "redirect:/register";
        }else {
            model.addAttribute("username", Cognito.loggedInUser.getUsername());
            return "home";
        }
    }

    @GetMapping("/register")
    public String GetRegister(){

        return "registration";
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
            return "registration";
        }
    }

    @GetMapping("/verify")
    public String GetVerify(@RequestParam("email") String email,
                            @RequestParam("username") String username,
                            Model model
    ){

        model.addAttribute("email",email);
        model.addAttribute("username",username);
        return "verify";
    }

    @PostMapping("/verify")
    public String PostVerify(@RequestParam("username") String username,
                             @RequestParam("confirmationCode") String confirmationCode
    ){

        if (Cognito.ConfirmUser(username,confirmationCode)){

            return "redirect:/login";

        }else{
            return "verify";
        }
    }


    @GetMapping("/login")
    public String GetLogin(){

        return "login";
    }

    @PostMapping("/login")
    public String PostLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            RedirectAttributes redirectAttributes){

        System.out.println(username);
        System.out.println(password);

        if (Cognito.Login(username,password)){

            return "redirect:/";

        }else {
            return "login";
        }
    }

    @PostMapping("/logout")
    public String PostLogout(){
        Cognito.Logout();
        return "redirect:/login";
    }

    @PostMapping("changePassword")
    public String PostChangePassword(@RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword
    ){

        System.out.println("Changed password from "+oldPassword +" to "+newPassword);

        if (Cognito.ChangePassword(oldPassword,newPassword)){
            return "redirect:/";
        }else {
            return "home";

        }
    }

    @DeleteMapping("/users/{username}")
    public String deleteUser(@PathVariable String username) {
        boolean deletionResult = Cognito.DeleteUser(username);
        if (deletionResult) {
            return "User deleted successfully.";
        } else {
            return "Failed to delete the user.";
        }
    }


}
