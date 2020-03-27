package com.trinet.bnftnwbandbatchprocessor.batch;


import com.trinet.bnftnwbandbatchprocessor.conf.BatchConfiguration;
import com.trinet.bnftnwbandbatchprocessor.conf.BatchJobDefinition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = {BatchJobDefinition.class, BatchConfiguration.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BatchJobTest {






//   @Ignore
//    @Test
//    public void givenTaskletsJob_whenJobEnds_thenStatusCompleted()
//            throws Exception {
//
//
//
//            JobExecution jobExecution = jobLauncherTestUtils.launchJob();
//
//
//         assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
//
//
//}
//
//
//    @Ignore
//    @Test
//    public void givenTaskletsJob_whenJobEnds_thenStatusCompleted2()
//            throws Exception {
//
//
//
//        JobExecution jobExecution = initJobLauncherTestUtils.launchJob();
//
//    assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
//}

}
