package com.vtalent.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vtalent.binding.CoResponse;
import com.vtalent.entity.CitizenAppEntity;
import com.vtalent.entity.CoTriggerEntity;
import com.vtalent.entity.DcCaseEntity;
import com.vtalent.entity.EligibilityDetailsEntity;
import com.vtalent.repository.CitizenAppRepository;
import com.vtalent.repository.CoTriggerRepository;
import com.vtalent.repository.DcCaseRepository;
import com.vtalent.repository.EligibilityDateilsRepository;
import com.vtalent.service.Coservice;
import com.vtalent.utils.EmailUtilsClass;

@Service
public class CoServiceImpl implements Coservice {

	@Autowired
	private CoTriggerRepository coTriggerRepository;

	@Autowired
	private EligibilityDateilsRepository eligibilityDateilsRepository;

	@Autowired
	private CitizenAppRepository citizenAppRepository;

	@Autowired
	private DcCaseRepository dcCaseRepository;

	@Autowired
	private EmailUtilsClass emailUtilsClass;

	@Override
	public CoResponse processPendingTriggers() {

		CoResponse response = new CoResponse();

		CitizenAppEntity appEntity = null;
		// fetch all pending triggers

		List<CoTriggerEntity> pendingTrgs = coTriggerRepository.findByTrgStatus("pending");

		response.setTotalTriggers(Long.valueOf(pendingTrgs.size()));

		// process each pending triggers
		for (CoTriggerEntity entity : pendingTrgs) {
			// get eligibility data based on caseNumber

			EligibilityDetailsEntity eligibilityRecord = eligibilityDateilsRepository
					.findByCaseNum(entity.getCaseNum());

			// get citizen data based on caseNumber
			Optional<DcCaseEntity> findById = dcCaseRepository.findById(entity.getCaseNum());
			if (findById.isPresent()) {
				DcCaseEntity dcCaseEntity = findById.get();
				Integer appId = dcCaseEntity.getAppId();
				Optional<CitizenAppEntity> appEntityOptional = citizenAppRepository.findById(appId);
				if (appEntityOptional.isPresent()) {
					appEntity = appEntityOptional.get();
				}
			}

			Long failed = 0l;
			Long succes = 0l;

			// generate pdf with eligibility details
			// send pdf to citizen mail
			try {
				generatePdfAndSendPdf(eligibilityRecord, appEntity);
				succes++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				failed++;
			}
			response.setSuccTriggers(succes);
			response.setFailedTriggers(failed);

			// store the pdf and update trigger as complete

		}

		// return summary

		return response;
	}

	private void generatePdfAndSendPdf(EligibilityDetailsEntity eligibilityDetailsEntity,
			CitizenAppEntity citizenAppEntity) throws Exception {
		Document document = new Document(PageSize.A4);

		File file = new File(eligibilityDetailsEntity.getCaseNum() + ".pdf");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PdfWriter.getInstance(document, fos);
		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("Eligibility Report", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 1.5f, 3.0f, 1.5f, 3.0f });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Citizen Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Status", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Start Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan End Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Benefit Amount", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Denial Reason", font));
		table.addCell(cell);

		table.addCell(citizenAppEntity.getFullName());
		table.addCell(eligibilityDetailsEntity.getPlanName());
		table.addCell(eligibilityDetailsEntity.getPlanStatus());
		table.addCell(eligibilityDetailsEntity.getPlanStartDate() + "");
		table.addCell(eligibilityDetailsEntity.getPlanEndDate() + "");
		table.addCell(eligibilityDetailsEntity.getBenefitAmt() + "");
		table.addCell(eligibilityDetailsEntity.getDenialReason() + "");

		document.add(table);

		document.close();

		String subject = "His Eligiblity Info";
		String body = "His Eligiblity Info";

		emailUtilsClass.sendEmail(citizenAppEntity.getEmail(), subject, body, file);

		updateTrigger(eligibilityDetailsEntity.getCaseNum(), file);

	}

	private void updateTrigger(Long caseNum, File file) throws Exception {

		CoTriggerEntity coEntity = coTriggerRepository.findByCaseNum(caseNum);

		byte[] arr = new byte[(byte) file.length()];

		FileInputStream fis = new FileInputStream(file);
		fis.read(arr);

		coEntity.setCoPdf(arr);

		coEntity.setTrgStatus("Completed");

		coTriggerRepository.save(coEntity);

		fis.close();
	}

}
