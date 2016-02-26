/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherstation;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.jdom2.input.SAXBuilder;
import org.jdom2.*;

/**
 *
 * @author 1960681
 */
public class XMLParser {
    
    public void parse(File directory, ArrayList<WeatherYear> yearsList)
    {      
        SAXBuilder builder = new SAXBuilder();
        try
        {
            int fileIndex = 0;
            while(fileIndex < directory.listFiles().length)
            {
                File file = directory.listFiles()[fileIndex];
                fileIndex++;
                Document document = builder.build(file);
                Element root = document.getRootElement();
                String fileName = file.getName();
                String temp = fileName.split("-")[0];
                int yearNum = Integer.parseInt(temp);

                WeatherYear tempYear = new WeatherYear(yearNum);

                for(int monthIndex = 0; monthIndex < 12; monthIndex++)
                {
                    WeatherMonth tempMonth = new WeatherMonth(monthIndex);

                    Calendar cal = new GregorianCalendar(tempYear.year, tempMonth.month, 1);

                    int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                    for(int dayIndex = 0; dayIndex < daysInMonth; dayIndex++)
                    {
                        WeatherDay tempDay = new WeatherDay(dayIndex);

                        List<Element> weatherList = root.getChildren();
                        for ( int j = 0; j < 96; j++ )
                        {
                            Element weather = weatherList.get(j);
                            WeatherData data = new WeatherData(weather);
                            data.year = yearNum;
                            data.month = monthIndex;
                            data.day = dayIndex;
                            tempDay.data.add(data);
                        }
                        for (int k = 0; k < tempDay.data.size(); k++) {
                            tempDay.meanTemp = tempDay.meanTemp + Integer.parseInt(tempDay.data.get(k).temperature);
                            tempDay.meanWindSpeed = tempDay.meanWindSpeed + Integer.parseInt(tempDay.data.get(k).windSpeed);
                            tempDay.totalRainfall = tempDay.totalRainfall + Integer.parseInt(tempDay.data.get(k).rainfall);
                            tempMonth.meanTemp = tempMonth.meanTemp + Integer.parseInt(tempDay.data.get(k).temperature);
                            tempMonth.meanWindSpeed = tempMonth.meanWindSpeed + Integer.parseInt(tempDay.data.get(k).windSpeed);
                            tempMonth.totalRainfall = tempMonth.totalRainfall + Integer.parseInt(tempDay.data.get(k).rainfall);
                            tempYear.meanTemp = tempYear.meanTemp + Integer.parseInt(tempDay.data.get(k).temperature);
                            tempYear.meanWindSpeed = tempYear.meanWindSpeed + Integer.parseInt(tempDay.data.get(k).windSpeed);
                            tempYear.totalRainfall = tempYear.totalRainfall + Integer.parseInt(tempDay.data.get(k).rainfall);
                            
                            
                            if(tempDay.highTemp < Integer.parseInt(tempDay.data.get(k).temperature)){
                                tempDay.highTemp = Integer.parseInt(tempDay.data.get(k).temperature);
                                tempDay.highDate = tempDay.data.get(k).date;
                                tempDay.highTime = tempDay.data.get(k).time;
                               
                            }
                            if(tempMonth.highTemp < Integer.parseInt(tempDay.data.get(k).temperature)){
                                tempMonth.highTemp = Integer.parseInt(tempDay.data.get(k).temperature);
                                tempMonth.highDate = tempDay.data.get(k).date;
                                tempMonth.highTime = tempDay.data.get(k).time;
                                
                            
                               
                            }
                            if(tempYear.highTemp < Integer.parseInt(tempDay.data.get(k).temperature)){
                                tempYear.highTemp = Integer.parseInt(tempDay.data.get(k).temperature);
                                tempYear.highDate = tempDay.data.get(k).date;
                                tempYear.highTime = tempDay.data.get(k).time;

                            }
                            
                            if(tempDay.lowTemp > Integer.parseInt(tempDay.data.get(k).temperature)){
                                tempDay.lowTemp = Integer.parseInt(tempDay.data.get(k).temperature);
                                tempDay.lowDate = tempDay.data.get(k).date;
                                tempDay.lowTime = tempDay.data.get(k).time;
                            }
                            if(tempMonth.lowTemp > Integer.parseInt(tempDay.data.get(k).temperature)){
                                tempMonth.lowTemp = Integer.parseInt(tempDay.data.get(k).temperature);
                                tempMonth.lowDate = tempDay.data.get(k).date;
                                tempMonth.lowTime = tempDay.data.get(k).time;
                            }
                            if(tempYear.lowTemp > Integer.parseInt(tempDay.data.get(k).temperature)){
                                tempYear.lowTemp = Integer.parseInt(tempDay.data.get(k).temperature);
                                tempYear.lowDate = tempDay.data.get(k).date;
                                tempYear.lowTime = tempDay.data.get(k).time;
                            }
                            
                            if(tempDay.maxWindSpeed < Integer.parseInt(tempDay.data.get(k).windSpeed)){
                                tempDay.maxWindSpeed = Integer.parseInt(tempDay.data.get(k).windSpeed);
                                tempDay.windSpeedDate = tempDay.data.get(k).date;
                                tempDay.windSpeedTime = tempDay.data.get(k).time;
                            }
                            if(tempMonth.maxWindSpeed < Integer.parseInt(tempDay.data.get(k).windSpeed)){
                                tempMonth.maxWindSpeed = Integer.parseInt(tempDay.data.get(k).windSpeed);
                                tempMonth.windSpeedDate = tempDay.data.get(k).date;
                                tempMonth.windSpeedTime = tempDay.data.get(k).time;
                            }
                            if(tempYear.maxWindSpeed < Integer.parseInt(tempDay.data.get(k).windSpeed)){
                                tempYear.maxWindSpeed = Integer.parseInt(tempDay.data.get(k).windSpeed);
                                tempYear.windSpeedDate = tempDay.data.get(k).date;
                                tempYear.windSpeedTime = tempDay.data.get(k).time;
                            }
                            
                            
                        }
                        

                        tempMonth.days.add(tempDay);
                        tempYear.days.add(tempDay); 
                    }
                    
                    if(fileIndex < directory.listFiles().length)
                    {
                        tempYear.months.add(tempMonth);
                        file = directory.listFiles()[fileIndex];
                        fileIndex++;
                        document = builder.build(file);
                        root = document.getRootElement();
                    }
                }
                yearsList.add(tempYear);
            }
        }
        catch ( Exception e)
        {
            System.out.println( e.toString() );
        }
    }
    
}
