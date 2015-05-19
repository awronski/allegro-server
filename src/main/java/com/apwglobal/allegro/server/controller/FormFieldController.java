package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IFormFieldsService;
import com.apwglobal.nice.domain.FormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FormFieldController implements JsonpControllerAdvice, ClientIdAwareController {

    @Autowired
    private IFormFieldsService formFieldsService;

    @RequestMapping("/form-fields")
    @ResponseBody
    public List<FormField> formFields(
            @RequestParam(required = true) int categoryId,
            @RequestParam(required = false, defaultValue = "true") boolean onlyRequired) {

        return formFieldsService.getFormFields(getClientId(), categoryId, onlyRequired);
    }

}
