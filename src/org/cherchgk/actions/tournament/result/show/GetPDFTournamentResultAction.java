/*
 * Copyright 2012-2014 Andrey Grigorov, Anton Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cherchgk.actions.tournament.result.show;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.cherchgk.actions.tournament.result.TournamentResult;
import org.cherchgk.actions.tournament.result.ranking.RankingAlgorithm;
import org.cherchgk.actions.tournament.result.ranking.RankingPoint;
import org.cherchgk.domain.Team;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;
import org.cherchgk.utils.HttpUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Действие выгрузки результатов турнира в формате PDF.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class GetPDFTournamentResultAction extends BaseShowTournamentResultAction {

    private static final float defaultFontSize = 10f;

    public GetPDFTournamentResultAction(TournamentService tournamentService) {
        super(tournamentService);
    }

    public InputStream getDocument() throws DocumentException, IOException {
        Font normalFont = getNormalFont();
        Font boldFont = getBoldFont();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Paragraph tournamentNameParagraph = new Paragraph(tournament.getTitle(), boldFont);
        tournamentNameParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(tournamentNameParagraph);

        Paragraph tournamentDateParagraph = new Paragraph(tournament.getDateAsString(), boldFont);
        tournamentDateParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(tournamentDateParagraph);

        if (teamCategory != null) {
            Paragraph teamCategoryParagraph = new Paragraph(teamCategory.getTitle(), boldFont);
            teamCategoryParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(teamCategoryParagraph);
        }

        int numColumns = 3 + tournamentResult.getRankingAlgorithms().size();
        PdfPTable resultTable = new PdfPTable(numColumns);
        int[] widths = new int[numColumns];
        widths[0] = 1;
        widths[1] = 3;
        for (int i = 2; i < numColumns; i++) {
            widths[i] = 1;
        }
        resultTable.setWidths(widths);
        resultTable.setSpacingBefore(10f);

        PdfPCell cell = new PdfPCell(new Phrase("Команда", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(2);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        resultTable.addCell(cell);

        for (RankingAlgorithm rankingAlgorithm : tournamentResult.getRankingAlgorithms()) {
            cell = new PdfPCell(new Phrase(rankingAlgorithm.getPointName(), boldFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            resultTable.addCell(cell);
        }

        cell = new PdfPCell(new Phrase("Место", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        resultTable.addCell(cell);

        boolean showTeamCategoryInTable = (teamCategory == null) && !tournament.getTeamCategories().isEmpty();

        for (TournamentResult.TeamResult teamResult : tournamentResult.getTeamResultList()) {
            if (showTeamCategoryInTable) {
                if (teamResult.getTeam().getTeamCategory() != null) {
                    cell = new PdfPCell(new Phrase(teamResult.getTeam().getTeamCategory().getTitle(), normalFont));
                } else {
                    cell = new PdfPCell(new Phrase("", normalFont));
                }
                resultTable.addCell(cell);
            }
            cell = new PdfPCell(new Phrase(teamResult.getTeam().getName(), normalFont));
            if (!showTeamCategoryInTable) {
                cell.setColspan(2);
            }
            resultTable.addCell(cell);
            for (Map<Team, RankingPoint> m : tournamentResult.getRankingPointsList()) {
                cell = new PdfPCell(new Phrase(m.get(teamResult.getTeam()).toString(), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                resultTable.addCell(cell);
            }
            cell = new PdfPCell(new Phrase(teamResult.getPlace(), normalFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            resultTable.addCell(cell);
        }

        document.add(resultTable);
        document.close();

        return new ByteInputStream(out.toByteArray(), out.size());
    }

    public String getContentDisposition() {
        String fileName = tournament.getTitle() + " - " + tournament.getDateAsString();
        if (teamCategory != null) {
            fileName += " - " + teamCategory.getTitle();
        }
        return HttpUtils.getContentDisposition(fileName + ".pdf", ActionContextHelper.getRequest());
    }

    private BaseFont getBaseFont(String fontFileName) {
        String realPath = ActionContextHelper.getRequest().getSession().getServletContext().getRealPath("");
        try {
            return BaseFont.createFont(realPath + File.separator + "WEB-INF" + File.separator + "fonts"
                    + File.separator + fontFileName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private Font getNormalFont() {
        BaseFont baseFont = getBaseFont("DejaVuSerif.ttf");
        if (baseFont != null) {
            return new Font(baseFont, defaultFontSize);
        }
        return new Font(Font.FontFamily.TIMES_ROMAN, defaultFontSize, Font.NORMAL);
    }

    private Font getBoldFont() {
        BaseFont baseFont = getBaseFont("DejaVuSerif-Bold.ttf");
        if (baseFont != null) {
            return new Font(baseFont, defaultFontSize);
        }
        return new Font(Font.FontFamily.TIMES_ROMAN, defaultFontSize, Font.BOLD);
    }
}
