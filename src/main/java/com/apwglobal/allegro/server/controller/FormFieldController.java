package com.apwglobal.allegro.server.controller;

import com.apwglobal.nice.domain.FormField;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class FormFieldController {

    @Autowired
    private IAllegroNiceApi allegro;

    @ControllerAdvice
    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
        public JsonpAdvice() {
            super("callback");
        }
    }

    @Cacheable(value = "form-fields")
    @RequestMapping("/form-fields")
    @ResponseBody
    public List<FormField> formFields(
            @RequestParam(required = true) int categoryId,
            @RequestParam(required = false, defaultValue = "true") boolean onlyRequired) {

        List<FormField> fields = allegro
                .getSellFormFields(categoryId);

        return filter(fields, onlyRequired);
    }

    private List<FormField> filter(List<FormField> fields, boolean onlyRequired) {
        if (onlyRequired) {
            fields = fields
                    .stream()
                    .filter(FormField::isRequired)
                    .collect(toList());
        }
        return fields;
    }
}
