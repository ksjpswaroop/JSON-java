package org.json.junit;

/*
Public Domain.
*/

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONML;
import org.json.JSONObject;
import org.junit.Test;
import org.xml.sax.InputSource;

import static org.junit.Assert.assertEquals;

/** Independent witness for the attribute-value case in stleary/JSON-java#1092. */
public class ReportedJsonmlAttributeWhitespaceTest {
    @Test
    public void newlineAttributeSurvivesStandardXmlParsing() throws Exception {
        String expected = "Lettuce\nTomato\nMustard";
        JSONObject jsonml = new JSONObject().put("tagName", "MyTag").put("value", expected);
        String xml = JSONML.toString(jsonml);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        String actual = factory.newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)))
                .getDocumentElement().getAttribute("value");

        assertEquals("JSONML must preserve the string value of an XML attribute", expected, actual);
    }
}
