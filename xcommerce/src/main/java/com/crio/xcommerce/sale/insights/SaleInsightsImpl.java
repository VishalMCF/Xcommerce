package com.crio.xcommerce.sale.insights;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.crio.xcommerce.contract.exceptions.AnalyticsException;
import com.crio.xcommerce.contract.insights.SaleAggregate;
import com.crio.xcommerce.contract.insights.SaleAggregateByMonth;
import com.crio.xcommerce.contract.insights.SaleInsights;
import com.crio.xcommerce.contract.resolver.DataProvider;
import com.crio.xcommerce.model.Status;
import com.crio.xcommerce.model.IProvider;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class SaleInsightsImpl implements SaleInsights {

    private static int NO_OF_MONTHS = 12;

    @Override
    public SaleAggregate getSaleInsights(DataProvider dataProvider, int year) throws IOException, AnalyticsException {
        SaleAggregate saleAggregate = new SaleAggregate();
        List<IProvider> providersList;
        try{
            providersList = convertFileRowsToObjLists(dataProvider);
        }catch(Exception e){
            throw new AnalyticsException("Value cannot be found");
        }
        saleAggregate.setTotalSales(calculateTotalSales(providersList,year));
        saleAggregate.setAggregateByMonths(calculateSaleByMonth(providersList,year));
        return saleAggregate;
    }

    private List<SaleAggregateByMonth> calculateSaleByMonth(List<IProvider> providersList,int year) {

        List<SaleAggregateByMonth> saleAggregateByMonths = new ArrayList<>(); 
        for(int i=0;i<NO_OF_MONTHS;i++){
            saleAggregateByMonths.add(new SaleAggregateByMonth(i, 0.0));
        }
        for(IProvider data: providersList){
            if(data.getTransDate().getYear()==year){
                if(isStatusValid(data.getTransStatus())){
                    SaleAggregateByMonth saleOfMonth = saleAggregateByMonths.
                                            get(data.getTransDate()
                                            .getMonthValue()-1);
                    saleOfMonth.setSales(saleOfMonth.getSales()+data.getTransAmount());
                }
            }
        }
        return saleAggregateByMonths;
    }

    private Double calculateTotalSales(List<IProvider> providersList,int year) {
        double totalSales = 0.0;
        for(IProvider data: providersList){
            if(data.getTransDate().getYear()==year){
                if(isStatusValid(data.getTransStatus())){
                    totalSales += data.getTransAmount();
                }
            }
        }
        return totalSales;
    }

    private List<IProvider> convertFileRowsToObjLists(DataProvider dataProvider) throws IOException,
            AnalyticsException {
        File file = dataProvider.resolveFile();
        String vendorName = dataProvider.getProvider();
        List<Map<?, ?>> data = readObjectsFromCsv(file);
        List<IProvider> providersList = SalesFactory.getProviderAsList(vendorName, data);
        return providersList;
    }

    private static List<Map<?, ?>> readObjectsFromCsv(File file) throws IOException {
        CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader(Map.class).with(bootstrap).readValues(file);
        return mappingIterator.readAll();
    }

    public boolean isStatusValid(Status status){
        if(status.equals(Status.COMPLETE)||
        status.equals(Status.PAID)||
        status.equals(Status.SHIPPED)||
        status.equals(Status.DELIVERED)){
            return true;
        }
        return false;
    }
    
}