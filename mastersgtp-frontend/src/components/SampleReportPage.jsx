import { PDFDownloadLink, PDFViewer } from '@react-pdf/renderer';
import React, { useState } from 'react';
import { Button, Col, Container, Row } from 'react-bootstrap';
import { getScratches } from '../service/ScratchService';
import ReportDocument from './ReportDocument';

const SampleReportPage = () => {
    const [selectedReport, setSelectedReport] = useState(null);
    const [isLandscape, setIsLandscape] = useState(false);
    const [scratchData, setScratchData] = useState([]); // Define scratchData

    // Sample data for generic reports
    const reportDataSamples = [
        { field1: 'Data 1A', field2: 'Data 1B', field3: 'Data 1C', field4: 'Data 1D' },
        { field1: 'Data 2A', field2: 'Data 2B', field3: 'Data 2C', field4: 'Data 2D' },
    ];

    // Define reports array with generic data for non-specific reports
    const reports = Array.from({ length: 8 }, (_, i) => ({
        title: `Report ${i + 1}`,
        data: reportDataSamples,
        isScratchReport: false,
    }));

    // Fetch scratch data and set the report for scratches
    const fetchScratchData = async () => {
        try {
            const response = await getScratches();
            setScratchData(response.data);
            setSelectedReport({
                title: "Scratch Report",
                data: response.data,
                isScratchReport: true,
            });
        } catch (error) {
            console.error("Error fetching scratches:", error);
        }
    };

    const handleViewReport = (report) => {
        setSelectedReport(report);
    };

    const handleCrossReportView = () => {
        const day = prompt("Please enter the Day for the Score Report:");
        const dogNumber = prompt("Please enter the Dog Number for the Score Report:");
        if (dogNumber) {
            setSelectedReport({
                title: `Cross Report for Dog #${dogNumber}`,
                isCrossReport: true,
                day,
                dogNumber,
            });
        }
    };

    return (
        <Container fluid className="py-4" style={{ backgroundColor: '#f8f9fa', borderRadius: '10px', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)', maxWidth: '800px' }}>
            <h2 className="text-center mb-4">Generate Reports</h2>
            
            <Row className="d-flex justify-content-center mb-4">
                {reports.map((report, index) => (
                    <Col xs={6} md={4} className="mb-3 d-flex justify-content-center" key={index}>
                        <Button variant="primary" onClick={() => handleViewReport(report)} style={{ width: '100%', maxWidth: '150px' }}>
                            View {report.title}
                        </Button>
                    </Col>
                ))}
                <Col xs={6} md={4} className="mb-3 d-flex justify-content-center">
                    <Button variant="primary" onClick={handleCrossReportView} style={{ width: '100%', maxWidth: '150px' }}>
                        View Cross Report
                    </Button>
                </Col>
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
                        <Button variant="secondary" onClick={() => setIsLandscape(!isLandscape)}>
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
                            isCrossReport={selectedReport.isCrossReport}
                            dogNumber={selectedReport.dogNumber}
                            day={selectedReport.day}
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
                                    isCrossReport={selectedReport.isCrossReport}
                                    dogNumber={selectedReport.dogNumber}
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
