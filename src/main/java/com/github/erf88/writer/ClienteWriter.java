package com.github.erf88.writer;

import com.github.erf88.model.out.ClienteOut;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.core.io.WritableResource;

import java.io.BufferedOutputStream;
import java.util.List;

public class ClienteWriter implements ItemStreamWriter<ClienteOut> {

    private final WritableResource resource;
    private HSSFWorkbook workbook;
    private HSSFPalette palette;
    private HSSFSheet sheet;
    private HSSFCellStyle estiloCelula;
    private HSSFCellStyle estiloLinha;
    private int rows;

    public ClienteWriter(WritableResource resource) {
        this.resource = resource;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        iniciaWorkbook();
        escreveCabecalho();
    }
    private void iniciaWorkbook() {
        workbook = new HSSFWorkbook();
        palette = workbook.getCustomPalette();
        sheet = workbook.createSheet();
        rows = 0;
    }
    private void escreveCabecalho() {
        estiloCelula = workbook.createCellStyle();
        estiloLinha = workbook.createCellStyle();
        boolean negrito = true;
        short corFonte = IndexedColors.WHITE.getIndex();
        short corCelula = palette.findSimilarColor(112, 173, 71).getIndex();
        short tamanho = 11;
        String nome = "Calibri";
        HSSFFont fonte = criaFonte(negrito, corFonte, tamanho, nome);
        HSSFRow linha = sheet.createRow(rows);
        aplicaEstiloLinha(linha);
        escreveCelulaCabecalho(linha, 0, corCelula, fonte, "ID", 15);
        escreveCelulaCabecalho(linha, 1, corCelula, fonte, "NOME_COMPLETO", 35);
        escreveCelulaCabecalho(linha, 2, corCelula, fonte, "EMAIL", 40);
        escreveCelulaCabecalho(linha, 3, corCelula, fonte, "DATA_PROCESSAMENTO", 25);
        rows++;
    }

    private void escreveCelulaCabecalho( HSSFRow linha, int coluna, short corCelula, HSSFFont fonte, String valor, int larguraCelula) {
        HSSFCell celula = linha.createCell(coluna);
        aplicaEstiloCelula(celula, corCelula, fonte);
        celula.setCellValue(valor);
        sheet.setColumnWidth(coluna, Math.round(larguraCelula * 256 + 200));
    }

    private HSSFFont criaFonte(boolean negrito, short cor, short tamanho, String nome) {
        HSSFFont fonte = workbook.createFont();
        fonte.setBold(negrito);
        fonte.setColor(cor);
        fonte.setFontHeightInPoints(tamanho);
        fonte.setFontName(nome);
        return fonte;
    }

    private void aplicaEstiloLinha(HSSFRow linha) {
        estiloLinha.setAlignment(HorizontalAlignment.CENTER);
        estiloLinha.setWrapText(true);
        linha.setRowStyle(estiloLinha);
    }
    private void aplicaEstiloCelula(HSSFCell celula, short cor, HSSFFont fonte) {
        estiloCelula.setAlignment(HorizontalAlignment.CENTER);
        estiloCelula.setBorderTop(BorderStyle.THIN);
        estiloCelula.setBorderBottom(BorderStyle.THIN);
        estiloCelula.setBorderLeft(BorderStyle.THIN);
        estiloCelula.setBorderRight(BorderStyle.THIN);
        estiloCelula.setFillForegroundColor(cor);
        estiloCelula.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCelula.setFont(fonte);
        estiloCelula.setWrapText(true);
        celula.setCellStyle(estiloCelula);
    }

    @Override
    public void write(List<? extends ClienteOut> clientes) throws Exception {
        estiloCelula = workbook.createCellStyle();
        estiloLinha = workbook.createCellStyle();
        sheet = workbook.getSheetAt(0);
        boolean negrito = false;
        short corFonte = IndexedColors.BLACK.getIndex();
        short corCelula = IndexedColors.WHITE.getIndex();
        short tamanho = 11;
        String nome = "Calibri";
        HSSFFont fonte = criaFonte(negrito, corFonte, tamanho, nome);

        for (ClienteOut cliente : clientes) {
            HSSFRow linha = sheet.createRow(rows++);
            aplicaEstiloLinha(linha);
            escreveCelula(linha, 0, corCelula, fonte, cliente.getId());
            escreveCelula(linha, 1, corCelula, fonte, cliente.getNomeCompleto());
            escreveCelula(linha, 2, corCelula, fonte, cliente.getEmail());
            escreveCelula(linha, 3, corCelula, fonte, cliente.getDataProcessamento());
        }

    }

    private void escreveCelula(HSSFRow linha, int coluna, short corCelula, HSSFFont fonte, Object valor) {
        HSSFCell celula = linha.createCell(coluna);
        aplicaEstiloCelula(celula, corCelula, fonte);
        celula.setCellValue(valor.toString());
    }

    @Override
    public void close() throws ItemStreamException {
        if(this.workbook == null) {
            return;
        }

        try(BufferedOutputStream stream = new BufferedOutputStream(resource.getOutputStream())) {
            workbook.write(stream);
            stream.flush();
            workbook.close();
        } catch (Exception e) {
            throw new ItemStreamException("Erro ao gravar no arquivo de saida", e);
        }

        rows = 0;
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
    }

}
