import { Document, Font, Page, PDFDownloadLink, PDFViewer, StyleSheet, Text, View } from '@react-pdf/renderer';
import React, { useState } from 'react';
import { Button, Col, Container, Row } from 'react-bootstrap';
import TimesNewRoman from '../font/times-new-roman-bold-italic.ttf';
import { getScratches } from '../service/ScratchService';

Font.register({
    family: 'Times New Roman',
    src: TimesNewRoman,
    fontStyle: 'bold italic'
});

const styles = StyleSheet.create({
    page: {
        padding: 30,
        flexDirection: 'column',
        justifyContent: 'space-between', // Ensures the footer stays at the bottom
    },
    title: {
        fontSize: 24,
        marginBottom: 10,
        color: 'blue',
        fontFamily: 'Times New Roman',
        fontStyle: 'bold italic'
    },
    tableHeader: {
        flexDirection: 'row',
        borderBottom: '1px solid black',
        paddingBottom: 4,
        marginBottom: 4,
        color: 'blue',
        fontFamily: 'Times New Roman',
        fontStyle: 'bold italic'
    },
    tableRow: {
        flexDirection: 'row',
        marginBottom: 4,
    },
    columnHeader: {
        fontWeight: 'bold',
        width: '25%',
    },
    tableCell: {
        width: '25%',
    },
    footer: {
        borderTop: '2px solid gray',
        position: 'absolute',
        bottom: 30,
        left: 20,
        right: 0,
        paddingTop: 5,
        textAlign: 'start',
        fontSize: 10,
        color: 'blue',
        maxWidth: '95vw',
        fontFamily: 'Times New Roman',
        fontStyle: 'bold italic'
    },
});

const ReportDocument = ({ title, reportData, isLandscape, isScratchReport }) => (
    <Document>
        <Page size={isLandscape ? [1224, 792] : [792, 1224]} style={styles.page}>
            <View>
                {/* Title */}
                <Text style={styles.title}>{title}</Text>
                {/* Scratch Report Table */}
                {isScratchReport ? (
                    <View>
                        <View style={styles.tableHeader}>
                            <Text style={{ ...styles.columnHeader, width: '20%' }}>Dog Number</Text>
                            <Text style={{ ...styles.columnHeader, width: '30%' }}>Judge Name</Text>
                            <Text style={{ ...styles.columnHeader, width: '30%' }}>Reason</Text>
                            <Text style={{ ...styles.columnHeader, width: '20%' }}>Time</Text>
                        </View>
                        {reportData.map((scratch, index) => (
                            <View key={index} style={styles.tableRow}>
                                <Text style={{ ...styles.tableCell, width: '20%' }}>{scratch.dog.number}</Text>
                                <Text style={{ ...styles.tableCell, width: '30%' }}>{scratch.judge.name}</Text>
                                <Text style={{ ...styles.tableCell, width: '30%' }}>{scratch.reason}</Text>
                                <Text style={{ ...styles.tableCell, width: '20%' }}>
                                    {`${String(Math.floor(scratch.time / 60)).padStart(2, '0')}:${String(scratch.time % 60).padStart(2, '0')}`}
                                </Text>
                            </View>
                        ))}
                    </View>
                ) : (
                    // Other Report Data
                    reportData.map((row, index) => (
                        <View key={index} style={styles.tableRow}>
                            <Text style={styles.tableCell}>{row.field1}</Text>
                            <Text style={styles.tableCell}>{row.field2}</Text>
                            <Text style={styles.tableCell}>{row.field3}</Text>
                            <Text style={styles.tableCell}>{row.field4}</Text>
                        </View>
                    ))
                )}
            </View>
            
            {/* Footer */}
            <Text style={styles.footer}>Generated on {new Date().toLocaleDateString()}</Text>
        </Page>
    </Document>
);

const SampleReportPage = () => {
    const [selectedReport, setSelectedReport] = useState(null);
    const [isLandscape, setIsLandscape] = useState(false); // Track orientation state
    const [scratchData, setScratchData] = useState([]);
    const reportDataSamples = [
        { field1: 'Data 1A', field2: 'Data 1B', field3: 'Data 1C', field4: 'Data 1D' },
        { field1: 'Data 2A', field2: 'Data 2B', field3: 'Data 2C', field4: 'Data 2D' },
    ];

    const reports = Array.from({ length: 8 }, (_, i) => ({
        title: `Report ${i + 1}`,
        data: reportDataSamples,
    }));

    // Fetch scratches and set data for the Scratch Report
    const fetchScratchData = async () => {
        try {
            const response = await getScratches();
            setScratchData(response.data);
            setSelectedReport({ title: "Scratch Report", data: response.data, isScratchReport: true });
        } catch (error) {
            console.error("Error fetching scratches:", error);
        }
    };

    const handleViewReport = (report) => {
        setSelectedReport(report);
    };

    return (
        <Container fluid className="py-4" style={{ backgroundColor: '#f8f9fa', borderRadius: '10px', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)', maxWidth: '800px' }}>
            <h2 className="text-center mb-4">Generate Reports</h2>
            
            <Row className="d-flex justify-content-center mb-4">
                {reports.map((report, index) => (
                    <Col xs={6} md={4} className="mb-3 d-flex justify-content-center" key={index}>
                        <Button
                            variant="primary"
                            onClick={() => handleViewReport(report)}
                            style={{ width: '100%', maxWidth: '150px' }}
                        >
                            View {report.title}
                        </Button>
                    </Col>
                ))}
                <Col xs={6} md={4} className="mb-3 d-flex justify-content-center">
                    <Button
                        variant="primary"
                        onClick={fetchScratchData}
                        style={{ width: '100%', maxWidth: '150px' }}
                    >
                        View All Scratches
                    </Button>
                </Col>
            </Row>

            {selectedReport && (
                <>
                    <div className="text-center mb-3">
                        <Button
                            variant="secondary"
                            onClick={() => setIsLandscape((prev) => !prev)}
                        >
                            Toggle Orientation: {isLandscape ? 'Landscape' : 'Portrait'}
                        </Button>
                    </div>
                    <h3 className="text-center mb-3">{selectedReport.title}</h3>
                    <PDFViewer style={{ width: '100%', height: '500px', border: '1px solid #ddd', marginBottom: '20px' }}>
                        <ReportDocument
                            title={selectedReport.title}
                            reportData={selectedReport.isScratchReport ? scratchData : selectedReport.data}
                            isLandscape={isLandscape}
                            isScratchReport={selectedReport.isScratchReport}
                        />
                    </PDFViewer>
                    <div className="text-center mt-2">
                        <PDFDownloadLink
                            document={
                                <ReportDocument
                                    title={selectedReport.title}
                                    reportData={selectedReport.isScratchReport ? scratchData : selectedReport.data}
                                    isLandscape={isLandscape}
                                    isScratchReport={selectedReport.isScratchReport}
                                />
                            }
                            fileName={`${selectedReport.title.toLowerCase().replace(/\s+/g, '_')}.pdf`}
                            style={{ textDecoration: 'none' }}
                        >
                            {({ loading }) => (
                                <Button variant="success">
                                    {loading ? 'Preparing download...' : 'Download PDF'}
                                </Button>
                            )}
                        </PDFDownloadLink>
                    </div>
                </>
            )}
        </Container>
    );
};

export default SampleReportPage;
