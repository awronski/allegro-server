package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.FormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FormFieldsService implements IFormFieldsService {

    @Autowired
    private IAllegroClientFactory allegro;

    @Cacheable(value = "form-fields")
    @Override
    public List<FormField> getFormFields(long sellerId, int catId, boolean onlyRequired) {
        List<FormField> fields = allegro.get(sellerId)
                .getSellFormFields(catId);

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
