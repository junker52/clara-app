package org.clara.translate.app.controller;

import org.clara.translate.app.domain.Budget;
import org.clara.translate.app.service.BudgetService;
import org.clara.translate.app.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FileController.class)
public class FileControllerTest {

    @MockBean
    private BudgetService budgetService;

    @MockBean
    private FileService fileService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getFileNormalFileTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        Budget budget = Budget.builder().words(200).build();

        given(this.fileService.isFileValid(any())).willReturn(Boolean.TRUE);
        given(this.budgetService.createBudget(any(), anyLong(), anyLong())).willReturn(budget);

        this.mvc.perform(multipart("/files/{lang1}/{lang2}", 1L, 2L).file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.words", is(Integer.valueOf(budget.getWords()))));

        verify(this.fileService, times(1)).isFileValid(any());
        verify(this.budgetService, times(1)).createBudget(any(), anyLong(), anyLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFileNoFileTest() throws Exception {
        MockMultipartFile file = null;

        this.mvc.perform(multipart("/files/{lang1}/{lang2}", 1L, 2L).file(file));
    }
}