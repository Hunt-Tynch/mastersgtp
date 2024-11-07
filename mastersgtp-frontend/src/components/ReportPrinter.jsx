import { Document, Page, PDFViewer, Text, View } from '@react-pdf/renderer';
import React from 'react';

const ReportDocument = ({ reportData }) => (
    <Document>
        <Page size="A4" style={{ padding: 30 }}>
            <Text style={{ fontSize: 24, marginBottom: 10 }}>Hunt Report</Text>
            {reportData.map((row, index) => (
                <View key={index} style={{ flexDirection: 'row', marginBottom: 5 }}>
                    <Text style={{ width: '25%' }}>{row.field1}</Text>
                    <Text style={{ width: '25%' }}>{row.field2}</Text>
                    <Text style={{ width: '25%' }}>{row.field3}</Text>
                    <Text style={{ width: '25%' }}>{row.field4}</Text>
                </View>
            ))}
        </Page>
    </Document>
);

const ReportPrinter = ({ reportData }) => (
    <PDFViewer width="100%" height="600">
        <ReportDocument reportData={reportData} />
    </PDFViewer>
);

export default ReportPrinter;
