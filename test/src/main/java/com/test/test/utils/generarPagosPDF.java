package com.test.test.utils;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.test.test.models.Vehiculo_Residente;

import jakarta.servlet.http.HttpServletResponse;

public class generarPagosPDF {

	private List<Vehiculo_Residente> listaPagos;

	public generarPagosPDF(List<Vehiculo_Residente> listaPagos) {
		super();
		this.listaPagos = listaPagos;
	}

	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();

		celda.setBackgroundColor(Color.WHITE);
		celda.setPadding(5);

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.BLACK);

		celda.setPhrase(new Phrase("Núm. placa", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Tiempo estacionado (min.)", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Cantidad a pagar", fuente));
		tabla.addCell(celda);
	}

	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for (Vehiculo_Residente pago : listaPagos) {
			tabla.addCell(String.valueOf(pago.getPlaca()));
			tabla.addCell(String.valueOf(pago.getTiempo()));
			float total_pago = (float)(pago.getTiempo() * 0.5);
			tabla.addCell(String.valueOf(total_pago));
		}
	}

	public void exportar(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());

		documento.open();

		PdfPTable tabla = new PdfPTable(3);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] { 2f, 3.5f, 2f });
		tabla.setWidthPercentage(110);

		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);

		documento.add(tabla);
		documento.close();
	}
}
