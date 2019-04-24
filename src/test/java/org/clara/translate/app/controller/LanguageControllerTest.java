package org.clara.translate.app.controller;

import org.clara.translate.app.domain.Language;
import org.clara.translate.app.service.LanguageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LanguageController.class)
public class LanguageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LanguageService languageService;

    @Test
    public void getAllLanguages() throws Exception {

        Language lang1 = Language.builder().id(1L).code("ES").build();
        Language lang2 = Language.builder().id(2L).code("FR").build();
        List<Language> langs = Arrays.asList(lang1, lang2);

        given(this.languageService.getAllLanguages()).willReturn(langs);

        this.mvc.perform(get("/languages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        //Verifying the method is accessed
        verify(this.languageService, times(1)).getAllLanguages();
    }

    @Test
    public void getAllLanguagesWithNoLangs() throws Exception {

        List<Language> langs = Collections.EMPTY_LIST;

        given(this.languageService.getAllLanguages()).willReturn(langs);

        this.mvc.perform(get("/languages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        //Verifying the method is accessed
        verify(this.languageService, times(1)).getAllLanguages();
    }

    @Test
    public void getAllOtherLanguages() throws Exception {

        Language lang1 = Language.builder().id(1L).code("ES").build();
        Language lang2 = Language.builder().id(2L).code("FR").build();
        List<Language> languages = Arrays.asList(lang1, lang2);

        given(this.languageService.getAllOtherLanguages(anyLong())).willReturn(languages);

        this.mvc.perform(get("/languages/other/{id}", anyLong()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].code", is(lang1.getCode())));

        //Verifying the method is accessed
        verify(this.languageService, times(1)).getAllOtherLanguages(anyLong());

    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllOtherLanguagesWithNullId() throws Exception {

        Language lang1 = Language.builder().id(1L).code("ES").build();
        Language lang2 = Language.builder().id(2L).code("FR").build();
        List<Language> languages = Arrays.asList(lang1, lang2);

        given(this.languageService.getAllOtherLanguages(anyLong())).willReturn(languages);

        this.mvc.perform(get("/languages/other/{id}", null));
    }
}