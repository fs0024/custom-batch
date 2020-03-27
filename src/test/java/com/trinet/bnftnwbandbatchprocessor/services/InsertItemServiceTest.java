package com.trinet.bnftnwbandbatchprocessor.services;


import com.trinet.bnftnwbandbatchprocessor.entity.batch.FileItems;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskStep;
import com.trinet.bnftnwbandbatchprocessor.repository.batch.FileItemRepository;
import com.trinet.bnftnwbandbatchprocessor.repository.batch.TaskItemRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class InsertItemServiceTest {


    @Mock
    private TaskItemRepository taskItemRepository;
    @Mock
    private FileItemRepository fileItemRepository;

    @InjectMocks InsertItemService insertItemService ;

    @BeforeAll
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @AfterAll
    public void after() throws Exception {
    }



    @Test
    public void insertItems() {

        Mockito.when(taskItemRepository.saveAll(Mockito.anyList())).thenReturn(new ArrayList<>());

        Assert.assertEquals("SUCCESS" ,insertItemService.insertItems(new ArrayList<>()));


    }

    @Test
    public void removeOrphans() {

        List<TaskItem> list = new ArrayList<>();
         TaskItem item = new TaskItem();
         item.setId(1);

         Mockito.when ( taskItemRepository.findTaskItemByTaskStep(Mockito.any())).thenReturn(list);
        FileItems dbitem = new FileItems() ;

        Mockito.when ( fileItemRepository.findByTaskItem(Mockito.any())).thenReturn(dbitem);



        Assert.assertEquals("SUCCESS" ,insertItemService.removeOrphans(new TaskStep()));




    }
}