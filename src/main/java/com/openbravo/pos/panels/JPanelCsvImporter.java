/*
 * Copyright (C) 2016 Beat Luginbühl <lugi@lugipfupf.ch>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openbravo.pos.panels;

import com.csvreader.CsvReader;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.imports.JPanelCustomerFields;
import com.unicenta.pozapps.forms.AppLocal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Beat Luginbühl <lugi@lugipfupf.ch>
 */
public abstract class JPanelCsvImporter extends JPanelTable2 {
    protected JPanelCSVFileChooser fileChooserPanel;
    protected JPanelPopulatable fieldConfigurator;
    
    @Override
    protected void init() {
    }
    
    @Override
    public void activate() {
        startNavigation();
    }
    
    @Override
    protected void startNavigation() {
        this.fileChooserPanel = new JPanelCSVFileChooser(this);
        this.fileChooserPanel.setComponentOrientation(getComponentOrientation());
        this.toolbar.add(this.fileChooserPanel);
    }
    
    @Override
    public boolean deactivate() {
        return true;
    }
    
    public void readCsvMetaData(String csvFileName, char delimiter, char quote) throws FileNotFoundException {
        CsvReader csvReader = new CsvReader(csvFileName);
        csvReader.setDelimiter(delimiter);
        csvReader.setTextQualifier(quote);
        
        try {
            csvReader.readHeaders();
            String[] headers = csvReader.getHeaders();
            
            int recordCount = 0;
            while (csvReader.skipRecord()) {
                recordCount++;
            }
            ArrayList<String> recordCountList = new ArrayList<>();
            ArrayList<String> headerList = new ArrayList<>();
            recordCountList.add(String.valueOf(recordCount));
            headerList.addAll(Arrays.asList(headers));
            
            this.fileChooserPanel.populate(recordCountList);
            this.fieldConfigurator.populate(headerList);
        } catch (IOException ex) {
            Logger.getLogger(JPanelCsvImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
