package mylab.mail.util.emailblacklister.controller;

import mylab.mail.util.emailblacklister.model.EmailBlacklistRequest;
import mylab.mail.util.emailblacklister.model.EmailBlacklistResponse;
import mylab.mail.util.emailblacklister.services.EmailBlacklistManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kernel0237 on 10/22/14.
 */

@Controller
public class EmailBlacklistController {

    @Autowired
    private EmailBlacklistManager emailBlacklistManager;

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public
    @ResponseBody
    EmailBlacklistResponse create(@RequestBody EmailBlacklistRequest request) {
        emailBlacklistManager.create(request.getEmailBlacklistForm());

        EmailBlacklistResponse response = new EmailBlacklistResponse();

        return response;
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public
    @ResponseBody
    EmailBlacklistResponse delete(@RequestParam(value = "ids") String ids) {
        emailBlacklistManager.delete(ids);

        EmailBlacklistResponse response = new EmailBlacklistResponse();

        return response;
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public
    @ResponseBody
    EmailBlacklistResponse get(@RequestParam(value = "emailDomain") String emailDomain) {
        EmailBlacklistResponse response = new EmailBlacklistResponse();
        response.setEmailBlacklistForm(emailBlacklistManager.get(emailDomain));

        return response;
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public
    @ResponseBody
    EmailBlacklistResponse list() {
        EmailBlacklistResponse response = new EmailBlacklistResponse();
        response.setEmailBlacklistForms(emailBlacklistManager.list());

        return response;
    }

}
