package controller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import entities.Order;
import entities.OrderItem;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class PDFCreator {
	
	private static String FILE = "test.pdf";
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	List<OrderItem> oItems;
	Document document;
	BigDecimal total;
	HttpServletResponse response;
	
	public PDFCreator(Order o, HttpServletResponse r){
		this.total = o.getTotal();
		this.oItems = o.getItems();
		response = r;
		r.setContentType("application/pdf");
	}
	
	private void addMetaData() {
		document.addTitle("Facture - BookStore");
	    document.addSubject("Facture");
	    document.addCreator("BookStore");
	}
	
	public void createPDF() {
		try {
	      document = new Document();
	      PdfWriter.getInstance(document, response.getOutputStream());
	      document.open();
	      addMetaData();
	      addContentPage();
	      document.close();
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	private void addContentPage() throws DocumentException {
	    Paragraph page = new Paragraph();
		addEmptyLine(page, 1);
		page.add(new Paragraph("Votre Facture - BookStore", catFont));
		
		addEmptyLine(page, 2);
		
		page.add(createTable());
		
		addEmptyLine(page, 1);
		page.add(new Paragraph("Facture émise le : " + new Date(), smallBold));
		
		document.add(page);
  	}

	private PdfPTable createTable() throws BadElementException {
	        PdfPTable table = new PdfPTable(4);
	        
	        // Titre de chaque colonne
	        table.addCell("Référence");
	        table.addCell("Quantitée");
	        table.addCell("Prix");
	        table.addCell("Total");
	        table.completeRow();
	        
	        // Boucle sur chaque Ã©lÃ©ment de la facture
	        Iterator<OrderItem> it = oItems.iterator();
	        while(it.hasNext()){
	        	OrderItem oItem = it.next();
	        	table.addCell(oItem.getBook().getTitle());
		        table.addCell(Integer.toString(oItem.getQuantity()));
		        table.addCell(oItem.getBook().getPrice().toString());
		        table.addCell(oItem.getTotal().toString());
	        }
	        
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(total.toString());
	        return table;
	  }

	  private void addEmptyLine(Paragraph p, int number) {
	    for (int i = 0; i < number; i++) {
	    	p.add(new Paragraph(" "));
	    }
	  }
}