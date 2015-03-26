package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.controller.util.TestOptional;
import com.apwglobal.allegro.server.dao.PostBuyFormDao;
import com.apwglobal.nice.command.SearchPostBuyForm;
import com.apwglobal.nice.domain.PostBuyForm;
import junit.framework.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.List;
import java.util.Optional;

import static com.apwglobal.allegro.server.controller.util.TestOptional.*;
import static com.apwglobal.allegro.server.controller.util.TestOptional.eq;
import static com.apwglobal.allegro.server.controller.util.TestOptional.ge;
import static com.apwglobal.allegro.server.controller.util.TestOptional.le;
import static java.util.stream.Collectors.toList;

@Controller
public class PostBuyFormController {

    public static final int SEARCH_LIMIT = 100;

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
        return postBuyFormDao.getPostBuyFormsBetween(s.getFrom(), s.getTo())
                .stream()
                .filter(f -> eq(s.getBuyerId(), f.getBuyerId()))
                .filter(f -> eq(s.getEmail(), f.getEmail()))
                .filter(f -> eq(s.getTransactionId(), f.getTransactionId()))
                .filter(f -> eq(s.getWithInvoice(), f.getWithInvoice()))
                .filter(f -> exist(s.getWithMsg(), f.getMsg()))
                .limit(s.getLimit().orElse(SEARCH_LIMIT))
                .collect(toList());
    }


}
