package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.dao.PostBuyFormDao;
import com.apwglobal.nice.command.SearchPostBuyForm;
import com.apwglobal.nice.domain.PostBuyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.List;

@Controller
public class PostBuyFormController {

    @Autowired
    private PostBuyFormDao postBuyFormDao;

    @ControllerAdvice
    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
        public JsonpAdvice() {
            super("callback");
        }
    }

    @Cacheable("forms")
    @RequestMapping("/forms")
    @ResponseBody
    public List<PostBuyForm> postBuyForms(@RequestParam(value = "limit", required = false, defaultValue = "50") int limit) {
        return postBuyFormDao.getLastPostBuyForms(limit);
    }

    @RequestMapping(value = "/forms/search", method = RequestMethod.POST)
    @ResponseBody
    public List<PostBuyForm> search(@RequestBody SearchPostBuyForm s) {
        return postBuyFormDao.getLastPostBuyForms(s.getLimit().orElse(50));
    }

}
