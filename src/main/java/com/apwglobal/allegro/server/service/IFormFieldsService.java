package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.FormField;

import java.util.List;

public interface IFormFieldsService {

    List<FormField> getFormFields(long sellerId, int catId, boolean onlyRequired);

}
