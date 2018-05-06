package org.pop.lf2u.controller;

import java.util.List;

import org.pop.lf2u.model.Report;
import org.pop.lf2u.service.FarmerService;
import org.pop.lf2u.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

	@Autowired
	private ReportService reportService;
	@Autowired
	private FarmerService farmerService;
	
	@RequestMapping(value = "/farmers/{fid}/reports", method = RequestMethod.GET)
	public ResponseEntity<List<Report>> getReports(@PathVariable("fid") String fid) {
		
		return new ResponseEntity<List<Report>>(reportService.getReportsByFarmer(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/farmers/{fid}/reports/{frid}", method = RequestMethod.GET)
	public ResponseEntity<Report> getReportDetailByFarmer(@PathVariable("fid") String fid,@PathVariable("frid") String frid, Report report) {
		
		if(farmerService.findById(fid) == null ) return new ResponseEntity<Report>(HttpStatus.NOT_FOUND);
		
		if(!reportService.isContainsFrid(frid)) return new ResponseEntity<Report>(HttpStatus.NOT_FOUND);
		
		report.setFrid(frid);
		report = reportService.getReportDetailByFarmer(report);
		
		return new ResponseEntity<Report>(report,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/managers/reports", method = RequestMethod.GET)
	public ResponseEntity<List<Report>> getReports() {
		
		return new ResponseEntity<List<Report>>(reportService.getReportsByManager(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/managers/reports/{mrid}", method = RequestMethod.GET)
	public ResponseEntity<Report> getReportDetailByManager(@PathVariable("mrid") String mrid,Report report) {
		
		
		if(!reportService.isContainsMrid(mrid)) return new ResponseEntity<Report>(HttpStatus.NOT_FOUND);
		
		report.setMrid(mrid);
		report = reportService.getReportDetailByManager(report);
		
		return new ResponseEntity<Report>(report,HttpStatus.OK);
	}
	
	
	
}
