package com.afbb.balakrishna.parsingtechinques.XMLPULL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class XmlPullParserHandler {
    private List<Employee> employees = new ArrayList<Employee>();
    private Employee employee;
    private String text;

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Employee> parse(String fileData) {
        InputStream is = new ByteArrayInputStream(fileData.getBytes(StandardCharsets.UTF_8));
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("employee")) {
                            // create a new instance of employee
                            employee = new Employee();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("employee")) {
                            // add employee object to list
                            employees.add(employee);
                        } else if (tagname.equalsIgnoreCase("id")) {
                            employee.setId(Integer.parseInt(text));
                        } else if (tagname.equalsIgnoreCase("name")) {
                            employee.setName(text);
                        } else if (tagname.equalsIgnoreCase("salary")) {
                            employee.setSalary(Float.parseFloat(text));
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees;
    }
}  