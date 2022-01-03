package com.crio.xcommerce.sale.insights;

import java.util.*;
import java.util.stream.Collectors;

import com.crio.xcommerce.contract.exceptions.AnalyticsException;
import com.crio.xcommerce.model.AmazonProvider;
import com.crio.xcommerce.model.EbayProvider;
import com.crio.xcommerce.model.FlipkartProvider;
import com.crio.xcommerce.model.IProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SalesFactory {

    private static List<IProvider> providers;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<IProvider> getProviderAsList(String providerName,List<Map<?,?>> data)
            throws AnalyticsException {
        switch(providerName){
            case "amazon":
                return getAmazonData(data);
            case "ebay":
                return getEbayData(data);
            case "flipkart":
                return getFlipKartData(data);
            default:
                return Collections.emptyList();
        }
    }
    

	private static List<IProvider> getFlipKartData(List<Map<?, ?>> data) throws AnalyticsException {
        providers = data.stream()
                        .map(d -> mapper.convertValue(d, FlipkartProvider.class))
                        .collect(Collectors.toList());
        return providers;
	}

    private static List<IProvider> getAmazonData(List<Map<?, ?>> data) throws AnalyticsException {
       providers = data.stream()
                        .map(d -> mapper.convertValue(d, AmazonProvider.class))
                        .collect(Collectors.toList());
        return providers;
    }

    private static List<IProvider> getEbayData(List<Map<?, ?>> data) throws AnalyticsException {
        providers = data.stream()
                        .map(d -> mapper.convertValue(d, EbayProvider.class))
                        .collect(Collectors.toList());
        return providers;
    }
}