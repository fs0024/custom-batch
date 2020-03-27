package com.trinet.bnftnwbandbatchprocessor.controller;




import com.trinet.bnftnwbandbatchprocessor.services.BatchApiService;
import com.trinet.bnftnwbandbatchprocessor.util.BatchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/platform")
@RestController
public class RateBandProcessor {

    @Autowired
    private BatchManager batchManager;

    @Autowired
    private BatchApiService batchApiService ;
    

    @GetMapping("/monitor")
    public  String monitor(){
    	
        return "success" ;
    }

    @GetMapping("/updateSchedule")
    public  String updateSchedule() throws Exception{

		return  batchManager.updateSchedule("ratebandJob");

    }


	@GetMapping("/stopjobs")
	public  String stopjobs() throws Exception{

		 return batchManager.stopJobs();

	}


	@GetMapping("/startProcess")
	public String startProcess() throws Exception {

		return batchManager.scheduleRun("ratebandJob");

	}

	@GetMapping("/stopSchedule")
	public String killProcess() throws Exception {

		return	batchManager.stopSchedule("ratebandJob");

	}

	@GetMapping("/runNow")
	public String runNow() throws Exception {

		return batchManager.runNow();

	}



}
